package com.springboot.controller;

public class ApplyOfferResponse {
    private int updatedCartValue;

    public ApplyOfferResponse(int updatedCartValue) {
        this.updatedCartValue = updatedCartValue;
    }

    public int getUpdatedCartValue() {
        return updatedCartValue;
    }

    public void setUpdatedCartValue(int updatedCartValue) {
        this.updatedCartValue = updatedCartValue;
    }

    @Override
    public String toString() {
        return "ApplyOfferResponse{" +
                "updatedCartValue=" + updatedCartValue +
                '}';
    }
}
