package com.aariyan.stockmover.Model;

public class ProductsSyncModel {
    private String PastelCode,Barcode,ProductDescription;
    public ProductsSyncModel(){}

    public ProductsSyncModel(String pastelCode, String barcode, String productDescription) {
        PastelCode = pastelCode;
        Barcode = barcode;
        ProductDescription = productDescription;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
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
