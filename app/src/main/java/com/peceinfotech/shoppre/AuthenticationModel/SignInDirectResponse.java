package com.peceinfotech.shoppre.AuthenticationModel;

public class SignInDirectResponse {
    String code , error , error_description;

    public SignInDirectResponse(String code, String error, String error_description) {
        this.code = code;
        this.error = error;
        this.error_description = error_description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
