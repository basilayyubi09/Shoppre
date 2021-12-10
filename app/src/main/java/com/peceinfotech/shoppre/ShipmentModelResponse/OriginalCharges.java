package com.peceinfotech.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OriginalCharges {

    @SerializedName("standard_photo_charges")
    @Expose
    private Integer standardPhotoCharges;
    @SerializedName("advanced_photo_charges")
    @Expose
    private Integer advancedPhotoCharges;
    @SerializedName("split_package")
    @Expose
    private Integer splitPackage;
    @SerializedName("wrong_address_amount")
    @Expose
    private Integer wrongAddressAmount;
    @SerializedName("scan_document_amount")
    @Expose
    private Integer scanDocumentAmount;
    @SerializedName("receive_mail_amount")
    @Expose
    private Integer receiveMailAmount;
    @SerializedName("pickup_amount")
    @Expose
    private Integer pickupAmount;
    @SerializedName("special_handling_amount")
    @Expose
    private Integer specialHandlingAmount;
    @SerializedName("consolidation_charge")
    @Expose
    private Integer consolidationCharge;
    @SerializedName("package_return")
    @Expose
    private Integer packageReturn;

    public Integer getStandardPhotoCharges() {
        return standardPhotoCharges;
    }

    public void setStandardPhotoCharges(Integer standardPhotoCharges) {
        this.standardPhotoCharges = standardPhotoCharges;
    }

    public Integer getAdvancedPhotoCharges() {
        return advancedPhotoCharges;
    }

    public void setAdvancedPhotoCharges(Integer advancedPhotoCharges) {
        this.advancedPhotoCharges = advancedPhotoCharges;
    }

    public Integer getSplitPackage() {
        return splitPackage;
    }

    public void setSplitPackage(Integer splitPackage) {
        this.splitPackage = splitPackage;
    }

    public Integer getWrongAddressAmount() {
        return wrongAddressAmount;
    }

    public void setWrongAddressAmount(Integer wrongAddressAmount) {
        this.wrongAddressAmount = wrongAddressAmount;
    }

    public Integer getScanDocumentAmount() {
        return scanDocumentAmount;
    }

    public void setScanDocumentAmount(Integer scanDocumentAmount) {
        this.scanDocumentAmount = scanDocumentAmount;
    }

    public Integer getReceiveMailAmount() {
        return receiveMailAmount;
    }

    public void setReceiveMailAmount(Integer receiveMailAmount) {
        this.receiveMailAmount = receiveMailAmount;
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

    public Integer getConsolidationCharge() {
        return consolidationCharge;
    }

    public void setConsolidationCharge(Integer consolidationCharge) {
        this.consolidationCharge = consolidationCharge;
    }

    public Integer getPackageReturn() {
        return packageReturn;
    }

    public void setPackageReturn(Integer packageReturn) {
        this.packageReturn = packageReturn;
    }
}
