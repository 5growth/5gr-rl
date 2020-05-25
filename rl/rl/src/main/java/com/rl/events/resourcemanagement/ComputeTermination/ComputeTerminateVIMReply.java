/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeTermination;

import java.util.ArrayList;
import java.util.List;

public class ComputeTerminateVIMReply {

    private long reqid;
    private long servid; //service identifiers 
    private List<Long> domIdList;
    private List<Boolean> outcomeList;
    private ArrayList<Long> poplist;
    private List<String> computeIdList;
    private List<Integer> errorcodeList;
    private List<String> errormsgList;

    public ComputeTerminateVIMReply(long reqid, long servid, List<Long> domIdList, List<Boolean> outcomeList, ArrayList<Long> poplist, List<String> computeIdList, List<Integer> errorcodeList, List<String> errormsgList) {
        this.reqid = reqid;
        this.servid = servid;
        this.domIdList = domIdList;
        this.outcomeList = outcomeList;
        this.poplist = poplist;
        this.computeIdList = computeIdList;
        this.errorcodeList = errorcodeList;
        this.errormsgList = errormsgList;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public long getServid() {
        return servid;
    }

    public void setServid(long servid) {
        this.servid = servid;
    }

    public List<Long> getDomIdList() {
        return domIdList;
    }

    public void setDomIdList(List<Long> domIdList) {
        this.domIdList = domIdList;
    }

    public List<Boolean> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(List<Boolean> outcomeList) {
        this.outcomeList = outcomeList;
    }

    public ArrayList<Long> getPoplist() {
        return poplist;
    }

    public void setPoplist(ArrayList<Long> poplist) {
        this.poplist = poplist;
    }

    public List<String> getComputeIdList() {
        return computeIdList;
    }

    public void setComputeIdList(List<String> computeIdList) {
        this.computeIdList = computeIdList;
    }

    public List<Integer> getErrorcodeList() {
        return errorcodeList;
    }

    public void setErrorcodeList(List<Integer> errorcodeList) {
        this.errorcodeList = errorcodeList;
    }

    public List<String> getErrormsgList() {
        return errormsgList;
    }

    public void setErrormsgList(List<String> errormsgList) {
        this.errormsgList = errormsgList;
    }

  

}
