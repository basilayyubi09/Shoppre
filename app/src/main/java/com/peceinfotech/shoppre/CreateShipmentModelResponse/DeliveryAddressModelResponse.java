package com.peceinfotech.shoppre.CreateShipmentModelResponse;

public class DeliveryAddressModelResponse {

    String name;
    String line1;
    String city;
    String country;
    String phoneNo;

    public DeliveryAddressModelResponse(String name, String line1, String city, String country, String phoneNo) {
        this.name = name;
        this.line1 = line1;
        this.city = city;
        this.country = country;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
