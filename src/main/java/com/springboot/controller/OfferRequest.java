package com.springboot.controller;

import java.util.List;

public class OfferRequest {
    private int userId;
    private String offerType;
    private int offerValue;
    private List<String> segments;

    // Constructor
    public OfferRequest(int userId, String offerType, int offerValue, List<String> segments) {
        this.userId = userId;
        this.offerType = offerType;
        this.offerValue = offerValue;
        this.segments = segments;
    }

    // Getters and Setters (if needed)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public int getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(int offerValue) {
        this.offerValue = offerValue;
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments;
    }
}
