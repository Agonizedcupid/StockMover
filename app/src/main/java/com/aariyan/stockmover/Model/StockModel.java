package com.aariyan.stockmover.Model;

public class StockModel {

    private String productCode,date,quantity,stockType;
    public StockModel(){}

    public StockModel(String productCode, String date, String quantity, String stockType) {
        this.productCode = productCode;
        this.date = date;
        this.quantity = quantity;
        this.stockType = stockType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }
}
