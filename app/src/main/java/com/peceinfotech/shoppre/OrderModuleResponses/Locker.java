package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Locker implements Serializable {
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("allocated_at")
    @Expose
    private String allocatedAt;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllocatedAt() {
        return allocatedAt;
    }

    public void setAllocatedAt(String allocatedAt) {
        this.allocatedAt = allocatedAt;
    }
}
