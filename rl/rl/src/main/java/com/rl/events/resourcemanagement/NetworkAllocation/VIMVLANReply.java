/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class VIMVLANReply {
    private long logicalLinkId;
    private ArrayList<Long> vlans; //contains domain managing the VIM abstract resources

    public VIMVLANReply(long logicalLinkId, ArrayList<Long> vlans) {
        this.logicalLinkId = logicalLinkId;
        this.vlans = vlans;
    }

    public long getLogicalLinkId() {
        return logicalLinkId;
    }

    public void setLogicalLinkId(long logicalLinkId) {
        this.logicalLinkId = logicalLinkId;
    }

    public ArrayList<Long> getVlans() {
        return vlans;
    }

    public void setVlans(ArrayList<Long> vlans) {
        this.vlans = vlans;
    }
}
