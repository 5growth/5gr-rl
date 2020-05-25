/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin;

import com.google.common.eventbus.EventBus;
/**
 *
 * @author fhuser
 */
public class SingletonEventBus {
     private static EventBus bus;
    
    private SingletonEventBus(){}
    
    public static synchronized EventBus getBus(){
        if(bus == null){
            bus = new EventBus("MTP_Dispatcher");
        }
        return bus;
    }
}
