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
public class PAComputeConfig {
    private LocalPAConfig comppaconfig;

    public PAComputeConfig(LocalPAConfig comppaconfig) {
        this.comppaconfig = comppaconfig;
    }

    public LocalPAConfig getComppaconfig() {
        return comppaconfig;
    }

    public void setComppaconfig(LocalPAConfig comppaconfig) {
        this.comppaconfig = comppaconfig;
    }


    
}
