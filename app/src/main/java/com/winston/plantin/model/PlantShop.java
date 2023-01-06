package com.winston.plantin.model;

public class PlantShop {

    private Integer shopID;
    private String name;
    private String location;
    private String image;
    private Double latitude;
    private Double longitude;

    public PlantShop(Integer shopID, String name, String location, String image, Double latitude, Double longitude) {
        this.shopID = shopID;
        this.name = name;
        this.location = location;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlantShop(){}

    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
