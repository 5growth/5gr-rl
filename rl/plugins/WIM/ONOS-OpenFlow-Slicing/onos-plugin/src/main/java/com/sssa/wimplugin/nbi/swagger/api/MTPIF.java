
package com.sssa.wimplugin.nbi.swagger.api;


import com.sssa.wimplugin.events.abstraction.StartServer;
import com.sssa.wimplugin.events.abstraction.StopServer;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;


public class MTPIF {
    private URI BASE_URI;
    private HttpServer server;
    
    public MTPIF(String ip, String port ) {
        //String uri_str = "http://" + ip + ":" + port + "/wimsdnplugin";
        String uri_str = "http://" + ip + ":" + port;
        BASE_URI = URI.create(uri_str);
        System.out.println("Create web server...");
        
        System.out.flush();
       // try {
            //register all api resources
            ResourceConfig config = new ResourceConfig();
            config.register(ComputeResourcesApi.class);
            config.register(HealthzApi.class);
            config.register(InformationApi.class);
            config.register(NetworkResourcesApi.class);
            config.register(QuotasApi.class);
            config.register(ReservationsApi.class);
            config.register(SoftwareImagesApi.class);
            config.register(AbstractComputeResourcesApi.class);
            config.register(AbstractNetworkApi.class);
            config.register(AbstractNetworkResourcesApi.class);
            config.register(AbstractResourcesApi.class);
     
            //instance http server
            server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, false);

    }
    
    @Subscribe
    public void handle_startserver(StartServer servreq) {
        System.out.println("Start web server...");
        try {
          server.start();  
        } catch (IOException ex) {
            System.out.println("Error start web service");
        }
        System.out.println("done!");
        
    }
    
    @Subscribe
    public void handle_stopserver(StopServer servreq) {

        server.stop();

    }

    
    
    
}
