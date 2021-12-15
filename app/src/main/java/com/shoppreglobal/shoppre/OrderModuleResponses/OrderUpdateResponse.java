package com.shoppreglobal.shoppre.OrderModuleResponses;

public class OrderUpdateResponse {

    int profileImage;
    String profileName;
    String orderStatus;
    String dateAndTime;

    public OrderUpdateResponse(int profileImage, String profileName, String orderStatus, String dateAndTime) {
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.orderStatus = orderStatus;
        this.dateAndTime = dateAndTime;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
