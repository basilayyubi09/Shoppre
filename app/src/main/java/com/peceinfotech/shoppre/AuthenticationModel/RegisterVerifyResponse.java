package com.peceinfotech.shoppre.AuthenticationModel;

import com.google.gson.annotations.SerializedName;

public class RegisterVerifyResponse {
    @SerializedName("message")
    String message;

    public RegisterVerifyResponse() {
    }

    public RegisterVerifyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
