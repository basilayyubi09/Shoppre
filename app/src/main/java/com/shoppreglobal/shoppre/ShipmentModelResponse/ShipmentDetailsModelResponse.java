package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;

import java.util.List;

public class ShipmentDetailsModelResponse {
    @SerializedName("shipment")
    @Expose
    private Shipment shipment;
    @SerializedName("packages")
    @Expose
    private List<PackageModel> packages = null;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("totalHours")
    @Expose
    private Integer totalHours;
    @SerializedName("originalCharges")
    @Expose
    private OriginalCharges originalCharges;
    @SerializedName("serviceChargesSummary")
    @Expose
    private ServiceChargesSummary serviceChargesSummary;
    @SerializedName("shippingChargeSummary")
    @Expose
    private ShippingChargeSummary shippingChargeSummary;

    public ServiceChargesSummary getServiceChargesSummary() {
        return serviceChargesSummary;
    }

    public void setServiceChargesSummary(ServiceChargesSummary serviceChargesSummary) {
        this.serviceChargesSummary = serviceChargesSummary;
    }

    public ShippingChargeSummary getShippingChargeSummary() {
        return shippingChargeSummary;
    }

    public void setShippingChargeSummary(ShippingChargeSummary shippingChargeSummary) {
        this.shippingChargeSummary = shippingChargeSummary;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public List<PackageModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageModel> packages) {
        this.packages = packages;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public OriginalCharges getOriginalCharges() {
        return originalCharges;
    }

    public void setOriginalCharges(OriginalCharges originalCharges) {
        this.originalCharges = originalCharges;
    }
}