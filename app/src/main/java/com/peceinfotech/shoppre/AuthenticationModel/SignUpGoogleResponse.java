package com.peceinfotech.shoppre.AuthenticationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpGoogleResponse {
    @SerializedName("token")
    @Expose
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
