/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.placement;

import com.rl.common.objects.LocalPAConfig;

/**
 *
 * @author efabuba
 */
public class PANetworkConfig {
    private LocalPAConfig netpaconfig;

    public PANetworkConfig(LocalPAConfig netpaconfig) {
        this.netpaconfig = netpaconfig;
    }

    public LocalPAConfig getNetpaconfig() {
        return netpaconfig;
    }

    public void setNetpaconfig(LocalPAConfig netpaconfig) {
        this.netpaconfig = netpaconfig;
    }
    
    
}
