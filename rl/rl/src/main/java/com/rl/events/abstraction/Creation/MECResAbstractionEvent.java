/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Creation;

import com.rl.extinterface.nbi.swagger.model.MECRegionInfo;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class MECResAbstractionEvent {
    private long id; //id of the MEC domain
    private List<MECRegionInfo> mecregionlist;

    public MECResAbstractionEvent(long id, List<MECRegionInfo> mecregionlist) {
        this.id = id;
        this.mecregionlist = mecregionlist;
    }
            
    

    
    public void setId(long val) {
        id = val;
    }
    public long getId() {
        return id;
    }

    public List<MECRegionInfo> getMecregionlist() {
        return mecregionlist;
    }

    public void setMecregionlist(List<MECRegionInfo> mecregionlist) {
        this.mecregionlist = mecregionlist;
    }
    
}
