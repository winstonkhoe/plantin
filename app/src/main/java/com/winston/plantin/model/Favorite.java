package com.winston.plantin.model;

public class Favorite {
    private Integer favoriteID;
    private Integer shopID;
    private Integer userID;

    public Favorite(){}

    public Favorite(Integer favoriteID, Integer shopID, Integer userID) {
        this.favoriteID = favoriteID;
        this.shopID = shopID;
        this.userID = userID;
    }

    public Integer getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(Integer favoriteID) {
        this.favoriteID = favoriteID;
    }

    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
