package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingChargeSummary {

    @SerializedName("total_item_value")
    @Expose
    private Integer totalItemValue;
    @SerializedName("total_package_count")
    @Expose
    private Integer totalPackageCount;
    @SerializedName("box_weights")
    @Expose
    private List<BoxWeight> boxWeights = null;
    @SerializedName("final_chargeable_weight")
    @Expose
    private Float finalChargeableWeight;
    @SerializedName("sub_total")
    @Expose
    private Double subTotal;
    @SerializedName("shipping_discount")
    @Expose
    private Double shippingDiscount;
    @SerializedName("basic_shipping_costs")
    @Expose
    private Double basicShippingCosts;

    public Integer getTotalPackageCount() {
        return totalPackageCount;
    }

    public void setTotalPackageCount(Integer totalPackageCount) {
        this.totalPackageCount = totalPackageCount;
    }

    public Integer getTotalItemValue() {
        return totalItemValue;
    }

    public void setTotalItemValue(Integer totalItemValue) {
        this.totalItemValue = totalItemValue;
    }

    public List<BoxWeight> getBoxWeights() {
        return boxWeights;
    }

    public void setBoxWeights(List<BoxWeight> boxWeights) {
        this.boxWeights = boxWeights;
    }

    public Float getFinalChargeableWeight() {
        return finalChargeableWeight;
    }

    public void setFinalChargeableWeight(Float finalChargeableWeight) {
        this.finalChargeableWeight = finalChargeableWeight;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(Double shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public Double getBasicShippingCosts() {
        return basicShippingCosts;
    }

    public void setBasicShippingCosts(Double basicShippingCosts) {
        this.basicShippingCosts = basicShippingCosts;
    }
}
