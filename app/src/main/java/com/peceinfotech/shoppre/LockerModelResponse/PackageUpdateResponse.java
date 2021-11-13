package com.peceinfotech.shoppre.LockerModelResponse;

public class PackageUpdateResponse {

    String name;
    String orderStatus;
    String date;

    public PackageUpdateResponse(String name, String orderStatus, String date) {
        this.name = name;
        this.orderStatus = orderStatus;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
