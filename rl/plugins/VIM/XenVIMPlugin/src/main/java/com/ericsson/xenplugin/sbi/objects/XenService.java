/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.sbi.objects;

import com.xensource.xenapi.Connection;
import com.xensource.xenapi.Host;
import com.xensource.xenapi.PIF;
import com.xensource.xenapi.SR;
import com.xensource.xenapi.VM;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author efabuba
 */
public class XenService {
    
    private String zoneid;
    private String master;
    private String username;
    private String password;
    private String floatingip;
    //XEN variables
    protected Connection connection;
    /* Maps of Virtual Machine Robots controller system */
    private Map <VM, VM.Record> mapVmPool;
    /* record all host in a DC */
    private Map <Host, Host.Record> mapHostPool;
    /* record SR in the pool */
    private Map <SR,SR.Record> mapSrPool;
    /* record PIF in the pool */
    private Map <PIF,PIF.Record> mapPifPool;

    public XenService(String zoneid, String master, String username, String password, String floatingip) {
        this.zoneid = zoneid;
        this.master = master;
        this.username = username;
        this.password = password;
        this.floatingip = floatingip;
        mapVmPool = new HashMap();
        mapHostPool = new HashMap();
        mapSrPool = new HashMap();
        mapPifPool = new HashMap();
    }

    public String getZoneid() {
        return zoneid;
    }

    public String getMaster() {
        return master;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFloatingip() {
        return floatingip;
    }

    public void setFloatingip(String floatingip) {
        this.floatingip = floatingip;
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Map<VM, VM.Record> getMapVmPool() {
        return mapVmPool;
    }

    public void setMapVmPool(Map<VM, VM.Record> mapVmPool) {
        this.mapVmPool = mapVmPool;
    }

    public Map<Host, Host.Record> getMapHostPool() {
        return mapHostPool;
    }

    public void setMapHostPool(Map<Host, Host.Record> mapHostPool) {
        this.mapHostPool = mapHostPool;
    }

    public Map<SR, SR.Record> getMapSrPool() {
        return mapSrPool;
    }

    public void setMapSrPool(Map<SR, SR.Record> mapSrPool) {
        this.mapSrPool = mapSrPool;
    }

    public Map<PIF, PIF.Record> getMapPifPool() {
        return mapPifPool;
    }

    public void setMapPifPool(Map<PIF, PIF.Record> mapPifPool) {
        this.mapPifPool = mapPifPool;
    }
    

    
    
}
    
