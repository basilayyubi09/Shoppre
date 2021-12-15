package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StateNameAndColor implements Serializable {
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("color")
    @Expose
    private String color;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
