package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OriginalPlcCharges {
    @SerializedName("photo_amount")
    @Expose
    private Integer photoAmount;
    @SerializedName("split_package_amount")
    @Expose
    private Integer splitPackageAmount;
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

    public Integer getPhotoAmount() {
        return photoAmount;
    }

    public void setPhotoAmount(Integer photoAmount) {
        this.photoAmount = photoAmount;
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
