
package com.sssa.wimplugin;

import com.sssa.wimplugin.events.abstraction.Parsedomainlist;
import com.sssa.wimplugin.events.abstraction.StopServer;
import com.sssa.wimplugin.nbi.swagger.api.AbstractComputeResourcesApi;
import com.sssa.wimplugin.nbi.swagger.api.AbstractNetworkApi;
import com.sssa.wimplugin.nbi.swagger.api.AbstractNetworkResourcesApi;
import com.sssa.wimplugin.nbi.swagger.api.AbstractResourcesApi;
import com.sssa.wimplugin.nbi.swagger.api.ComputeResourcesApi;
import com.sssa.wimplugin.nbi.swagger.api.HealthzApi;
import com.sssa.wimplugin.nbi.swagger.api.InformationApi;
import com.sssa.wimplugin.nbi.swagger.api.MTPIF;
import com.sssa.wimplugin.nbi.swagger.api.NetworkResourcesApi;
import com.sssa.wimplugin.nbi.swagger.api.QuotasApi;
import com.sssa.wimplugin.nbi.swagger.api.ReservationsApi;
import com.sssa.wimplugin.nbi.swagger.api.SoftwareImagesApi;
import com.sssa.wimplugin.sbi.SDNDomain;
import com.google.common.eventbus.EventBus;
import java.io.IOException;
import java.util.Scanner;
//import com.eranplugin.DeadEventListener;


public class WimSdnplugin {
    
    public static void main(String[] args) throws IOException {
        
        if(args.length > 0)
        {
        boolean onexit = false;
        Scanner scanner = new Scanner(System.in);
        String prompt;
        //Create eventbus
        System.out.println("WIM SDN PLUGIN START");
        EventBus evbus;
        MTPIF mtpif = new MTPIF(args[1], args[2]);
        evbus = SingletonEventBus.getBus();
        
        evbus.register(mtpif);
        evbus.register(new SDNDomain());

        //register jaxrs api
        evbus.register(new ComputeResourcesApi());
        evbus.register(new HealthzApi());
        evbus.register(new InformationApi());
        evbus.register(new NetworkResourcesApi());
        evbus.register(new QuotasApi());
        evbus.register(new ReservationsApi());
        evbus.register(new SoftwareImagesApi());
        evbus.register(new AbstractComputeResourcesApi());
        evbus.register(new AbstractNetworkResourcesApi());
        evbus.register(new AbstractNetworkApi());
        evbus.register(new AbstractResourcesApi());
        
                
        //class to register dead event
        evbus.register (new DeadEventListener());
        //register deadevent for debug
        System.out.println("Start acquiring SDN domain info... !!!");

        
        String domfile = args[0];
        Parsedomainlist domlist = new Parsedomainlist(domfile);
        
        
        evbus.post(domlist);
        //System.out.println("MTP ready ");
        System.out.println("Start MTP feature TEST:");
        while (!onexit) {

              System.out.println("-- Type EXIT to exit the program:");
            
            prompt = scanner.next();
            if (prompt.equals("EXIT")) {
                StopServer servreq = new StopServer();
                evbus.post(servreq);
                onexit = true;
//                continue;
            } 
//            
        }
        
    }//WIM
    else
    {
    
        System.out.println("WIM - Prometheus Exporter");
        com.sssa.wimplugin.prometheus.Exporter exp = new com.sssa.wimplugin.prometheus.Exporter(8877);
    
    }//Prometheus Platform
    
          }
}
