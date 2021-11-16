package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItemState implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("State")
    @Expose
    private State__1 state;

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

    public State__1 getState() {
        return state;
    }

    public void setState(State__1 state) {
        this.state = state;
    }
}
