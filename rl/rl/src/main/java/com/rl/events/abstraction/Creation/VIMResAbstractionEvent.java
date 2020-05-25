/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Creation;

import com.rl.common.objects.ComputeResElem;
import com.rl.common.objects.MapResources;
import com.rl.common.objects.NetworkResElem;
import java.util.*;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;

/**
 *
 * @author user
 */
public class VIMResAbstractionEvent {
    private long id; //id of the VIM domain
    List<NfviPop> poplist;
    List<ResourceZone> zonelist;
    List<ComputeResElem> resinfolist;
    List <MapResources> compresmap;
    List<NetworkResElem> netinfolist;
    List <MapResources> netresmap;
    
    public VIMResAbstractionEvent() {
        id = 0;
        poplist = new ArrayList();
        zonelist = new ArrayList();
        resinfolist = new ArrayList();
        compresmap = new ArrayList();
        netinfolist = new ArrayList();
        netresmap = new ArrayList();
    }
    public VIMResAbstractionEvent(long idval) {
        id = idval;
        poplist = new ArrayList();
        zonelist = new ArrayList();
        resinfolist = new ArrayList();
        compresmap = new ArrayList();
        netinfolist = new ArrayList();
        netresmap = new ArrayList();
    }
    //get/set function
    public long getId() {
        return id;
    } 
    public void setId(long val) {
        id = val;
    }
    public List<NfviPop> getPopList() {
        return poplist;
    }
    public void setPopList(List<NfviPop> plist) {
        poplist = plist;
    }
    public NfviPop getPopElem(int i) {
        if (poplist.isEmpty()) 
            return new NfviPop();
        return poplist.get(i);
    }
    public void setPopElem(NfviPop domelem) {
        poplist.add(domelem);
    }
    public List<ResourceZone> getZoneList() {
        return zonelist;
    }
    public void setZoneList(List<ResourceZone> plist) {
        zonelist = plist;
    }
    public ResourceZone getZoneElem(int i) {
        if (zonelist.isEmpty()) 
            return new ResourceZone();
        return zonelist.get(i);
    }
    public void setZoneElem(ResourceZone domelem) {
        zonelist.add(domelem);
    }
    public List<ComputeResElem> getComputeResList() {
        return resinfolist;
    }
    public void setComputeResList(List<ComputeResElem> plist) {
        resinfolist = plist;
    }
    public ComputeResElem getComputeResElem(int i) {
        if (resinfolist.isEmpty()) 
            return new ComputeResElem();
        return resinfolist.get(i);
    }
    public void setComputeResElem(ComputeResElem domelem) {
        resinfolist.add(domelem);
    }
    public List<MapResources> getCompMapList() {
        return compresmap;
    }
    public void setCompMapList(List<MapResources> plist) {
        compresmap = plist;
    }
    public MapResources getCompMapElem(int i) {
        if (compresmap.isEmpty()) 
            return null;
        return compresmap.get(i);
    }
    public void setCompMapElem(MapResources domelem) {
        compresmap.add(domelem);
    }
    public List<NetworkResElem> getNetworkResList() {
        return netinfolist;
    }
    public void setNetworkResList(List<NetworkResElem> plist) {
        netinfolist = plist;
    }
    public NetworkResElem getNetworkResElem(int i) {
        if (netinfolist.isEmpty()) 
            return new NetworkResElem();
        return netinfolist.get(i);
    }
    public void setNetworkResElem(NetworkResElem domelem) {
        netinfolist.add(domelem);
    }
    public List<MapResources> getNetworkMapList() {
        return netresmap;
    }
    public void setNetworkMapList(List<MapResources> plist) {
        netresmap = plist;
    }
    public MapResources getNetworkMapElem(int i) {
        if (netresmap.isEmpty()) 
            return null;
        return netresmap.get(i);
    }
    public void setNetworkMapElem(MapResources domelem) {
        netresmap.add(domelem);
    }

}
