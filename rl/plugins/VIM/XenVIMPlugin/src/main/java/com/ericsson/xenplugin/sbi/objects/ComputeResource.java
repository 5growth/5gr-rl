/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class ComputeResource {
    private String id;
    private String type;
    private String acc_capability;
    private String memsize;
    private String subscr_policy;
    private boolean numaSupported;
    private String architecture;
    private String numvcpu;
    private String cpuclock;
    private boolean pinningSupported;
    private String total;
    private String available;
    private String reserved;
    private String allocated;

    public ComputeResource() {
        
    }
    
    public ComputeResource(String id, String type, String acc_capability, String memsize, String subscr_policy, boolean numaSupported, String architecture, String numvcpu, String cpuclock, boolean pinningSupported, String total, String available, String reserved, String allocated) {
        this.id = id;
        this.type = type;
        this.acc_capability = acc_capability;
        this.memsize = memsize;
        this.subscr_policy = subscr_policy;
        this.numaSupported = numaSupported;
        this.architecture = architecture;
        this.numvcpu = numvcpu;
        this.cpuclock = cpuclock;
        this.pinningSupported = pinningSupported;
        this.total = total;
        this.available = available;
        this.reserved = reserved;
        this.allocated = allocated;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcc_capability() {
        return acc_capability;
    }

    public void setAcc_capability(String acc_capability) {
        this.acc_capability = acc_capability;
    }

    public String getMemsize() {
        return memsize;
    }

    public void setMemsize(String memsize) {
        this.memsize = memsize;
    }

    public String getSubscr_policy() {
        return subscr_policy;
    }

    public void setSubscr_policy(String subscr_policy) {
        this.subscr_policy = subscr_policy;
    }

    public boolean isNumaSupported() {
        return numaSupported;
    }

    public void setNumaSupported(boolean numaSupported) {
        this.numaSupported = numaSupported;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getNumvcpu() {
        return numvcpu;
    }

    public void setNumvcpu(String numvcpu) {
        this.numvcpu = numvcpu;
    }

    public String getCpuclock() {
        return cpuclock;
    }

    public void setCpuclock(String cpuclock) {
        this.cpuclock = cpuclock;
    }

    public boolean isPinningSupported() {
        return pinningSupported;
    }

    public void setPinningSupported(boolean pinningSupported) {
        this.pinningSupported = pinningSupported;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getAllocated() {
        return allocated;
    }

    public void setAllocated(String allocated) {
        this.allocated = allocated;
    }
    

    

    
    
}  

