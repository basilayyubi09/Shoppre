package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActualPlcCharges {
    @SerializedName("storage_amount")
    @Expose
    private Integer storageAmount;
    @SerializedName("photo_amount")
    @Expose
    private Integer photoAmount;
    @SerializedName("pickup_amount")
    @Expose
    private Integer pickupAmount;
    @SerializedName("split_package_amount")
    @Expose
    private Integer splitPackageAmount;
    @SerializedName("special_handling_amount")
    @Expose
    private Integer specialHandlingAmount;
    @SerializedName("receive_mail_amount")
    @Expose
    private Integer receiveMailAmount;
    @SerializedName("scan_document_amount")
    @Expose
    private Integer scanDocumentAmount;
    @SerializedName("wrong_address_amount")
    @Expose
    private Integer wrongAddressAmount;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;

    public Integer getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(Integer storageAmount) {
        this.storageAmount = storageAmount;
    }

    public Integer getPhotoAmount() {
        return photoAmount;
    }

    public void setPhotoAmount(Integer photoAmount) {
        this.photoAmount = photoAmount;
    }

    public Integer getPickupAmount() {
        return pickupAmount;
    }

    public void setPickupAmount(Integer pickupAmount) {
        this.pickupAmount = pickupAmount;
    }

    public Integer getSplitPackageAmount() {
        return splitPackageAmount;
    }

    public void setSplitPackageAmount(Integer splitPackageAmount) {
        this.splitPackageAmount = splitPackageAmount;
    }

    public Integer getSpecialHandlingAmount() {
        return specialHandlingAmount;
    }

    public void setSpecialHandlingAmount(Integer specialHandlingAmount) {
        this.specialHandlingAmount = specialHandlingAmount;
    }

    public Integer getReceiveMailAmount() {
        return receiveMailAmount;
    }

    public void setReceiveMailAmount(Integer receiveMailAmount) {
        this.receiveMailAmount = receiveMailAmount;
    }

    public Integer getScanDocumentAmount() {
        return scanDocumentAmount;
    }

    public void setScanDocumentAmount(Integer scanDocumentAmount) {
        this.scanDocumentAmount = scanDocumentAmount;
    }

    public Integer getWrongAddressAmount() {
        return wrongAddressAmount;
    }

    public void setWrongAddressAmount(Integer wrongAddressAmount) {
        this.wrongAddressAmount = wrongAddressAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }
}
