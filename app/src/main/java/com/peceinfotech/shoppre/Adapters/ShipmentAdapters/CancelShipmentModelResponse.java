package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelShipmentModelResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
