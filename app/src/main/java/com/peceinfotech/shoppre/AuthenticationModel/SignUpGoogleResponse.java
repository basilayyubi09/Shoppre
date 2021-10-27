package com.peceinfotech.shoppre.AuthenticationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpGoogleResponse {
    @SerializedName("token")
    @Expose
    private Token token;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("message")
    @Expose
    private String message;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
