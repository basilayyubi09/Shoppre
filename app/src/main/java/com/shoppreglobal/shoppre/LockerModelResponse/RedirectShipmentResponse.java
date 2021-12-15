package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.OrderModuleResponses.Customer;

import java.util.List;

public class RedirectShipmentResponse {

    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("packages")
    @Expose
    private List<PackageModel> packages = null;
    @SerializedName("shipmentMeta")
    @Expose
    private ShipmentMeta shipmentMeta;
    @SerializedName("IS_LIQUID")
    @Expose
    private String isLiquid;
    @SerializedName("IsShippingAddress")
    @Expose
    private Boolean isShippingAddress;
    @SerializedName("shippingPreference")
    @Expose
    private ShippingPreference shippingPreference;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<PackageModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageModel> packages) {
        this.packages = packages;
    }

    public ShipmentMeta getShipmentMeta() {
        return shipmentMeta;
    }

    public void setShipmentMeta(ShipmentMeta shipmentMeta) {
        this.shipmentMeta = shipmentMeta;
    }

    public String getIsLiquid() {
        return isLiquid;
    }

    public void setIsLiquid(String isLiquid) {
        this.isLiquid = isLiquid;
    }

    public Boolean getIsShippingAddress() {
        return isShippingAddress;
    }

    public void setIsShippingAddress(Boolean isShippingAddress) {
        this.isShippingAddress = isShippingAddress;
    }

    public ShippingPreference getShippingPreference() {
        return shippingPreference;
    }

    public void setShippingPreference(ShippingPreference shippingPreference) {
        this.shippingPreference = shippingPreference;
    }
}

