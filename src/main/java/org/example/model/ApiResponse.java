package org.example.model;

import java.util.List;

public class ApiResponse<T> {
    private boolean status;
    private String message;
    private List<String> errors;
    private T data;

    public static<T> ApiResponse<T> error(String message, List<String> errors) {
        ApiResponse<T> response = new ApiResponse<>(message, null);
        response.setErrors(errors);
        return response;
    }

    public ApiResponse(String message, T data) {
        this.status = true;
        this.message = message;
        this.data = data;
        this.errors = null;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
