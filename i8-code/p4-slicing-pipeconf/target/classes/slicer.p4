/*
 * Copyright 2017-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <core.p4>
#include <v1model.p4>

#define MAX_PORTS 255
#define WRITE_REG(r, v) r.write((bit<32>)standard_metadata.egress_port, v);
#define READ_REG(r, v) r.read(v,(bit<32>)standard_metadata.egress_port);
#define CAP(c, v, a, t){ if (v > c) a = c; else a = (t)v; }
#define MAX(a,b,res) { if (a>b) res = a; else res = b;}
#define MIN(a,b,res) { if (a<b) res = a; else res = b;}


const bit<16> ETH_TYPE_MYTUNNEL = 0x1212;
const bit<16> ETH_TYPE_IPV4 = 0x800;

const bit<16> ETH_TYPE_VLAN = 0x8100;
const bit<16> ETH_TYPE_VLAN2 = 0x9100;

typedef bit<9> port_t;
typedef bit<9> queue_id_t;
typedef bit<12> VLAN_VID;
const port_t CPU_PORT = 255;

typedef bit<32> METER_ID_t;
typedef bit<32> sliceid_t;

typedef bit<48> T_DELAY_t;  //transmission delay -  sets rate assuming 1500 byte MTU
typedef bit<48> C_DELAY_t; //congestion delay - drop
typedef bit<48> M_DELAY_t; // markinf delay

//state registers
register<bit<32>>(256) r_dropped;      // packets dropped to report in next not dropped packet
register<bit<48>>(256) s_delay;
register<bit<48>>(256) r_delay;
register<bit<48>>(256) s_ts;
register<bit<48>>(256) r_delta;

//------------------------------------------------------------------------------
// HEADERS
//------------------------------------------------------------------------------

header vlan_t {
    bit<16> ethertype;
    bit<3> pcp;
    bit<1> cfi;
    bit<12> vid;
}


header ethernet_t {
    bit<48> dst_addr;
    bit<48> src_addr;
}

header eth_type_t {
    bit<16> value;
}

header ipv4_t {
    bit<4>  version;
    bit<4>  ihl;
    bit<6>  diffserv;
    bit<2> ecn;
    bit<16> len;
    //  bit<16>   identification;  // repurpose identification field...
    bit<4>    drops;           // up to 15 previous packets dropped
    bit<6>    rdelay_ms;       // up to 63ms queue delay
    bit<6>    vdelay_ms;       // up to 63ms queue delay
    bit<3>  flags;
    bit<13> frag_offset;
    bit<8>  ttl;
    bit<8>  protocol;
    bit<16> hdr_checksum;
    bit<32> src_addr;
    bit<32> dst_addr;
}
header meta_t {
    bit<1> mtd;
    bit<1> foo;
    bit<6> padding;
}

struct metadata_t {
    meta_t meta;
    bit<1>   mark_drop;
    sliceid_t slice_id;
    bit<16> hash1;
    bit<32> meter_tag;
    bool metered;
    bool queue;
    T_DELAY_t T_DELAY;
    C_DELAY_t C_DELAY;
    M_DELAY_t M_DELAY;
}

// Packet-in header. Prepended to packets sent to the controller and used to
// carry the original ingress port where the packet was received.
@controller_header("packet_in")
header packet_in_header_t {
    bit<9> ingress_port;
    bit<7> _padding;
}

// Packet-out header. Prepended to packets received by the controller and used
// to tell the switch on which port this packet should be forwarded.
@controller_header("packet_out")
header packet_out_header_t {
    bit<9> egress_port;
    bit<7> _padding;
}

// For convenience we collect all headers under the same struct.
struct headers_t {
    ethernet_t ethernet;
    vlan_t vlan;
    eth_type_t ether_type;
    ipv4_t ipv4;
    packet_out_header_t packet_out;
    packet_in_header_t packet_in;
}

//------------------------------------------------------------------------------
// PARSER
//------------------------------------------------------------------------------

parser c_parser(packet_in packet,
                  out headers_t hdr,
                  inout metadata_t meta,
                  inout standard_metadata_t standard_metadata) {

    // A P4 parser is described as a state machine, with initial state "start"
    // and final one "accept". Each intermediate state can specify the next
    // state by using a select statement over the header fields extracted.
    state start {
        transition select(standard_metadata.ingress_port) {
            CPU_PORT: parse_packet_out;
            default: parse_ethernet;
        }
    }

    state parse_packet_out {
        packet.extract(hdr.packet_out);
        transition parse_ethernet;
    }

    state parse_ethernet {
        packet.extract(hdr.ethernet);
        transition select(packet.lookahead<bit<16>>()){
            ETH_TYPE_VLAN: parse_vlan;
            default: parse_eth_type;
        }
    }

    state parse_vlan {
        packet.extract(hdr.vlan);
        transition parse_eth_type;
    }

    state parse_eth_type {
            packet.extract(hdr.ether_type);
            transition select(hdr.ether_type.value) {
                ETH_TYPE_IPV4: parse_ipv4;
                default:accept;
            }
        }

    state parse_ipv4 {
        packet.extract(hdr.ipv4);
        transition accept;
    }
}

//------------------------------------------------------------------------------
// INGRESS PIPELINE
//------------------------------------------------------------------------------

control c_ingress(inout headers_t hdr,
                    inout metadata_t meta,
                    inout standard_metadata_t standard_metadata) {

    // We use these counters to count packets/bytes received/sent on each port.
    // For each counter we instantiate a number of cells equal to MAX_PORTS.
    counter(MAX_PORTS, CounterType.packets_and_bytes) tx_port_counter;
    counter(MAX_PORTS, CounterType.packets_and_bytes) rx_port_counter;

    meter(32w4801, MeterType.packets) my_meter;

    action send_to_cpu() {
        standard_metadata.egress_spec = CPU_PORT;
        // Packets sent to the controller needs to be prepended with the
        // packet-in header. By setting it valid we make sure it will be
        // deparsed on the wire (see c_deparser).
        hdr.packet_in.setValid();
        hdr.packet_in.ingress_port = standard_metadata.ingress_port;
    }

    action set_out_port(port_t port) {
        // Specifies the output port for this packet by setting the
        // corresponding metadata.
        standard_metadata.egress_spec = port;
    }

    action set_vlan(port_t port, VLAN_VID vlan_vid) {
        hdr.vlan.vid = vlan_vid;
        hdr.vlan.pcp = 0;
        hdr.vlan.cfi = 0;
        hdr.vlan.ethertype = ETH_TYPE_VLAN;
        hdr.vlan.setValid();

        standard_metadata.egress_spec = port;
    }

    action set_vlan_meter(port_t port, VLAN_VID vlan_vid, METER_ID_t meter_id) {
        hdr.vlan.vid = vlan_vid;
        hdr.vlan.pcp = 0;
        hdr.vlan.cfi = 0;
        hdr.vlan.ethertype = ETH_TYPE_VLAN;
        hdr.vlan.setValid();

        my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
        standard_metadata.egress_spec = port;
    }
    action set_vlan_meter_queue(port_t port, VLAN_VID vlan_vid, METER_ID_t meter_id, T_DELAY_t T_DELAY, C_DELAY_t C_DELAY,M_DELAY_t M_DELAY) {
        hdr.vlan.vid = vlan_vid;
        hdr.vlan.pcp = 0;
        hdr.vlan.cfi = 0;
        hdr.vlan.ethertype = ETH_TYPE_VLAN;
        hdr.vlan.setValid();

        my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
        standard_metadata.egress_spec = port;

        meta.queue = true;
        meta.T_DELAY = T_DELAY;
        meta.C_DELAY = C_DELAY;
        meta.M_DELAY = M_DELAY;
        meta.slice_id = meter_id;
    }

    action pop_vlan(port_t port) {
        hdr.vlan.setInvalid();

        standard_metadata.egress_spec = port;
    }

    action pop_vlan_meter(port_t port, METER_ID_t meter_id) {
        hdr.vlan.setInvalid();

        my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
        meta.metered = true;
        standard_metadata.egress_spec = port;
    }

    action pop_vlan_meter_queue(port_t port, METER_ID_t meter_id, T_DELAY_t T_DELAY, C_DELAY_t C_DELAY,M_DELAY_t M_DELAY) {
        hdr.vlan.setInvalid();

        my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
        meta.metered = true;
        standard_metadata.egress_spec = port;

        meta.queue = true;
        meta.T_DELAY = T_DELAY;
        meta.C_DELAY = C_DELAY;
        meta.M_DELAY = M_DELAY;
        meta.slice_id = meter_id;
    }

    action push_vlan(port_t port, VLAN_VID vlan_vid) {
    // If VLAN is already valid, we overwrite it with a potentially new VLAN
    // ID, and same CFI, PRI, and eth_type values found in ingress.
        hdr.vlan.setValid();
        hdr.vlan.vid = vlan_vid;
        hdr.vlan.pcp = 0;
        hdr.vlan.cfi = 0;
        hdr.vlan.ethertype = ETH_TYPE_VLAN;
        standard_metadata.egress_spec = port;
    }

    action push_vlan_meter(port_t port, VLAN_VID vlan_vid, METER_ID_t meter_id) {
    // If VLAN is already valid, we overwrite it with a potentially new VLAN
    // ID, and same CFI, PRI, and eth_type values found in ingress.
        hdr.vlan.setValid();
        hdr.vlan.vid = vlan_vid;
        hdr.vlan.pcp = 0;
        hdr.vlan.cfi = 0;
        hdr.vlan.ethertype = ETH_TYPE_VLAN;
       my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
       meta.metered = true;
       standard_metadata.egress_spec = port;
    }

    action push_vlan_meter_queue(port_t port, VLAN_VID vlan_vid, METER_ID_t meter_id, T_DELAY_t T_DELAY, C_DELAY_t C_DELAY,M_DELAY_t M_DELAY) {
    // If VLAN is already valid, we overwrite it with a potentially new VLAN
    // ID, and same CFI, PRI, and eth_type values found in ingress.
        hdr.vlan.setValid();
        hdr.vlan.vid = vlan_vid;
        hdr.vlan.pcp = 0;
        hdr.vlan.cfi = 0;
        hdr.vlan.ethertype = ETH_TYPE_VLAN;

       my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
       meta.metered = true;
       standard_metadata.egress_spec = port;

       meta.queue = true;
       meta.T_DELAY = T_DELAY;
       meta.C_DELAY = C_DELAY;
       meta.M_DELAY = M_DELAY;
       meta.slice_id = meter_id;
    }

    action _drop() {
        mark_to_drop(standard_metadata);
    }

    action m_action(METER_ID_t meter_id) {
        //my_meter.read(meta.meter_tag);
        my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
        meta.metered = true;
    }

    action set_out_port_metered(bit<9> port, METER_ID_t meter_id) {
        standard_metadata.egress_spec = port;
        my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
        meta.metered = true;
    }

    action set_out_port_metered_queue(bit<9> port, METER_ID_t meter_id, T_DELAY_t T_DELAY, C_DELAY_t C_DELAY,M_DELAY_t M_DELAY) {
       standard_metadata.egress_spec = port;
       my_meter.execute_meter((bit<32>)meter_id, meta.meter_tag);
       meta.metered = true;

       meta.queue = true;
       meta.T_DELAY = T_DELAY;
       meta.C_DELAY = C_DELAY;
       meta.M_DELAY = M_DELAY;
       meta.slice_id = meter_id;
    }

    action set_ecn() {
        if (hdr.ipv4.ecn & 2 == 2)
        {
             hdr.ipv4.ecn = 3;
        }
    }

    action set_ecn_forward(bit<9> port) {
        if (hdr.ipv4.ecn & 2 == 2)
        {
             hdr.ipv4.ecn = 3;
        }
        standard_metadata.egress_spec = port;
    }

    action m_drop() {
        #define WRITE_REG_SLICE(r, v) r.write(meta.slice_id, v);
        #define READ_REG_SLICE(r, v) r.read(v,meta.slice_id);
        bit<32> dropped_pks;
        mark_to_drop(standard_metadata);
       // READ_REG_SLICE(r_dropped, dropped_pks);
       // dropped_pks = dropped_pks + 1;
       // WRITE_REG_SLICE(r_dropped, dropped_pks);
    }

    // Table counter used to count packets and bytes matched by each entry of
    // t_l2_fwd table.
    direct_counter(CounterType.packets_and_bytes) l2_fwd_counter;

    table t_l2_fwd {
        key = {
            standard_metadata.ingress_port  : ternary;
            hdr.ethernet.dst_addr           : ternary;
            hdr.ethernet.src_addr           : ternary;
            hdr.ipv4.dst_addr               : lpm;
            hdr.vlan.vid                    : ternary;
            hdr.ether_type.value         : ternary;
        }
        actions = {
            set_vlan;
            set_vlan_meter;
            set_vlan_meter_queue;
            push_vlan;
            push_vlan_meter;
            push_vlan_meter_queue;
            pop_vlan;
            pop_vlan_meter;
            pop_vlan_meter_queue;
            set_out_port;
            set_out_port_metered;
            set_out_port_metered_queue;
            send_to_cpu;
            m_action;
            _drop;
            NoAction;
        }
        default_action = NoAction();
        counters = l2_fwd_counter;
    }

    // Table counter used to count packets and bytes matched by each entry of
    // m_filter table.
    direct_counter(CounterType.packets_and_bytes) m_filter_counter;
    table m_filter {
        key = {
            meta.meter_tag: exact;
        }
        actions = {
            set_ecn;
            set_ecn_forward;
            m_drop;
            NoAction;
        }
        size = 1024;
        default_action = m_drop();
        counters = m_filter_counter;
    }

    // Defines the processing applied by this control block. You can see this as
    // the main function applied to every packet received by the switch.
    apply {
        if (standard_metadata.ingress_port == CPU_PORT) {
            // Packet received from CPU_PORT, this is a packet-out sent by the
            // controller. Skip table processing, set the egress port as
            // requested by the controller (packet_out header) and remove the
            // packet_out header.
            standard_metadata.egress_spec = hdr.packet_out.egress_port;
            hdr.packet_out.setInvalid();
        } else {
            // Packet received from data plane port.
            // Applies table t_l2_fwd to the packet.
            t_l2_fwd.apply();
             // Update port counters at index = ingress or egress port.
            if (hdr.ipv4.isValid() && meta.metered) {
                m_filter.apply();
            }
            if (standard_metadata.egress_spec < MAX_PORTS) {
                tx_port_counter.count((bit<32>) standard_metadata.egress_spec);
            }
            if (standard_metadata.ingress_port < MAX_PORTS) {
                rx_port_counter.count((bit<32>) standard_metadata.ingress_port);
            }
        }
    }
}

//------------------------------------------------------------------------------
// EGRESS PIPELINE
//------------------------------------------------------------------------------

control c_egress(inout headers_t hdr,
                 inout metadata_t meta,
                 inout standard_metadata_t standard_metadata) {

    action drop() {
        bit<32> dropped_pks;
        mark_to_drop(standard_metadata);//ToDo check if it is working with standard_metadata parameter, NBL code havent got
        READ_REG_SLICE(r_dropped, dropped_pks);
        dropped_pks = dropped_pks + 1;
        WRITE_REG_SLICE(r_dropped, dropped_pks);
    }
    action rate_limit() {
        T_DELAY_t T_DELAY = meta.T_DELAY;
        C_DELAY_t C_DELAY = meta.C_DELAY;
        M_DELAY_t M_DELAY = meta.M_DELAY;
        bit<48> delay=0;  //delay reported
        bit<48> max_delay=0; //delay used
        bit<48> c_ts =  standard_metadata.egress_global_timestamp;
        bit<48> p_ts;
        bit<48> delta = 0;
		bit<6>  rdelay;
        bit<6>  vdelay;
        s_delay.read(delay,meta.slice_id);
        s_ts.read(p_ts,meta.slice_id);
        if (p_ts==0) p_ts = c_ts;
        delta = c_ts-p_ts;
        if (delta > delay) {delay = 0;}
        else {delay = delay - delta;}
        MAX(delay, (bit<48>)standard_metadata.deq_timedelta, max_delay);
        if (max_delay+(T_DELAY) > C_DELAY){meta.mark_drop=1;}
        else {
            if (hdr.ipv4.ecn & 2 == 2)//ecn marked
            {
                if (max_delay + T_DELAY > M_DELAY) {hdr.ipv4.ecn = 3;}
            }
                delay = delay + T_DELAY;
            }
        s_delay.write(meta.slice_id,delay);
        s_ts.write(meta.slice_id, c_ts);
        r_delta.write(meta.slice_id,delta);
        r_delay.write(meta.slice_id,(bit<48>) standard_metadata.deq_timedelta);
        CAP(63, (delay>>10), vdelay, bit<6>)
        CAP(63, (standard_metadata.deq_timedelta >> 10), rdelay, bit<6>)
        hdr.ipv4.vdelay_ms = vdelay;
        hdr.ipv4.rdelay_ms = rdelay;
    }

   table policing{
       key = {
       //No key => miss match (default action)
           //standard_metadata.egress_port: exact;
           //hdr.ipv4.srcAddr: lpm;
       }
       actions =
       {
           rate_limit;
       }
       default_action = rate_limit();
   }


    apply {
        //Only apply that to queue flows
        if(meta.queue == true) {
            policing.apply();
            if (meta.mark_drop == 1){
                drop();
            }
	        else {
                bit<32> dropped_pks;
                bit<4> drops;
                READ_REG_SLICE(r_dropped, dropped_pks);
                CAP(15, dropped_pks, drops, bit<4>); // max 15 drops can be reported
	        hdr.ipv4.drops = drops;  //used for reporting drops
                dropped_pks = dropped_pks - (bit<32>)drops;
                WRITE_REG_SLICE(r_dropped, dropped_pks);
            }
        }
    }
}

//------------------------------------------------------------------------------
// CHECKSUM HANDLING
//------------------------------------------------------------------------------

control c_verify_checksum(inout headers_t hdr, inout metadata_t meta) {
    apply {
        // Nothing to do here, we assume checksum is always correct.
    }
}

control c_compute_checksum(inout headers_t hdr, inout metadata_t meta) {
    apply {
          update_checksum(
                hdr.ipv4.isValid(),
                { hdr.ipv4.version,
                  hdr.ipv4.ihl,
                  hdr.ipv4.diffserv,
                  hdr.ipv4.ecn,
                  hdr.ipv4.len,
        //        hdr.ipv4.identification,
                  hdr.ipv4.drops,
        	  hdr.ipv4.rdelay_ms,
                  hdr.ipv4.vdelay_ms,
                  hdr.ipv4.flags,
                  hdr.ipv4.frag_offset,
                  hdr.ipv4.ttl,
                  hdr.ipv4.protocol,
                  hdr.ipv4.src_addr,
                  hdr.ipv4.dst_addr },
                hdr.ipv4.hdr_checksum,
                HashAlgorithm.csum16);
    }
}

//------------------------------------------------------------------------------
// DEPARSER
//------------------------------------------------------------------------------

control c_deparser(packet_out packet, in headers_t hdr) {
    apply {
        // Emit headers on the wire in the following order.
        // Only valid headers are emitted.
        packet.emit(hdr.packet_in);
        packet.emit(hdr.ethernet);
        packet.emit(hdr.vlan);
        packet.emit(hdr.ether_type);
        packet.emit(hdr.ipv4);
    }
}

//------------------------------------------------------------------------------
// SWITCH INSTANTIATION
//------------------------------------------------------------------------------

V1Switch(c_parser(),
         c_verify_checksum(),
         c_ingress(),
         c_egress(),
         c_compute_checksum(),
         c_deparser()) main;
