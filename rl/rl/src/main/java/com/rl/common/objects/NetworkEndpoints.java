/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

/**
 *
 * @author efabuba
 */
public class NetworkEndpoints {
    private String IngressIp;
    private String EgressIp;
    private String IngressPort;
    private String EgressPort;

    public NetworkEndpoints(String IngressIp, String EgressIp, String IngressPort, String EgressPort) {
        this.IngressIp = IngressIp;
        this.EgressIp = EgressIp;
        this.IngressPort = IngressPort;
        this.EgressPort = EgressPort;
    }

    public String getIngressIp() {
        return IngressIp;
    }

    public void setIngressIp(String IngressIp) {
        this.IngressIp = IngressIp;
    }

    public String getEgressIp() {
        return EgressIp;
    }

    public void setEgressIp(String EgressIp) {
        this.EgressIp = EgressIp;
    }

    public String getIngressPort() {
        return IngressPort;
    }

    public void setIngressPort(String IngressPort) {
        this.IngressPort = IngressPort;
    }

    public String getEgressPort() {
        return EgressPort;
    }

    public void setEgressPort(String EgressPort) {
        this.EgressPort = EgressPort;
    }
    
    
    
}
