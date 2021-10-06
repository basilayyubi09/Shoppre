package com.peceinfotech.shoppre.AuthenticationModel;

public class ForgotPasswordResponse {
    String error , error_description;

    public ForgotPasswordResponse() {
    }

    public ForgotPasswordResponse(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
