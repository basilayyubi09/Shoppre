package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.AuthenticationModel.DeliveryListModel;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.OrderModuleResponses.Customer;

import java.util.List;

public class Shipment {
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
    private Float weight;
    @SerializedName("final_weight")
    @Expose
    private Float finalWeight;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("final_amount")
    @Expose
    private Integer finalAmount;
    @SerializedName("volumetric_weight")
    @Expose
    private Float volumetricWeight;

    @SerializedName("box_length")
    @Expose
    private Integer boxLength;
    @SerializedName("box_height")
    @Expose
    private Integer boxHeight;
    @SerializedName("box_width")
    @Expose
    private Integer boxWidth;

    @SerializedName("sub_total_amount")
    @Expose
    private Float subTotalAmount;

    @SerializedName("address_id")
    @Expose
    private Object addressId;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("ShipmentState")
    @Expose
    private ShipmentState shipmentState;
    @SerializedName("Packages")
    @Expose
    private List<PackageModel> packages = null;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("tracking_url")
    @Expose
    private String tracking_url;

    @SerializedName("estimated_amount")
    @Expose
    private Float estimatedAmount;


    @SerializedName("Country")
    @Expose
    private DeliveryListModel.Country country;
    @SerializedName("Customer")
    @Expose
    private Customer customer;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("ShipmentMetum")
    @Expose
    private ShipmentMetum shipmentMetum;

    @SerializedName("ShipmentBoxes")
    @Expose
    private List<ShipmentBox> shipmentBoxes = null;

    @SerializedName("PackageUserCharges")
    @Expose
    private List<Object> packageUserCharges = null;

    public String getTracking_url() {
        return tracking_url;
    }

    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }

    public Float getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Float estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public List<Object> getPackageUserCharges() {
        return packageUserCharges;
    }

    public void setPackageUserCharges(List<Object> packageUserCharges) {
        this.packageUserCharges = packageUserCharges;
    }

    public Integer getPackageLevelChargesAmount() {
        return packageLevelChargesAmount;
    }

    public void setPackageLevelChargesAmount(Integer packageLevelChargesAmount) {
        this.packageLevelChargesAmount = packageLevelChargesAmount;
    }

    public List<ShipmentBox> getShipmentBoxes() {
        return shipmentBoxes;
    }

    public void setShipmentBoxes(List<ShipmentBox> shipmentBoxes) {
        this.shipmentBoxes = shipmentBoxes;
    }

    public ShipmentMetum getShipmentMetum() {
        return shipmentMetum;
    }

    public void setShipmentMetum(ShipmentMetum shipmentMetum) {
        this.shipmentMetum = shipmentMetum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }


    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }


    public Float getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(Float finalWeight) {
        this.finalWeight = finalWeight;
    }


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public Integer getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Integer finalAmount) {
        this.finalAmount = finalAmount;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Float getVolumetricWeight() {
        return volumetricWeight;
    }

    public void setVolumetricWeight(Float volumetricWeight) {
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


    public Float getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Float subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }


    public Object getAddressId() {
        return addressId;
    }

    public void setAddressId(Object addressId) {
        this.addressId = addressId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public ShipmentState getShipmentState() {
        return shipmentState;
    }

    public void setShipmentState(ShipmentState shipmentState) {
        this.shipmentState = shipmentState;
    }

    public List<PackageModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageModel> packages) {
        this.packages = packages;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliveryListModel.Country getCountry() {
        return country;
    }

    public void setCountry(DeliveryListModel.Country country) {
        this.country = country;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
