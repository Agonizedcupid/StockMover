package com.aariyan.stockmover.Model;

public class PostLines {
    private String shelffrom,ID,DeviceGUI_tick,productfrombarcode,Qty,shelfto,producttobarcode,confirmqty,Expiry,productfromcode,producttocode;

    public PostLines() {

    }

    public PostLines(String shelffrom, String ID, String deviceGUI_tick, String productfrombarcode, String qty, String shelfto, String producttobarcode, String confirmqty, String expiry, String productfromcode, String producttocode) {
        this.shelffrom = shelffrom;
        this.ID = ID;
        DeviceGUI_tick = deviceGUI_tick;
        this.productfrombarcode = productfrombarcode;
        Qty = qty;
        this.shelfto = shelfto;
        this.producttobarcode = producttobarcode;
        this.confirmqty = confirmqty;
        Expiry = expiry;
        this.productfromcode = productfromcode;
        this.producttocode = producttocode;
    }

    public String getShelffrom() {
        return shelffrom;
    }

    public void setShelffrom(String shelffrom) {
        this.shelffrom = shelffrom;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDeviceGUI_tick() {
        return DeviceGUI_tick;
    }

    public void setDeviceGUI_tick(String deviceGUI_tick) {
        DeviceGUI_tick = deviceGUI_tick;
    }

    public String getProductfrombarcode() {
        return productfrombarcode;
    }

    public void setProductfrombarcode(String productfrombarcode) {
        this.productfrombarcode = productfrombarcode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getShelfto() {
        return shelfto;
    }

    public void setShelfto(String shelfto) {
        this.shelfto = shelfto;
    }

    public String getProducttobarcode() {
        return producttobarcode;
    }

    public void setProducttobarcode(String producttobarcode) {
        this.producttobarcode = producttobarcode;
    }

    public String getConfirmqty() {
        return confirmqty;
    }

    public void setConfirmqty(String confirmqty) {
        this.confirmqty = confirmqty;
    }

    public String getExpiry() {
        return Expiry;
    }

    public void setExpiry(String expiry) {
        Expiry = expiry;
    }

    public String getProductfromcode() {
        return productfromcode;
    }

    public void setProductfromcode(String productfromcode) {
        this.productfromcode = productfromcode;
    }

    public String getProducttocode() {
        return producttocode;
    }

    public void setProducttocode(String producttocode) {
        this.producttocode = producttocode;
    }
}
