package com.wanted.onboarding.Utils;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class CustomResponseEntity {
    public static ResponseEntity<String> jsonResponse(int statusCode, String body) {
        return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
