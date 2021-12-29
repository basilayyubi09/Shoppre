package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.OrderModuleResponses.PackageState;
import com.shoppreglobal.shoppre.OrderModuleResponses.Store;

import java.io.Serializable;
import java.util.List;

public class PackageModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("weight")
    @Expose
    private Object weight;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("order_code")
    @Expose
    private Object orderCode;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("invoice_code")
    @Expose
    private String invoiceCode;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("is_restricted_item")
    @Expose
    private Object isRestrictedItem;
    @SerializedName("invoice")
    @Expose
    private Object invoice;
    @SerializedName("is_full_invoice_received")
    @Expose
    private boolean isFullInvoiceReceived;
    @SerializedName("is_wrong_item")
    @Expose
    private Object isWrongItem;
    @SerializedName("upload_type")
    @Expose
    private Integer uploadType;
    @SerializedName("PackageState")
    @Expose
    private PackageState packageState;
    @SerializedName("PackageStates")
    @Expose
    private List<PackageState__1> packageStates = null;
    @SerializedName("Store")
    @Expose
    private Store store;
    @SerializedName("PackageItems")
    @Expose
    private List<PackageItem> packageItems = null;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("color")
    @Expose
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Integer getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
    }

    public Object getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Object orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Object getIsRestrictedItem() {
        return isRestrictedItem;
    }

    public void setIsRestrictedItem(Object isRestrictedItem) {
        this.isRestrictedItem = isRestrictedItem;
    }

    public Object getInvoice() {
        return invoice;
    }

    public Boolean getIsFullInvoiceReceived() {
        return isFullInvoiceReceived;
    }

    public void setIsFullInvoiceReceived(boolean isFullInvoiceReceived) {
        this.isFullInvoiceReceived = isFullInvoiceReceived;
    }


    public void setInvoice(Object invoice) {
        this.invoice = invoice;
    }

    public Object getIsWrongItem() {
        return isWrongItem;
    }

    public void setIsWrongItem(Object isWrongItem) {
        this.isWrongItem = isWrongItem;
    }

    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
    }

    public PackageState getPackageState() {
        return packageState;
    }

    public void setPackageState(PackageState packageState) {
        this.packageState = packageState;
    }

    public List<PackageState__1> getPackageStates() {
        return packageStates;
    }

    public void setPackageStates(List<PackageState__1> packageStates) {
        this.packageStates = packageStates;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
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
