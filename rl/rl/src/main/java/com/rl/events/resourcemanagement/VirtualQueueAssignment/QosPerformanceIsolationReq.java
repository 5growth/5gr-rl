/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.VirtualQueueAssignment;

import com.rl.resourcemanagement.qosperfomanceisolation.model.AssignQosQueueRequest;


public class QosPerformanceIsolationReq {

    private long reqid;
    private String servid; //service identifiers
    private long domid; //id of vim domain to contact
    //private long computeservid;
    private AssignQosQueueRequest qosreq;
    private long nfvipopid;

    public QosPerformanceIsolationReq(long reqid, String servid, long domid, AssignQosQueueRequest qosreq, long nfvipopid) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.qosreq = qosreq;
        this.nfvipopid = nfvipopid;
    }



    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public AssignQosQueueRequest getQosQueuereq() {
        return qosreq;
    }

    public void setQosQueuereq(AssignQosQueueRequest qosreq) {
        this.qosreq = qosreq;
    }


    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }


}
