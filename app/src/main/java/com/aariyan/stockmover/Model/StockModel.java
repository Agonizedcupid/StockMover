package com.aariyan.stockmover.Model;

public class StockModel {

    private String shelf, ID, DeviceGUI_tick, Qty, barcode, Expiry, productCode, strTransactionType;

    public StockModel() {
    }

    public StockModel(String ID, String shelf, String deviceGUI_tick, String qty, String barcode, String expiry, String productCode, String strTransactionType) {
        this.ID = ID;
        this.shelf = shelf;
        DeviceGUI_tick = deviceGUI_tick;
        Qty = qty;
        this.barcode = barcode;
        Expiry = expiry;
        this.productCode = productCode;
        this.strTransactionType = strTransactionType;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
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

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getExpiry() {
        return Expiry;
    }

    public void setExpiry(String expiry) {
        Expiry = expiry;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStrTransactionType() {
        return strTransactionType;
    }

    public void setStrTransactionType(String strTransactionType) {
        this.strTransactionType = strTransactionType;
    }
}
