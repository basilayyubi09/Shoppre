package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShipmentMeta implements Serializable {

    @SerializedName("storage_amount")
    @Expose
    private Integer storageAmount;
    @SerializedName("photo_amount")
    @Expose
    private Integer photoAmount;
    @SerializedName("pickup_amount")
    @Expose
    private Integer pickupAmount;
    @SerializedName("special_handling_amount")
    @Expose
    private Integer specialHandlingAmount;
    @SerializedName("receive_mail_amount")
    @Expose
    private Integer receiveMailAmount;
    @SerializedName("split_package_amount")
    @Expose
    private Integer splitPackageAmount;
    @SerializedName("wrong_address_amount")
    @Expose
    private Integer wrongAddressAmount;
    @SerializedName("consolidation_charge_amount")
    @Expose
    private Integer consolidationChargeAmount;
    @SerializedName("optsAmount")
    @Expose
    private Integer optsAmount;
    @SerializedName("scan_document_amount")
    @Expose
    private Integer scanDocumentAmount;

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

    public Integer getSplitPackageAmount() {
        return splitPackageAmount;
    }

    public void setSplitPackageAmount(Integer splitPackageAmount) {
        this.splitPackageAmount = splitPackageAmount;
    }

    public Integer getWrongAddressAmount() {
        return wrongAddressAmount;
    }

    public void setWrongAddressAmount(Integer wrongAddressAmount) {
        this.wrongAddressAmount = wrongAddressAmount;
    }

    public Integer getConsolidationChargeAmount() {
        return consolidationChargeAmount;
    }

    public void setConsolidationChargeAmount(Integer consolidationChargeAmount) {
        this.consolidationChargeAmount = consolidationChargeAmount;
    }

    public Integer getOptsAmount() {
        return optsAmount;
    }

    public void setOptsAmount(Integer optsAmount) {
        this.optsAmount = optsAmount;
    }

    public Integer getScanDocumentAmount() {
        return scanDocumentAmount;
    }

    public void setScanDocumentAmount(Integer scanDocumentAmount) {
        this.scanDocumentAmount = scanDocumentAmount;
    }

}
