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
    private Double serviceChargesTotal;
    @SerializedName("shipmentMeta")
    @Expose
    private ShipmentMeta shipmentMeta;
    @SerializedName("membershipShippingDiscount")
    @Expose
    private Double membershipShippingDiscount;
    @SerializedName("membershipServiceChargeDiscount")
    @Expose
    private Double membershipServiceChargeDiscount;
    @SerializedName("membershipTotalDiscount")
    @Expose
    private Double membershipTotalDiscount;
    @SerializedName("delayCharges")
    @Expose
    private Double delayCharges;
    @SerializedName("originalPlcChargesTotal")
    @Expose
    private Double originalPlcChargesTotal;
    @SerializedName("shipmentLevelChargesAmount")
    @Expose
    private Double shipmentLevelChargesAmount;
    @SerializedName("packageLevelDiscount")
    @Expose
    private Double packageLevelDiscount;
    @SerializedName("discountOrAdditionalCharges")
    @Expose
    private Double discountOrAdditionalCharges;

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

    public Double getServiceChargesTotal() {
        return serviceChargesTotal;
    }

    public void setServiceChargesTotal(Double serviceChargesTotal) {
        this.serviceChargesTotal = serviceChargesTotal;
    }

    public ShipmentMeta getShipmentMeta() {
        return shipmentMeta;
    }

    public void setShipmentMeta(ShipmentMeta shipmentMeta) {
        this.shipmentMeta = shipmentMeta;
    }

    public Double getMembershipShippingDiscount() {
        return membershipShippingDiscount;
    }

    public void setMembershipShippingDiscount(Double membershipShippingDiscount) {
        this.membershipShippingDiscount = membershipShippingDiscount;
    }

    public Double getMembershipServiceChargeDiscount() {
        return membershipServiceChargeDiscount;
    }

    public void setMembershipServiceChargeDiscount(Double membershipServiceChargeDiscount) {
        this.membershipServiceChargeDiscount = membershipServiceChargeDiscount;
    }

    public Double getMembershipTotalDiscount() {
        return membershipTotalDiscount;
    }

    public void setMembershipTotalDiscount(Double membershipTotalDiscount) {
        this.membershipTotalDiscount = membershipTotalDiscount;
    }

    public Double getDelayCharges() {
        return delayCharges;
    }

    public void setDelayCharges(Double delayCharges) {
        this.delayCharges = delayCharges;
    }

    public Double getOriginalPlcChargesTotal() {
        return originalPlcChargesTotal;
    }

    public void setOriginalPlcChargesTotal(Double originalPlcChargesTotal) {
        this.originalPlcChargesTotal = originalPlcChargesTotal;
    }

    public Double getShipmentLevelChargesAmount() {
        return shipmentLevelChargesAmount;
    }

    public void setShipmentLevelChargesAmount(Double shipmentLevelChargesAmount) {
        this.shipmentLevelChargesAmount = shipmentLevelChargesAmount;
    }

    public Double getPackageLevelDiscount() {
        return packageLevelDiscount;
    }

    public void setPackageLevelDiscount(Double packageLevelDiscount) {
        this.packageLevelDiscount = packageLevelDiscount;
    }

    public Double getDiscountOrAdditionalCharges() {
        return discountOrAdditionalCharges;
    }

    public void setDiscountOrAdditionalCharges(Double discountOrAdditionalCharges) {
        this.discountOrAdditionalCharges = discountOrAdditionalCharges;
    }
}
