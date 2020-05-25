/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateRequest;

/**
 *
 * @author efabuba
 */
public class Fed_NetworkAllocateElem {
    private Fed_E2ENetworkAllocateRequest input;
    private String output;
    public Fed_NetworkAllocateElem() {
        output = null;
        input = null;
    }
    
    public void setAllocateRequest(Fed_E2ENetworkAllocateRequest in) {
        input = in;
    }
    public Fed_E2ENetworkAllocateRequest getAllocateRequest() {
        return input;
    }
    public void setAllocateReply(String out) {
        output = out;
    }
    public String getAllocateReply() {
        return output;
    }
}
