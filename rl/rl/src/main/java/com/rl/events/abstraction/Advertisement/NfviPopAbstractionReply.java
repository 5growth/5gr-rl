/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;


import com.rl.extinterface.nbi.swagger.model.NfviPop;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class NfviPopAbstractionReply {
    private long reqid;
    private boolean compute_flag;
    private boolean network_flag;
    private ArrayList<NfviPop> nfvipoplist;
    
    public NfviPopAbstractionReply(long id, boolean cflag, boolean nflag) {
        reqid = id;
        compute_flag = cflag;
        network_flag = nflag;
        nfvipoplist = new ArrayList();
    }
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void addnfvipop(NfviPop el) {
        nfvipoplist.add(el);
    }
    
    public ArrayList<NfviPop> getnfvipoplist() {
        return nfvipoplist;
    }
    
    public void setComputeFlag(boolean id) {
        compute_flag = id;
    }
    
    public boolean getComputeFlag() {
        return compute_flag;
    }
    
    public void setNetworkFlag(boolean id) {
        network_flag = id;
    }
    
    public boolean getNetworkFlag() {
        return network_flag;
    }
}
