package com.springboot.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    public ApiResponse(String success) {
    }
    private String response_msg;
}
