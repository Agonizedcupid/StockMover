package com.aariyan.stockmover.Model;

public class LocationSyncModel {
    private String intBinLocationId,strBinLocationName,intaislenumber;

    public LocationSyncModel(){};

    public LocationSyncModel(String intBinLocationId, String strBinLocationName, String intaislenumber) {
        this.intBinLocationId = intBinLocationId;
        this.strBinLocationName = strBinLocationName;
        this.intaislenumber = intaislenumber;
    }

    public String getIntBinLocationId() {
        return intBinLocationId;
    }

    public void setIntBinLocationId(String intBinLocationId) {
        this.intBinLocationId = intBinLocationId;
    }

    public String getStrBinLocationName() {
        return strBinLocationName;
    }

    public void setStrBinLocationName(String strBinLocationName) {
        this.strBinLocationName = strBinLocationName;
    }

    public String getIntaislenumber() {
        return intaislenumber;
    }

    public void setIntaislenumber(String intaislenumber) {
        this.intaislenumber = intaislenumber;
    }
}
