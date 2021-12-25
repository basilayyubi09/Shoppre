package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.LockerModelResponse.ShipmentMeta;

public class ServiceChargesSummary {

    @SerializedName("plcCharges")
    @Expose
    private OriginalPlcCharges originalPlcCharges;
    @SerializedName("actualPlcCharges")
    @Expose
    private ActualPlcCharges actualPlcCharges;
    @SerializedName("serviceChargesTotal")
    @Expose
    private Integer serviceChargesTotal;
    @SerializedName("shipmentMeta")
    @Expose
    private ShipmentMeta shipmentMeta;
    @SerializedName("membershipShippingDiscount")
    @Expose
    private Integer membershipShippingDiscount;
    @SerializedName("membershipServiceChargeDiscount")
    @Expose
    private Integer membershipServiceChargeDiscount;
    @SerializedName("membershipTotalDiscount")
    @Expose
    private Integer membershipTotalDiscount;
    @SerializedName("delayCharges")
    @Expose
    private Integer delayCharges;
    @SerializedName("originalPlcChargesTotal")
    @Expose
    private Integer originalPlcChargesTotal;
    @SerializedName("shipmentLevelChargesAmount")
    @Expose
    private Integer shipmentLevelChargesAmount;
    @SerializedName("packageLevelDiscount")
    @Expose
    private Integer packageLevelDiscount;
    @SerializedName("discountOrAdditionalCharges")
    @Expose
    private Integer discountOrAdditionalCharges;

    public OriginalPlcCharges getOriginalPlcCharges() {
        return originalPlcCharges;
    }

    public void setOriginalPlcCharges(OriginalPlcCharges originalPlcCharges) {
        this.originalPlcCharges = originalPlcCharges;
    }

    public ActualPlcCharges getActualPlcCharges() {
        return actualPlcCharges;
    }

    public void setActualPlcCharges(ActualPlcCharges actualPlcCharges) {
        this.actualPlcCharges = actualPlcCharges;
    }

    public Integer getServiceChargesTotal() {
        return serviceChargesTotal;
    }

    public void setServiceChargesTotal(Integer serviceChargesTotal) {
        this.serviceChargesTotal = serviceChargesTotal;
    }

    public ShipmentMeta getShipmentMeta() {
        return shipmentMeta;
    }

    public void setShipmentMeta(ShipmentMeta shipmentMeta) {
        this.shipmentMeta = shipmentMeta;
    }

    public Integer getMembershipShippingDiscount() {
        return membershipShippingDiscount;
    }

    public void setMembershipShippingDiscount(Integer membershipShippingDiscount) {
        this.membershipShippingDiscount = membershipShippingDiscount;
    }

    public Integer getMembershipServiceChargeDiscount() {
        return membershipServiceChargeDiscount;
    }

    public void setMembershipServiceChargeDiscount(Integer membershipServiceChargeDiscount) {
        this.membershipServiceChargeDiscount = membershipServiceChargeDiscount;
    }

    public Integer getMembershipTotalDiscount() {
        return membershipTotalDiscount;
    }

    public void setMembershipTotalDiscount(Integer membershipTotalDiscount) {
        this.membershipTotalDiscount = membershipTotalDiscount;
    }

    public Integer getDelayCharges() {
        return delayCharges;
    }

    public void setDelayCharges(Integer delayCharges) {
        this.delayCharges = delayCharges;
    }

    public Integer getOriginalPlcChargesTotal() {
        return originalPlcChargesTotal;
    }

    public void setOriginalPlcChargesTotal(Integer originalPlcChargesTotal) {
        this.originalPlcChargesTotal = originalPlcChargesTotal;
    }

    public Integer getShipmentLevelChargesAmount() {
        return shipmentLevelChargesAmount;
    }

    public void setShipmentLevelChargesAmount(Integer shipmentLevelChargesAmount) {
        this.shipmentLevelChargesAmount = shipmentLevelChargesAmount;
    }

    public Integer getPackageLevelDiscount() {
        return packageLevelDiscount;
    }

    public void setPackageLevelDiscount(Integer packageLevelDiscount) {
        this.packageLevelDiscount = packageLevelDiscount;
    }

    public Integer getDiscountOrAdditionalCharges() {
        return discountOrAdditionalCharges;
    }

    public void setDiscountOrAdditionalCharges(Integer discountOrAdditionalCharges) {
        this.discountOrAdditionalCharges = discountOrAdditionalCharges;
    }
}
