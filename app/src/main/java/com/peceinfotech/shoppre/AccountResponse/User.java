package com.peceinfotech.shoppre.AccountResponse;

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
