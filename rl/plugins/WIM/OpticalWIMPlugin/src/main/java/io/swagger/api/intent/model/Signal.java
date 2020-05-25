package io.swagger.api.intent.model;


public class Signal {

    /*
    "channelSpacing": "CHL_50GHZ",
    "gridType": "DWDM",
    "spacingMultiplier": 12,
    "slotGranularity": 4
    */

    private String channelSpacing = "CHL_50GHZ";
    private String gridType = "DWDM";
    private int spacingMultiplier = 1;
    private int slotGranularity = 4;

    //channelSpacing
    public void setchannelSpacing(String device) {
        this.channelSpacing = device;
    }
    public String getchannelSpacing() {
        return channelSpacing;
    }
    //gridType
    public void setgridType(String val) {
        this.gridType = val;
    }
    public String getgridType() {
        return gridType;
    }
    //spacingMultiplier
    public void setspacingMultiplier(int val) {
        this.spacingMultiplier = val;
    }
    public int getspacingMultiplier() {
        return spacingMultiplier;
    }
    //spacingMultiplier
    public void setslotGranularity(int val) {
        this.slotGranularity = val;
    }
    public int getslotGranularity() {
        return slotGranularity;
    }


}
