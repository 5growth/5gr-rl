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
public class LocalPAConfig {
    private String ip; //ip to use for connect to local PA algorithm
    private long port; //port to use for connect to ocal PA algorithm
    private String name;
    private String status;

    public LocalPAConfig(String ip, long port, String name, String status) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.status = status;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
