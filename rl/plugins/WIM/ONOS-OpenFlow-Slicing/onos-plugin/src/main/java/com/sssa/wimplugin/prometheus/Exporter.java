
package com.sssa.wimplugin.prometheus;

//import io.prometheus.client.exporter.common.TextFormat;

import static com.sssa.wimplugin.sbi.ONOSNbi.getDevices;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.zip.GZIPOutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sssa.wimplugin.sbi.ONOSNbi.getNumberInstalledIntents;
import static com.sssa.wimplugin.sbi.ONOSNbi.get_devices;
import static com.sssa.wimplugin.sbi.ONOSNbi.get_statistics_ports_per_device;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Expose Prometheus metrics using a plain Java HttpServer.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * HTTPServer server = new HTTPServer(1234);
 * }
 * </pre>
 * */
public class Exporter {
    
            
    static final Gauge nbSwitches = Gauge.build().name("onos_number_openflow_switches").help("Number of the switches in the network").register();
    
    static final Gauge nbvLinks = Gauge.build().name("onos_number_virtual_links").help("Number of virtual links currently installed").register();
    
    static Gauge packets_received = Gauge.build().name("onos_device_packets_received").help("onos_device_packets_received").labelNames("device", "port").register();
    
    static Gauge packets_transmitted = Gauge.build().name("onos_device_packets_transmitted").help("onos_device_packets_transmitted").labelNames("device", "port").register();
    
    static Gauge bytes_received = Gauge.build().name("onos_device_bytes_received").help("onos_device_bytes_received").labelNames("device", "port").register();
    
    static Gauge bytes_transmitted = Gauge.build().name("onos_device_bytes_transmitted").help("onos_device_bytes_transmitted").labelNames("device", "port").register();
    
   
   
    private static class LocalByteArray extends ThreadLocal<ByteArrayOutputStream> {
        protected ByteArrayOutputStream initialValue()
        {
            return new ByteArrayOutputStream(1 << 20);
        }
    }

    static class HTTPMetricHandler implements HttpHandler {
        private CollectorRegistry registry;
        private final LocalByteArray response = new LocalByteArray();

        HTTPMetricHandler(CollectorRegistry registry) {
          this.registry = registry;
        }


        @Override
        public void handle(HttpExchange t) throws IOException {
            
           
            try {
                nbSwitches.set(getDevices());
                nbvLinks.set(getNumberInstalledIntents());
     
                for(int nbDevices=0; nbDevices<getDevices(); nbDevices++)
                {
                
                   String deviceID = get_devices().get(nbDevices);
                   System.out.println("deviceID: "+deviceID);
                   JSONObject data_0 = get_statistics_ports_per_device(deviceID);
                   
                   JSONArray statistics = data_0.getJSONArray("statistics");
                   int port_length = 0;

                   for(int ind=0; ind<statistics.length(); ind++)
                {
                   
                  String device = statistics.getJSONObject(ind).getString("device");  
                 if(device.equals(deviceID))
                 {                  
                  JSONArray ports = statistics.getJSONObject(ind).getJSONArray("ports");
                  port_length = ports.length();
                  
                  System.out.println("device: "+device);
                  
                       
                  System.out.println("ports number: "+port_length);

                   for(int number=0; number<port_length; number++)
                   {
                       
                       String[] labelValues = new String[2];
                       
                       labelValues[0] = device;//Strings To be extracted from JSON
                       
                       int port = ports.getJSONObject(number).getInt("port"); 
                       
                       System.out.println("port: "+port);
                       labelValues[1] = String.valueOf(port); //Strings To be extracted from JSON
                       
                       double pkReceived = ports.getJSONObject(number).getDouble("packetsReceived");
                       System.out.println("pkReceived: "+pkReceived);
                       
                       packets_received.labels(labelValues).set(pkReceived);
                       
                       double pkTransmitted = ports.getJSONObject(number).getDouble("packetsSent");
                       System.out.println("pkTransmitted: "+pkTransmitted);
                       
                       packets_transmitted.labels(labelValues).set(pkTransmitted);
                       
                       double bytesReceived = ports.getJSONObject(number).getDouble("bytesReceived");
                       System.out.println("bytesReceived: "+bytesReceived);
                       
                       bytes_received.labels(labelValues).set(bytesReceived);
                       
                       double bytesTransmitted = ports.getJSONObject(number).getDouble("bytesSent");
                       System.out.println("bytesTransmitted: "+bytesTransmitted);
                       
                       bytes_transmitted.labels(labelValues).set(bytesTransmitted);
                      
                   } } } }//for all the devices in the topology

                
                //nbPackets.set(getIntentPackets("0x0"));
            } catch (Exception ex) {
                Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            
            String query = t.getRequestURI().getRawQuery();

            ByteArrayOutputStream response = this.response.get();
            response.reset();
            OutputStreamWriter osw = new OutputStreamWriter(response);
            TextFormat.write004(osw,
                    registry.filteredMetricFamilySamples(parseQuery(query)));
            osw.flush();
            osw.close();
            response.flush();
            response.close();

            t.getResponseHeaders().set("Content-Type",
                    TextFormat.CONTENT_TYPE_004);
            t.getResponseHeaders().set("Content-Length",
                    String.valueOf(response.size()));
            if (shouldUseCompression(t)) {
                t.getResponseHeaders().set("Content-Encoding", "gzip");
                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                final GZIPOutputStream os = new GZIPOutputStream(t.getResponseBody());
                response.writeTo(os);
                os.finish();
            } else {
                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.size());
                response.writeTo(t.getResponseBody());
            }
            t.close();
        }

    }

    protected static boolean shouldUseCompression(HttpExchange exchange) {
        List<String> encodingHeaders = exchange.getRequestHeaders().get("Accept-Encoding");
        if (encodingHeaders == null) return false;

        for (String encodingHeader : encodingHeaders) {
            String[] encodings = encodingHeader.split(",");
            for (String encoding : encodings) {
                if (encoding.trim().toLowerCase().equals("gzip")) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static Set<String> parseQuery(String query) throws IOException {
        Set<String> names = new HashSet<String>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                if (idx != -1 && URLDecoder.decode(pair.substring(0, idx), "UTF-8").equals("name[]")) {
                    names.add(URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                }
            }
        }
        return names;
    }


    static class DaemonThreadFactory implements ThreadFactory {
        private ThreadFactory delegate;
        private final boolean daemon;

        DaemonThreadFactory(ThreadFactory delegate, boolean daemon) {
            this.delegate = delegate;
            this.daemon = daemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = delegate.newThread(r);
            t.setDaemon(daemon);
            return t;
        }

        static ThreadFactory defaultThreadFactory(boolean daemon) {
            return new DaemonThreadFactory(Executors.defaultThreadFactory(), daemon);
        }
    }

    protected final HttpServer server;
    protected final ExecutorService executorService;


    /**
     * Start a HTTP server serving Prometheus metrics from the given registry.
     */
    public Exporter(InetSocketAddress addr, CollectorRegistry registry, boolean daemon) throws IOException {
        server = HttpServer.create();
        server.bind(addr, 3);
        HttpHandler mHandler = new HTTPMetricHandler(registry);
        server.createContext("/", mHandler);
        server.createContext("/metrics", mHandler);
        executorService = Executors.newFixedThreadPool(5, DaemonThreadFactory.defaultThreadFactory(daemon));
        server.setExecutor(executorService);
        start(daemon);
        
    }

    /**
     * Start a HTTP server serving Prometheus metrics from the given registry using non-daemon threads.
     */
    public Exporter(InetSocketAddress addr, CollectorRegistry registry) throws IOException {
        this(addr, registry, false);
    }

    /**
     * Start a HTTP server serving the default Prometheus registry.
     */
    public Exporter(int port, boolean daemon) throws IOException {
        this(new InetSocketAddress(port), CollectorRegistry.defaultRegistry, daemon);
    }

    /**
     * Start a HTTP server serving the default Prometheus registry using non-daemon threads.
     */
    public Exporter(int port) throws IOException {
        
        this(port, false);
       
    }

    /**
     * Start a HTTP server serving the default Prometheus registry.
     */
    public Exporter(String host, int port, boolean daemon) throws IOException {
        this(new InetSocketAddress(host, port), CollectorRegistry.defaultRegistry, daemon);
    }

    /**
     * Start a HTTP server serving the default Prometheus registry using non-daemon threads.
     */
    public Exporter(String host, int port) throws IOException {
        this(new InetSocketAddress(host, port), CollectorRegistry.defaultRegistry, false);
    }

    /**
     * Start a HTTP server by making sure that its background thread inherit proper daemon flag.
     */
    public void start(boolean daemon) {
        if (daemon == Thread.currentThread().isDaemon()) {
            server.start();
        } else {
            FutureTask<Void> startTask = new FutureTask<Void>(new Runnable() {
                @Override
                public void run() {
                    server.start();
                }
            }, null);
            DaemonThreadFactory.defaultThreadFactory(daemon).newThread(startTask).start();
            try {
                startTask.get();
            } catch (ExecutionException e) {
                throw new RuntimeException("Unexpected exception on starting HTTPSever", e);
            } catch (InterruptedException e) {
                // This is possible only if the current tread has been interrupted,
                // but in real use cases this should not happen.
                // In any case, there is nothing to do, except to propagate interrupted flag.
                Thread.currentThread().interrupt();
            }
        }
        
           }

    /**
     * Stop the HTTP server.
     */
    public void stop() {
        server.stop(0);
        executorService.shutdown(); // Free any (parked/idle) threads in pool
    }
    
    
 /**********************************************************************************************/   

    
/***********************************************************************************************/    
}
