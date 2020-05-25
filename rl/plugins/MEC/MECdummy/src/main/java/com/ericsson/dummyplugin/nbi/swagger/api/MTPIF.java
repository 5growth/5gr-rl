/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.nbi.swagger.api;


import com.ericsson.dummyplugin.events.abstraction.StartServer;
import com.ericsson.dummyplugin.events.abstraction.StopServer;
import com.google.common.eventbus.Subscribe;
//import com.mtp.SingletonEventBus;
//import com.mtp.events.abstraction.Creation.StartServer;
//import com.mtp.events.abstraction.Creation.StopServer;
//import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateReply;
//import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
//import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateReply;
//import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateRequest;
//import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateReply;
//import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
//import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateReply;
//import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateRequest;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;


/**
 *
 * @author user
 */

public class MTPIF {
    private URI BASE_URI;
    private HttpServer server;
    
    public MTPIF(String ip, String port ) {
        String uri_str = "http://" + ip + ":" + port;
        BASE_URI = URI.create(uri_str);
        System.out.println("NBIIF --> Create web server...");
        System.out.flush();
       // try {
            //register all api resources
            ResourceConfig config = new ResourceConfig();
            config.register(ComputeResourcesApi.class);
            config.register(HealthzApi.class);
            config.register(InformationApi.class);
            config.register(MecappApi.class);
            config.register(NetworkResourcesApi.class);
            config.register(QuotasApi.class);
            config.register(ReservationsApi.class);
            config.register(ServiceApi.class);
            config.register(SoftwareImagesApi.class);
            config.register(AbstractComputeOperateResourcesApi.class);        
            config.register(AbstractComputeResourcesApi.class);
            config.register(AbstractNetworkApi.class);
            config.register(AbstractNetworkResourcesApi.class);
            config.register(AbstractRadioCoverageareaApi.class);
            config.register(AbstractResourcesApi.class);
     
            //instance http server
            server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, false);

            //server.start();
        // } catch (IOException ex) {
           // System.out.println("NBIIF --> Error start web service");
           // ex.printStackTrace();
       // }
        System.out.println("NBIIF --> done");
    }
    
    @Subscribe
    public void handle_startserver(StartServer servreq) {
        System.out.println("NBIIF --> Start web server...");
        try {
          server.start();  
        } catch (IOException ex) {
            System.out.println("NBIIF --> Error start web service");
        }
        System.out.println("NBIIF --> done!");
        System.out.println("NBIIF --> MTP ready");

    }
    
    @Subscribe
    public void handle_stopserver(StopServer servreq) {

        server.stop();

    }

    
    
    
}
