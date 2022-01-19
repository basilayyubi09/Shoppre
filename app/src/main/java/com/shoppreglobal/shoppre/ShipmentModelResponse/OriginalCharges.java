package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OriginalCharges {

    @SerializedName("standard_photo_charges")
    @Expose
    private Double standardPhotoCharges;
    @SerializedName("advanced_photo_charges")
    @Expose
    private Double advancedPhotoCharges;
    @SerializedName("split_package")
    @Expose
    private Double splitPackage;
    @SerializedName("wrong_address_amount")
    @Expose
    private Double wrongAddressAmount;
    @SerializedName("scan_document_amount")
    @Expose
    private Double scanDocumentAmount;
    @SerializedName("receive_mail_amount")
    @Expose
    private Double receiveMailAmount;
    @SerializedName("pickup_amount")
    @Expose
    private Double pickupAmount;
    @SerializedName("special_handling_amount")
    @Expose
    private Double specialHandlingAmount;
    @SerializedName("consolidation_charge")
    @Expose
    private Double consolidationCharge;
    @SerializedName("package_return")
    @Expose
    private Double packageReturn;

    public Double getStandardPhotoCharges() {
        return standardPhotoCharges;
    }

    public void setStandardPhotoCharges(Double standardPhotoCharges) {
        this.standardPhotoCharges = standardPhotoCharges;
    }

    public Double getAdvancedPhotoCharges() {
        return advancedPhotoCharges;
    }

    public void setAdvancedPhotoCharges(Double advancedPhotoCharges) {
        this.advancedPhotoCharges = advancedPhotoCharges;
    }

    public Double getSplitPackage() {
        return splitPackage;
    }

    public void setSplitPackage(Double splitPackage) {
        this.splitPackage = splitPackage;
    }

    public Double getWrongAddressAmount() {
        return wrongAddressAmount;
    }

    public void setWrongAddressAmount(Double wrongAddressAmount) {
        this.wrongAddressAmount = wrongAddressAmount;
    }

    public Double getScanDocumentAmount() {
        return scanDocumentAmount;
    }

    public void setScanDocumentAmount(Double scanDocumentAmount) {
        this.scanDocumentAmount = scanDocumentAmount;
    }

    public Double getReceiveMailAmount() {
        return receiveMailAmount;
    }

    public void setReceiveMailAmount(Double receiveMailAmount) {
        this.receiveMailAmount = receiveMailAmount;
    }

    public Double getPickupAmount() {
        return pickupAmount;
    }

    public void setPickupAmount(Double pickupAmount) {
        this.pickupAmount = pickupAmount;
    }

    public Double getSpecialHandlingAmount() {
        return specialHandlingAmount;
    }

    public void setSpecialHandlingAmount(Double specialHandlingAmount) {
        this.specialHandlingAmount = specialHandlingAmount;
    }

    public Double getConsolidationCharge() {
        return consolidationCharge;
    }

    public void setConsolidationCharge(Double consolidationCharge) {
        this.consolidationCharge = consolidationCharge;
    }

    public Double getPackageReturn() {
        return packageReturn;
    }

    public void setPackageReturn(Double packageReturn) {
        this.packageReturn = packageReturn;
    }
}
