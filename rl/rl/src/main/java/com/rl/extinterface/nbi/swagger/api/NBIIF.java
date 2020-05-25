/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.events.abstraction.Creation.StartServer;
import com.rl.events.abstraction.Creation.StopServer;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;


/**
 *
 * @author user
 */

public class NBIIF {
    private URI BASE_URI;
    private HttpServer server;
    
    public NBIIF(String ip, String port ) {
        String uri_str = "http://" + ip + ":" + port + "/mtpbase/";
        BASE_URI = URI.create(uri_str);
        System.out.println("NBIIF --> Create web server...");
        System.out.flush();
       // try {
            //register all api resources
            ResourceConfig config = new ResourceConfig();
            config.register(AbstractComputeOperateResourcesApi.class);
            config.register(AbstractComputeResourcesApi.class);
            config.register(AbstractNetworkApi.class);
            config.register(AbstractNetworkResourcesApi.class);
            config.register(AbstractRadioCoverageareaApi.class);
            config.register(AbstractResourcesApi.class);
            config.register(AbstractFederatedResourcesApi.class);
            config.register(ComputeOperateResourcesApi.class);
            config.register(ComputeResourcesApi.class);
            config.register(HealthzApi.class);
            config.register(InformationApi.class);
            config.register(ServiceApi.class);
            config.register(MecappApi.class);
            config.register(MfsApi.class);
            config.register(NetworkResourcesApi.class);
            config.register(PhysicalResourcesApi.class);
            config.register(PnfsApi.class);
            config.register(QuotasApi.class);
            config.register(ReservationsApi.class);
            config.register(SoftwareImagesApi.class);
            config.register(JacksonFeature.class);
     
            //instance http server
            server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, false);
           // Allow Body for http delete operation 
            server.getServerConfiguration().setAllowPayloadForUndefinedHttpMethods(true);
            //server.start();
        // } catch (IOException ex) {
           // System.out.println("NBIIF --> Error start web service");
           // ex.printStackTrace();
       // }
        System.out.println("NBIIF --> done");
    }
    
    @Subscribe
    public void handle_StartServer(StartServer servreq) {
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

  

    
    //TODO: Manage IFA005 Messages  from SO
    //TODO: Implement post for IFA Query Compute
    //TODO: Implement post for IFA Query Network
    //TODO: Implement post for IFA Compute Allocation
    //TODO: Implement post for IFA Network Allocation
    //TODO: Implement post for IFA Compute Allocation
    //TODO: Implement post for IFA Network Allocation
//    @Subscribe
//    public void handle_AdvertiseE2EAbstractionReply(AdvertiseE2EAbstractionReply advE2Erepev) {
//        System.out.println("NBIIF --> handle AdvertiseE2EAbstractionReply Event");
//        //TODO send IFA005 query messages as reply
//        System.out.println("NBIIF --> Query Done for reqid = " + advE2Erepev.getReqId());
//        System.out.println("End Advertise Test !!!! ");
//    }
    
    
    
    
    
    ////////////////////////TEST FUNCTIONS///////////////////////
    //Method to use for testing Advertise abstract  compute resources
//    public void test_AdvertiseVirtualCompute() {
//        System.out.println(" Start TEST Advertise Abstract Compute resource");
//        //post E2EComputeAllocateRequest
//        long reqid = 30;
//        //AdvertiseE2EAbstractionRequest advreq = new AdvertiseE2EAbstractionRequest(reqid, true, false); 
//        System.out.println("NBIIF --> Post AdvertiseE2EAbstractionRequest Event for compute");
//        //SingletonEventBus.getBus().post(advreq);
//    }
//    
//    //Method to use for testing Advertise abstract  compute resources
//    public void test_AdvertiseVirtualNetork() {
//        System.out.println("Start TEST Advertise Abstract Network resource");
//        //post E2EComputeAllocateRequest
//        long reqid = 40;
//        //AdvertiseE2EAbstractionRequest advreq = new AdvertiseE2EAbstractionRequest(reqid, false, true); 
//        System.out.println("NBIIF --> Post AdvertiseE2EAbstractionRequest Event for network");
//        //SingletonEventBus.getBus().post(advreq);
//    }
//    
//    
//    //Method to use for testing allocate virtual compute resources
//    public void test_AllocateVirtualCompute() {
//        System.out.println("Start TEST Allocate Virtual Allocate Compute resource");
//        //post E2EComputeAllocateRequest
//        long reqid = 10;
//        long servid = 20;
//        //VIME2EAbstractElem elem = new VIME2EAbstractElem();
//        E2EComputeAllocateRequest e2eallcompreq = new E2EComputeAllocateRequest();
//        System.out.println("NBIIF --> Post E2EComputeAllocateRequest Event");
//        SingletonEventBus.getBus().post(e2eallcompreq);
//    }
//    
//    //Method to use for testing allocate virtual network resources
//    public void test_AllocateVirtualNetwork() {
//        System.out.println("Start TEST Allocate Virtual Allocate Network resource");
//        //post E2EComputeAllocateRequest
//        long reqid = 11;
//        long servid = 20;
//        //WIME2EAbstractElem elem = new WIME2EAbstractElem();
//        E2ENetworkAllocateRequest e2eallnetreq = new E2ENetworkAllocateRequest();
//        System.out.println("NBIIF --> Post E2ENetworkAllocateRequest Event");
//        SingletonEventBus.getBus().post(e2eallnetreq);
//    }
//    
//    //Method to use for testing termination virtual compute resources
//    public void test_TerminateVirtualCompute() {
//        System.out.println("Start TEST Terminate Virtual Allocate Compute resource");
//        //post E2EComputeAllocateRequest
//        long reqid = 12;
//        long servid = 20;
//        //VIME2EAbstractElem elem = new VIME2EAbstractElem();
//        E2EComputeTerminateRequest e2etermcompreq = new E2EComputeTerminateRequest();
//        System.out.println("NBIIF --> Post E2EComputeTerminateRequest Event");
//        SingletonEventBus.getBus().post(e2etermcompreq);
//    }
//    
//    //Method to use for testing allocate virtual network resources
//    public void test_TerminateVirtualNetwork() {
//        System.out.println("Start TEST Allocate Virtual Allocate Network resource");
//        //post E2EComputeAllocateRequest
//        long reqid = 13;
//        long servid = 20;
//        //WIME2EAbstractElem elem = new WIME2EAbstractElem();
//        E2ENetworkTerminateRequest e2eallnetreq = new E2ENetworkTerminateRequest();
//        System.out.println("NBIIF --> Post E2ENetworkTerminateRequest Event");
//        SingletonEventBus.getBus().post(e2eallnetreq);
//    }
    
}
