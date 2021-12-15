package com.shoppreglobal.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("wallet_amount")
    @Expose
    private Integer walletAmount;
    @SerializedName("ps_wallet_amount")
    @Expose
    private Integer psWalletAmount;
    @SerializedName("courier_wallet_amount")
    @Expose
    private Integer courierWalletAmount;
    @SerializedName("parcel_wallet_amount")
    @Expose
    private Integer parcelWalletAmount;
    @SerializedName("marketing_wallet_amount")
    @Expose
    private Integer marketingWalletAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("profile_photo_url")
    @Expose
    private Object profilePhotoUrl;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("referred_by")
    @Expose
    private String referredBy;




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Object getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(Object profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(Integer walletAmount) {
        this.walletAmount = walletAmount;
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

    public Integer getParcelWalletAmount() {
        return parcelWalletAmount;
    }

    public void setParcelWalletAmount(Integer parcelWalletAmount) {
        this.parcelWalletAmount = parcelWalletAmount;
    }

    public Integer getMarketingWalletAmount() {
        return marketingWalletAmount;
    }

    public void setMarketingWalletAmount(Integer marketingWalletAmount) {
        this.marketingWalletAmount = marketingWalletAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
