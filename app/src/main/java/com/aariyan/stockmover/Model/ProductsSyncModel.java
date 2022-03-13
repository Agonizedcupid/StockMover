package com.aariyan.stockmover.Model;

public class ProductsSyncModel {
    private String PastelCode,Barcode;
    public ProductsSyncModel(){}

    public ProductsSyncModel(String pastelCode, String barcode) {
        PastelCode = pastelCode;
        Barcode = barcode;
    }

    public String getPastelCode() {
        return PastelCode;
    }

    public void setPastelCode(String pastelCode) {
        PastelCode = pastelCode;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }
}
