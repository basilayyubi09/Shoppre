package com.shoppreglobal.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralHistory {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("salutation")
    @Expose
    private Object salutation;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSalutation() {
        return salutation;
    }

    public void setSalutation(Object salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
