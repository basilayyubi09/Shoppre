package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShipmentMetum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("package_level_charges_amount")
    @Expose
    private Integer packageLevelChargesAmount;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("final_weight")
    @Expose
    private Integer finalWeight;
    @SerializedName("pick_up_charge_amount")
    @Expose
    private Integer pickUpChargeAmount;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("estimated_amount")
    @Expose
    private Integer estimatedAmount;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("value_amount")
    @Expose
    private Integer valueAmount;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("shipment_state_id")
    @Expose
    private Integer shipmentStateId;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("payment_gateway_id")
    @Expose
    private Object paymentGatewayId;
    @SerializedName("volumetric_weight")
    @Expose
    private Integer volumetricWeight;
    @SerializedName("box_length")
    @Expose
    private Integer boxLength;
    @SerializedName("box_height")
    @Expose
    private Integer boxHeight;
    @SerializedName("box_width")
    @Expose
    private Integer boxWidth;
    @SerializedName("tracking_code")
    @Expose
    private Object trackingCode;
    @SerializedName("sub_total_amount")
    @Expose
    private Integer subTotalAmount;
    @SerializedName("is_membership_plan_applied")
    @Expose
    private Object isMembershipPlanApplied;
    @SerializedName("ShipmentMetum")
    @Expose
    private ShipmentMetum shipmentMetum;
    @SerializedName("ShipmentState")
    @Expose
    private ShipmentState shipmentState;
    @SerializedName("ShipmentBoxes")
    @Expose
    private List<Object> shipmentBoxes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageLevelChargesAmount() {
        return packageLevelChargesAmount;
    }

    public void setPackageLevelChargesAmount(Integer packageLevelChargesAmount) {
        this.packageLevelChargesAmount = packageLevelChargesAmount;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(Integer finalWeight) {
        this.finalWeight = finalWeight;
    }

    public Integer getPickUpChargeAmount() {
        return pickUpChargeAmount;
    }

    public void setPickUpChargeAmount(Integer pickUpChargeAmount) {
        this.pickUpChargeAmount = pickUpChargeAmount;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Integer estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getValueAmount() {
        return valueAmount;
    }

    public void setValueAmount(Integer valueAmount) {
        this.valueAmount = valueAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getShipmentStateId() {
        return shipmentStateId;
    }

    public void setShipmentStateId(Integer shipmentStateId) {
        this.shipmentStateId = shipmentStateId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Object getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(Object paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    public Integer getVolumetricWeight() {
        return volumetricWeight;
    }

    public void setVolumetricWeight(Integer volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }

    public Integer getBoxLength() {
        return boxLength;
    }

    public void setBoxLength(Integer boxLength) {
        this.boxLength = boxLength;
    }

    public Integer getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(Integer boxHeight) {
        this.boxHeight = boxHeight;
    }

    public Integer getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(Integer boxWidth) {
        this.boxWidth = boxWidth;
    }

    public Object getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(Object trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Integer getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Integer subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Object getIsMembershipPlanApplied() {
        return isMembershipPlanApplied;
    }

    public void setIsMembershipPlanApplied(Object isMembershipPlanApplied) {
        this.isMembershipPlanApplied = isMembershipPlanApplied;
    }

    public ShipmentMetum getShipmentMetum() {
        return shipmentMetum;
    }

    public void setShipmentMetum(ShipmentMetum shipmentMetum) {
        this.shipmentMetum = shipmentMetum;
    }

    public ShipmentState getShipmentState() {
        return shipmentState;
    }

    public void setShipmentState(ShipmentState shipmentState) {
        this.shipmentState = shipmentState;
    }

    public List<Object> getShipmentBoxes() {
        return shipmentBoxes;
    }

    public void setShipmentBoxes(List<Object> shipmentBoxes) {
        this.shipmentBoxes = shipmentBoxes;
    }
}
