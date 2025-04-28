package com.jeroka.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message response payload")
    public class MessageResponse {
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 