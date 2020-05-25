/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;

/**
 *
 * @author efabuba
 */
public class NetworkAllocateElem {
    private E2ENetworkAllocateRequest input;
    private String output;
    public NetworkAllocateElem() {
        output = null;
        input = null;
    }
    
    public void setAllocateRequest(E2ENetworkAllocateRequest in) {
        input = in;
    }
    public E2ENetworkAllocateRequest getAllocateRequest() {
        return input;
    }
    public void setAllocateReply(String out) {
        output = out;
    }
    public String getAllocateReply() {
        return output;
    }
}
