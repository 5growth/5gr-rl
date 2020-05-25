/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.abstraction;



import com.ericsson.xenplugin.nbi.swagger.model.NfviPop;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class NfviPopAbstractionReply {
    private long reqid;
    private List<NfviPop> nfvipoplist;
    
    public NfviPopAbstractionReply(long id) {
        reqid = id;
        nfvipoplist = new ArrayList();
    }

    public NfviPopAbstractionReply(long reqid, List<NfviPop> nfvipoplist) {
        this.reqid = reqid;
        this.nfvipoplist = nfvipoplist;
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
    
    public List<NfviPop> getnfvipoplist() {
        return nfvipoplist;
    }
}
