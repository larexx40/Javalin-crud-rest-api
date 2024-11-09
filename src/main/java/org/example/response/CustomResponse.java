package org.example.response;

import java.util.List;

public class CustomResponse {
    private boolean status;
    private String message;
    private Object data;
    private List<String> error;
    private  int statusCode;

    // Constructor for a successful response
    public CustomResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Constructor for an error response
    public CustomResponse(boolean status, String message, List<String> error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public static CustomResponse success(String message, Object data) {
        return new CustomResponse(true, message, data);
    }

    public static CustomResponse error(String message, List<String> error) {
        return new CustomResponse(false, message, error);
    }

    // Getters
    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public List<String> getError() {
        return error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
