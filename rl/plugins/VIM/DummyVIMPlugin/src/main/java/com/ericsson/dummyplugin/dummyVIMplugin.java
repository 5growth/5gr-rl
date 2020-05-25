/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin;

import com.ericsson.dummyplugin.events.abstraction.Parsedomainlist;
import com.ericsson.dummyplugin.events.abstraction.StopServer;
import com.ericsson.dummyplugin.nbi.swagger.api.ComputeResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.HealthzApi;
import com.ericsson.dummyplugin.nbi.swagger.api.InformationApi;
import com.ericsson.dummyplugin.nbi.swagger.api.MTPIF;
import com.ericsson.dummyplugin.nbi.swagger.api.NetworkResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.QuotasApi;
import com.ericsson.dummyplugin.nbi.swagger.api.ReservationsApi;
import com.ericsson.dummyplugin.nbi.swagger.api.SoftwareImagesApi;
import com.ericsson.dummyplugin.sbi.XenIF;
import com.google.common.eventbus.EventBus;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractComputeOperateResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractComputeResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractFederatedResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractNetworkApi;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractNetworkResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractRadioCoverageareaApi;
import com.ericsson.dummyplugin.nbi.swagger.api.AbstractResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.ComputeOperateResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.MecappApi;
import com.ericsson.dummyplugin.nbi.swagger.api.MfsApi;
import com.ericsson.dummyplugin.nbi.swagger.api.PhysicalResourcesApi;
import com.ericsson.dummyplugin.nbi.swagger.api.PnfsApi;
import com.ericsson.dummyplugin.nbi.swagger.api.ServiceApi;
//import com.mtp.abstraction.E2EAbstractionLogic;
//import com.mtp.abstraction.ResourceSelectionLogic;
//import com.mtp.common.DatabaseDriver;
//import com.mtp.extinterface.sbi.SBIIF;
//import com.mtp.resourcemanagement.ResouceOrchestration;
////import java.lang.String;
//import com.mtp.events.abstraction.Creation.Parsedomainlist;
//import com.mtp.events.abstraction.Creation.StopServer;
//import com.mtp.extinterface.nbi.swagger.api.ComputeResourcesApi;
//import com.mtp.extinterface.nbi.swagger.api.HealthzApi;
//import com.mtp.extinterface.nbi.swagger.api.InformationApi;
//import com.mtp.extinterface.nbi.swagger.api.NBIIF;
//import com.mtp.extinterface.nbi.swagger.api.NetworkResourcesApi;
//import com.mtp.extinterface.nbi.swagger.api.QuotasApi;
//import com.mtp.extinterface.nbi.swagger.api.ReservationsApi;
//import com.mtp.extinterface.nbi.swagger.api.SoftwareImagesApi;
import java.util.Scanner;
//import com.eranplugin.DeadEventListener;
/**
 *
 * @author user
 */
public class dummyVIMplugin {
    public static void main(String[] args) {
        boolean onexit = false;
        boolean stub_en = true; //enable stubmode
        Scanner scanner = new Scanner(System.in);
        String prompt;
        //Create eventbus
        System.out.println("XEN PLUGIN START");
        EventBus evbus;
        MTPIF mtpif = new MTPIF(args[1], args[2]);
        evbus = SingletonEventBus.getBus();
        
//        //see if STUB_MODE is set as properties
//        String stubmode;
//        stubmode = System.getProperty("STUB_ENABLE");
//        
//        if ((stubmode ==null) || (!stubmode.equals("yes"))) {
//            //Execute MTP using IFA005 as SBI
//            System.out.println("///////STUB MODE DISABLE /////////");
//            stub_en = false;
//        } else {
//            System.out.println("///////STUB MODE ENABLE /////////");
//        }
        
        //register all modules
        System.out.println("XEN PLUGIN registering modules ...");

        evbus.register(mtpif);
        evbus.register(new XenIF());

        //register jaxrs api
        //register jaxrs api
        evbus.register(new AbstractComputeOperateResourcesApi());
        evbus.register(new AbstractComputeResourcesApi());
        evbus.register(new AbstractFederatedResourcesApi());
        evbus.register(new AbstractNetworkApi());
        evbus.register(new AbstractNetworkResourcesApi());
        evbus.register(new AbstractRadioCoverageareaApi());
        evbus.register(new AbstractResourcesApi());
        evbus.register(new ComputeOperateResourcesApi());
        evbus.register(new ComputeResourcesApi());
        evbus.register(new HealthzApi());
        evbus.register(new InformationApi());
        evbus.register(new MecappApi());
        evbus.register(new MfsApi());
        evbus.register(new NetworkResourcesApi());
        evbus.register(new PhysicalResourcesApi());
        evbus.register(new PnfsApi());
        evbus.register(new QuotasApi());
        evbus.register(new ReservationsApi());
        evbus.register(new ServiceApi());
        evbus.register(new SoftwareImagesApi());
        
                
        //class to register dead event
        evbus.register (new DeadEventListener());
        //register deadevent for debug
        System.out.println("Done !!!");
        System.out.println("Start acquiring abstract domain info... !!!");
        String domfile = args[0];
        Parsedomainlist domlist = new Parsedomainlist(domfile);
        evbus.post(domlist);
        //System.out.println("MTP ready ");
        System.out.println("Start MTP feature TEST:");
        while (!onexit) {
            
//            prompt = scanner.next();
//            if (prompt.equals("EXIT")) {
//                StopServer servreq = new StopServer();
//                evbus.post(servreq);
//                onexit = true;
//                continue;
//            } 
//            if (prompt.equals("ADVCOMPTEST")) {
//                soif.test_AdvertiseVirtualCompute();
//                continue;
//            }
//            if (prompt.equals("ADVNETTEST")) {
//                soif.test_AdvertiseVirtualNetork();
//                continue;
//            }
//            if (prompt.equals("ALLCOMPTEST")) {
//                soif.test_AllocateVirtualCompute();
//                continue;
//            }
//            if (prompt.equals("ALLNETTEST")) {
//                soif.test_AllocateVirtualNetwork();
//                continue;
//            }
//            if (prompt.equals("TERMCOMPTEST")) {
//                soif.test_TerminateVirtualCompute();
//                continue;
//            }
//            if (prompt.equals("TERMNETTEST")) {
//                soif.test_TerminateVirtualNetwork();
//            }
        }
        
    }
}
