package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderState implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("State")
    @Expose
    private State state;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
