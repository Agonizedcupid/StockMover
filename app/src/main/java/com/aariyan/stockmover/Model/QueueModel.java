package com.aariyan.stockmover.Model;

public class QueueModel {
    private String moveIn,moveFrom,location,barcode;

    public QueueModel() {}

    public QueueModel(String moveIn, String moveFrom, String location, String barcode) {
        this.moveIn = moveIn;
        this.moveFrom = moveFrom;
        this.location = location;
        this.barcode = barcode;
    }

    public String getMoveIn() {
        return moveIn;
    }

    public void setMoveIn(String moveIn) {
        this.moveIn = moveIn;
    }

    public String getMoveFrom() {
        return moveFrom;
    }

    public void setMoveFrom(String moveFrom) {
        this.moveFrom = moveFrom;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
