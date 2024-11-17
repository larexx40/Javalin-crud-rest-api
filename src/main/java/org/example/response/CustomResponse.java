package org.example.response;

import java.util.Collections;
import java.util.List;

public class CustomResponse {
    private final boolean status;
    private final String message;
    private final Object data;
    private final List<String> error;
    private int statusCode;

    // Constructor for a successful response with optional data
    public CustomResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data != null ? data : null;
        this.error = Collections.emptyList();  // Default to an empty list
    }

    // Constructor for an error response with optional error list
    public CustomResponse(boolean status, String message, List<String> error) {
        this.status = status;
        this.message = message;
        this.data = null;  // Default to null
        this.error = error != null ? error : Collections.emptyList();
    }

    public static CustomResponse success(String message, Object data) {
        return new CustomResponse(true, message, data);
    }

    public static CustomResponse success(String message) {
        return new CustomResponse(true, message, null);
    }

    public static CustomResponse error(String message, List<String> error) {
        return new CustomResponse(false, message, error);
    }

    public static CustomResponse error(String message) {
        return new CustomResponse(false, message, Collections.emptyList());
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
