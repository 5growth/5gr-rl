
package com.sssa.wimplugin;

import com.google.common.eventbus.EventBus;

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
