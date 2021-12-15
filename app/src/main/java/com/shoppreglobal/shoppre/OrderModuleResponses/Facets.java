package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facets {
    @SerializedName("TASKS")
    @Expose
    private Integer tasks;
    @SerializedName("VIEW_ALL")
    @Expose
    private Integer viewAll;
    @SerializedName("IN_REVIEW")
    @Expose
    private Integer inReview;
    @SerializedName("ACTION_REQUIRED")
    @Expose
    private Integer actionRequired;
    @SerializedName("READY_TO_SEND")
    @Expose
    private Integer readyToSend;

    public Integer getTasks() {
        return tasks;
    }

    public void setTasks(Integer tasks) {
        this.tasks = tasks;
    }

    public Integer getViewAll() {
        return viewAll;
    }

    public void setViewAll(Integer viewAll) {
        this.viewAll = viewAll;
    }

    public Integer getInReview() {
        return inReview;
    }

    public void setInReview(Integer inReview) {
        this.inReview = inReview;
    }

    public Integer getActionRequired() {
        return actionRequired;
    }

    public void setActionRequired(Integer actionRequired) {
        this.actionRequired = actionRequired;
    }

    public Integer getReadyToSend() {
        return readyToSend;
    }

    public void setReadyToSend(Integer readyToSend) {
        this.readyToSend = readyToSend;
    }

}
