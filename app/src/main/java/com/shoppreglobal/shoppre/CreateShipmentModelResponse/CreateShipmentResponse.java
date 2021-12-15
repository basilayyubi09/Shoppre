package com.shoppreglobal.shoppre.CreateShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateShipmentResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("package_level_charges_amount")
    @Expose
    private Integer packageLevelChargesAmount;
    @SerializedName("estimated_amount")
    @Expose
    private Integer estimatedAmount;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("packages_count")
    @Expose
    private Integer packagesCount;
    @SerializedName("value_amount")
    @Expose
    private Integer valueAmount;
    @SerializedName("sub_total_amount")
    @Expose
    private Integer subTotalAmount;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("pick_up_charge_amount")
    @Expose
    private Integer pickUpChargeAmount;
    @SerializedName("volumetric_weight")
    @Expose
    private Integer volumetricWeight;
    @SerializedName("is_doc")
    @Expose
    private Boolean isDoc;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("final_amount")
    @Expose
    private Integer finalAmount;
    @SerializedName("final_weight")
    @Expose
    private Integer finalWeight;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

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

    public Integer getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Integer estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPackagesCount() {
        return packagesCount;
    }

    public void setPackagesCount(Integer packagesCount) {
        this.packagesCount = packagesCount;
    }

    public Integer getValueAmount() {
        return valueAmount;
    }

    public void setValueAmount(Integer valueAmount) {
        this.valueAmount = valueAmount;
    }

    public Integer getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Integer subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getPickUpChargeAmount() {
        return pickUpChargeAmount;
    }

    public void setPickUpChargeAmount(Integer pickUpChargeAmount) {
        this.pickUpChargeAmount = pickUpChargeAmount;
    }

    public Integer getVolumetricWeight() {
        return volumetricWeight;
    }

    public void setVolumetricWeight(Integer volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }

    public Boolean getIsDoc() {
        return isDoc;
    }

    public void setIsDoc(Boolean isDoc) {
        this.isDoc = isDoc;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Integer getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(Integer finalWeight) {
        this.finalWeight = finalWeight;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
