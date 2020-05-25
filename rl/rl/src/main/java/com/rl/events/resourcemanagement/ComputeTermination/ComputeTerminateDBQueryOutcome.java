/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeTermination;

import java.util.ArrayList;
import java.util.List;

public class ComputeTerminateDBQueryOutcome {

    private long reqid;
    private long servid; //service identifiers 
    private List<Long> domIdList;
    private List<Boolean> outcomeList;
    private List<Integer> errorcodeList;
    private List<String> errormsgList;
    private ArrayList<Long> poplist;
    private List<String> computeIdList;

    public ComputeTerminateDBQueryOutcome() {
        reqid = 0;
        servid = 0;
        outcomeList = null;
        domIdList = null;
        errorcodeList = null;
        errormsgList = null;
        poplist = null;
        computeIdList = null;
    }

    public ComputeTerminateDBQueryOutcome(long reqid, long servid, List<Long> domIdList, List<Boolean> outcomeList, List<Integer> errorcodeList, List<String> errormsgList, ArrayList<Long> poplist, List<String> computeIdList) {
        this.reqid = reqid;
        this.servid = servid;
        this.domIdList = domIdList;
        this.outcomeList = outcomeList;
        this.errorcodeList = errorcodeList;
        this.errormsgList = errormsgList;
        this.poplist = poplist;
        this.computeIdList = computeIdList;
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

}
