
package com.sssa.wimplugin.sbi;

import com.sssa.wimplugin.SingletonEventBus;
import com.sssa.wimplugin.events.abstraction.Parsedomainlist;
import com.sssa.wimplugin.events.abstraction.StartServer;
import com.sssa.wimplugin.events.abstraction.WIMAbstractionReply;
import com.sssa.wimplugin.events.abstraction.WIMAbstractionRequest;
import com.sssa.wimplugin.events.allocate.ServiceNetworkAllocateReply;
import com.sssa.wimplugin.events.allocate.ServiceNetworkAllocateRequest;
import com.sssa.wimplugin.events.terminate.ServiceNetworkTerminateReply;
import com.sssa.wimplugin.events.terminate.ServiceNetworkTerminateRequest;
import com.sssa.wimplugin.nbi.swagger.model.AllocateReply;
import com.sssa.wimplugin.nbi.swagger.model.Gateways;
import com.sssa.wimplugin.nbi.swagger.model.GatewaysInner;
import com.sssa.wimplugin.nbi.swagger.model.GatewaysInnerGatewayAttributes;
import com.sssa.wimplugin.nbi.swagger.model.GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint;
import com.sssa.wimplugin.nbi.swagger.model.InlineResponse200;
import com.sssa.wimplugin.nbi.swagger.model.NetworkIds;
import com.sssa.wimplugin.nbi.swagger.model.VirtualLinks;
import com.sssa.wimplugin.nbi.swagger.model.VirtualLinksInner;
import com.sssa.wimplugin.nbi.swagger.model.VirtualLinksInnerVirtualLink;
import com.sssa.wimplugin.nbi.swagger.model.VirtualLinksInnerVirtualLinkNetworkQoS;
import com.sssa.wimplugin.sbi.objects_.NFVIPop;
import com.sssa.wimplugin.sbi.objects_.NetworkResource;
import com.sssa.wimplugin.sbi.objects_.NetworkService;
import com.sssa.wimplugin.sbi.objects_.Zone;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.json.JSONException;



public class SDNDomain {
    private List<NFVIPop> nfvipoplist;
    private List<Zone> zonelist;
    private HashMap<String, NetworkResource> networkresourceslist;
    private HashMap<String, NetworkService> networkserviceslist;
    private int servcounter;
    
    private HashMap<String, String> interfacesSrc;
    private HashMap<String, String> interfacesDst;
    private HashMap<String, String> intentslist;
   // private HashMap<String, String> intentsforwardlist;
   // private HashMap<String, String> intentsbackwardlist;
    
    public SDNDomain() {
        nfvipoplist = new ArrayList();
        zonelist = new ArrayList();
        networkresourceslist = new HashMap();
        networkserviceslist = new HashMap();
        servcounter = 1;
        
        interfacesSrc = new HashMap();
        interfacesDst = new HashMap();
        intentslist = new HashMap();//<identifier, intentId>
       // intentsforwardlist = new HashMap();
       // intentsbackwardlist = new HashMap();
    }
    
    
    
     //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        
        System.out.println("SDNDomain ---> Parse domlist from xml file");
        
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        
        nfvipoplist = xmldom.getPoplist();
        zonelist = xmldom.getZonelist();
        networkresourceslist = xmldom.getReslist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }
    
    
  /******************************************************************************************************************************/
   
        @Subscribe
    public void handle_WIMAbstractionRequest(WIMAbstractionRequest abstrreq) {//GET/Query request
        
        System.out.println("SDNDomain ---> retrieve network abstraction reqid = " + abstrreq.getReq());
        
        InlineResponse200 abstrrep = new InlineResponse200();
        Gateways gwlist = new Gateways();
        VirtualLinks linklist = new VirtualLinks();
        
        System.out.println("SDNDomain ---> Prepare response to the GET: List Gateways + List Virtual Links");
        
        String[] tok = nfvipoplist.get(0).getEndpoints().split(";");
        for (String token : tok) {
            GatewaysInner el = new GatewaysInner();
            GatewaysInnerGatewayAttributes elattr = new GatewaysInnerGatewayAttributes();
            List<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint> endplist = new ArrayList();
            elattr.setGatewayId(token);
            elattr.setGeographicalLocationInfo(nfvipoplist.get(0).getLocation());
            elattr.setWimId(nfvipoplist.get(0).getVim());
            GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint endp = new GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint();
            endp.setNetGwIpAddress(token);
            endplist.add(endp);
            elattr.setNetworkConnectivityEndpoint(endplist);
            el.setGatewayAttributes(elattr);
            gwlist.add(el);
        }
        
        for (NetworkResource value : networkresourceslist.values()) {
           VirtualLinksInner linkel = new VirtualLinksInner();
           VirtualLinksInnerVirtualLink linkattr = new VirtualLinksInnerVirtualLink();
           VirtualLinksInnerVirtualLinkNetworkQoS qos = new VirtualLinksInnerVirtualLinkNetworkQoS();
            qos.setLinkCostValue(BigDecimal.ONE);
            qos.setPacketLossRate(BigDecimal.ZERO);
            String valstr = value.getDelay();
            qos.setLinkDelayValue(new BigDecimal(value.getDelay()));
            linkattr.setNetworkQoS(qos);
            linkattr.setAvailableBandwidth(new BigDecimal(value.getFreecap()));
            linkattr.setDstGwId(value.getDstip());
            linkattr.setDstLinkId(Integer.parseInt(value.getRemid()));
            linkattr.setNetworkLayer(value.getType());
            linkattr.setSrcGwId(value.getSrcip());
            linkattr.setSrcLinkId(Integer.parseInt(value.getLocid()));
            linkattr.setTotalBandwidth(new BigDecimal(value.getTotcap()));
            linkattr.setVirtualLinkId(value.getId());
            linkel.setVirtualLink(linkattr);
            linklist.add(linkel);
        

        
        SingletonEventBus.getBus().post(wimabstrrep);
    }
    
  /******************************************************************************************************************************/
    
    
    @Subscribe
    public void handle_ServiceNetworkAllocateRequest(ServiceNetworkAllocateRequest servallreq) throws JSONException, Exception {//POST Request
        
        System.out.println("SDNDomain ---> allocate network service request" );
        
        System.out.println("SDNDomain ---> servallreq.getRequest().getWanLinkId(): "+servallreq.getRequest().getWanLinkId());

        System.out.println("SDNDomain ---> getVLANIds(): "+servallreq.getRequest().getSegmentId());

        NetworkResource el = networkresourceslist.get(servallreq.getRequest().getWanLinkId());
        
        if (el == null) {
            System.out.println("SDNDomain ---> no network resource found for service. resid = " + servallreq.getRequest().getWanLinkId());
            ServiceNetworkAllocateReply servallrep = new ServiceNetworkAllocateReply(servallreq.getReqid(), null);
            System.out.println("SDNDomain ---> post ServiceNetworkAllocateReply");
            SingletonEventBus.getBus().post(servallrep);
            return;
        }
        
            
        
    }
    
    
    @Subscribe
    public void handle_ServiceNetworkTerminateRequest(ServiceNetworkTerminateRequest servtermreq) throws JSONException, Exception {
        
       
    }
    
    
       
    
       /******************************************************************************/

}
