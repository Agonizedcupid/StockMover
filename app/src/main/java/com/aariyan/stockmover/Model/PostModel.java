package com.aariyan.stockmover.Model;

public class PostModel {

    private String Location,MoveType,Item,strUID;

    public PostModel() {}

    public PostModel(String location, String moveType, String item,String strUID) {
        Location = location;
        MoveType = moveType;
        Item = item;
        this.strUID = strUID;
    }

    public String getLocation() {
        return Location;
    }

    public String getStrUID() {
        return strUID;
    }

    public void setStrUID(String strUID) {
        this.strUID = strUID;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMoveType() {
        return MoveType;
    }

    public void setMoveType(String moveType) {
        MoveType = moveType;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }
}
