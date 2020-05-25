/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Creation;

import com.rl.common.objects.ComputeResElem;
import com.rl.common.objects.MapResources;
import com.rl.common.objects.NetworkResElem;
import com.rl.extinterface.nbi.swagger.model.MFInfo;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.PNFInfo;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.RadioCoverageAreaListInnerRadioCoverageAreaInfo;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class RadioResAbstractionEvent {
    private long id; //id of the VIM domain
    List<NfviPop> poplist;
    List<ResourceZone> zonelist;
    List<ComputeResElem> resinfolist;
    List<MapResources> compresmap;
    List<NetworkResElem> netinfolist;
    List<MapResources> netresmap;
    List<RadioCoverageAreaListInnerRadioCoverageAreaInfo> radioinfolist;
    List<PNFInfo> pnflist;
    List<MFInfo> mflist;
    
    public RadioResAbstractionEvent(long val) {
        id = val;
        this.poplist = new ArrayList();
        this.zonelist = new ArrayList();
        this.resinfolist = new ArrayList();
        this.compresmap = new ArrayList();
        this.netinfolist = new ArrayList();
        this.netresmap = new ArrayList();
        this.radioinfolist = new ArrayList();
        this.pnflist = new ArrayList();
        this.mflist = new ArrayList();
    }

    public RadioResAbstractionEvent(long id, List<NfviPop> poplist, List<ResourceZone> zonelist, List<ComputeResElem> resinfolist, List<MapResources> compresmap, List<NetworkResElem> netinfolist, List<MapResources> netresmap, List<RadioCoverageAreaListInnerRadioCoverageAreaInfo> radioinfolist, List<PNFInfo> pnflist, List<MFInfo> mflist) {
        this.id = id;
        this.poplist = poplist;
        this.zonelist = zonelist;
        this.resinfolist = resinfolist;
        this.compresmap = compresmap;
        this.netinfolist = netinfolist;
        this.netresmap = netresmap;
        this.radioinfolist = radioinfolist;
        this.pnflist = pnflist;
        this.mflist = mflist;
    }

 

    public List<NfviPop> getPoplist() {
        return poplist;
    }

    public void setPoplist(List<NfviPop> poplist) {
        this.poplist = poplist;
    }

    public List<ResourceZone> getZonelist() {
        return zonelist;
    }

    public void setZonelist(List<ResourceZone> zonelist) {
        this.zonelist = zonelist;
    }

    public List<ComputeResElem> getResinfolist() {
        return resinfolist;
    }

    public void setResinfolist(List<ComputeResElem> resinfolist) {
        this.resinfolist = resinfolist;
    }

    public List<MapResources> getCompresmap() {
        return compresmap;
    }

    public void setCompresmap(List<MapResources> compresmap) {
        this.compresmap = compresmap;
    }

    public List<NetworkResElem> getNetinfolist() {
        return netinfolist;
    }

    public void setNetinfolist(List<NetworkResElem> netinfolist) {
        this.netinfolist = netinfolist;
    }

    public List<MapResources> getNetresmap() {
        return netresmap;
    }

    public void setNetresmap(List<MapResources> netresmap) {
        this.netresmap = netresmap;
    }

    public List<RadioCoverageAreaListInnerRadioCoverageAreaInfo> getRadioinfolist() {
        return radioinfolist;
    }

    public void setRadioinfolist(List<RadioCoverageAreaListInnerRadioCoverageAreaInfo> radioinfolist) {
        this.radioinfolist = radioinfolist;
    }

    public List<PNFInfo> getPnflist() {
        return pnflist;
    }

    public void setPnflist(List<PNFInfo> pnflist) {
        this.pnflist = pnflist;
    }

    public List<MFInfo> getMflist() {
        return mflist;
    }

    public void setMflist(List<MFInfo> mflist) {
        this.mflist = mflist;
    }
    
    
    
    public void setId(long val) {
        id = val;
    }
    public long getId() {
        return id;
    }
    
    public NfviPop getPopElem(int i) {
        if (poplist.isEmpty()) 
            return new NfviPop();
        return poplist.get(i);
    }
    public void setPopElem(NfviPop domelem) {
        poplist.add(domelem);
    }
    
    public ResourceZone getZoneElem(int i) {
        if (zonelist.isEmpty()) 
            return new ResourceZone();
        return zonelist.get(i);
    }
    public void setZoneElem(ResourceZone domelem) {
        zonelist.add(domelem);
    }
    
    public ComputeResElem getComputeResElem(int i) {
        if (resinfolist.isEmpty()) 
            return new ComputeResElem();
        return resinfolist.get(i);
    }
    public void setComputeResElem(ComputeResElem domelem) {
        resinfolist.add(domelem);
    }

    public MapResources getCompMapElem(int i) {
        if (compresmap.isEmpty()) 
            return null;
        return compresmap.get(i);
    }
    public void setCompMapElem(MapResources domelem) {
        compresmap.add(domelem);
    }

    public NetworkResElem getNetworkResElem(int i) {
        if (netinfolist.isEmpty()) 
            return new NetworkResElem();
        return netinfolist.get(i);
    }
    public void setNetworkResElem(NetworkResElem domelem) {
        netinfolist.add(domelem);
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
