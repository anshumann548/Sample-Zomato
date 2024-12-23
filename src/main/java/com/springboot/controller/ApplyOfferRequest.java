package com.springboot.controller;

public class ApplyOfferRequest {
    private int userId;
    private int restaurantId;
    private int cartValue;

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int userId) {
        this.userId = userId;
    }

    public int getRestaurant_id() {
        return restaurantId;
    }

    public void setRestaurant_id(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCart_value() {
        return cartValue;
    }

    public void setCart_value(int cartValue) {
        this.cartValue = cartValue;
    }
}
