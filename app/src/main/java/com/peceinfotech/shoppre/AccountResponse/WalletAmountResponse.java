package com.peceinfotech.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletAmountResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("parcel_wallet_amount")
    @Expose
    private Integer parcelWalletAmount;
    @SerializedName("ps_wallet_amount")
    @Expose
    private Integer psWalletAmount;
    @SerializedName("courier_wallet_amount")
    @Expose
    private Integer courierWalletAmount;
    @SerializedName("marketing_wallet_amount")
    @Expose
    private Integer marketingWalletAmount;
    @SerializedName("alternate_email")
    @Expose
    private Object alternateEmail;
    @SerializedName("profile_photo_url")
    @Expose
    private Object profilePhotoUrl;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("secondary_phone")
    @Expose
    private Object secondaryPhone;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("wallet_amount")
    @Expose
    private Integer walletAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
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

    public Integer getParcelWalletAmount() {
        return parcelWalletAmount;
    }

    public void setParcelWalletAmount(Integer parcelWalletAmount) {
        this.parcelWalletAmount = parcelWalletAmount;
    }

    public Integer getPsWalletAmount() {
        return psWalletAmount;
    }

    public void setPsWalletAmount(Integer psWalletAmount) {
        this.psWalletAmount = psWalletAmount;
    }

    public Integer getCourierWalletAmount() {
        return courierWalletAmount;
    }

    public void setCourierWalletAmount(Integer courierWalletAmount) {
        this.courierWalletAmount = courierWalletAmount;
    }

    public Integer getMarketingWalletAmount() {
        return marketingWalletAmount;
    }

    public void setMarketingWalletAmount(Integer marketingWalletAmount) {
        this.marketingWalletAmount = marketingWalletAmount;
    }

    public Object getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(Object alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public Object getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(Object profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(Object secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Integer walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
