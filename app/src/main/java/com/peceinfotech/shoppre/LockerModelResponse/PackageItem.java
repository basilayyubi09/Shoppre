package com.peceinfotech.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PackageItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("object_invoice")
    @Expose
    private Object objectInvoice;
    @SerializedName("color")
    @Expose
    private Object color;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("if_item_unavailable")
    @Expose
    private Object ifItemUnavailable;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("package_order_code")
    @Expose
    private Object packageOrderCode;
    @SerializedName("package_id")
    @Expose
    private Integer packageId;
    @SerializedName("received_date")
    @Expose
    private Object receivedDate;
    @SerializedName("ps_total_price")
    @Expose
    private Object psTotalPrice;
    @SerializedName("ps_total_quantity")
    @Expose
    private Integer psTotalQuantity;
    @SerializedName("package_item_state_id")
    @Expose
    private Integer packageItemStateId;
    @SerializedName("PhotoRequests")
    @Expose
    private List<Object> photoRequests = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Object getObjectInvoice() {
        return objectInvoice;
    }

    public void setObjectInvoice(Object objectInvoice) {
        this.objectInvoice = objectInvoice;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getIfItemUnavailable() {
        return ifItemUnavailable;
    }

    public void setIfItemUnavailable(Object ifItemUnavailable) {
        this.ifItemUnavailable = ifItemUnavailable;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getPackageOrderCode() {
        return packageOrderCode;
    }

    public void setPackageOrderCode(Object packageOrderCode) {
        this.packageOrderCode = packageOrderCode;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Object getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Object receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Object getPsTotalPrice() {
        return psTotalPrice;
    }

    public void setPsTotalPrice(Object psTotalPrice) {
        this.psTotalPrice = psTotalPrice;
    }

    public Integer getPsTotalQuantity() {
        return psTotalQuantity;
    }

    public void setPsTotalQuantity(Integer psTotalQuantity) {
        this.psTotalQuantity = psTotalQuantity;
    }

    public Integer getPackageItemStateId() {
        return packageItemStateId;
    }

    public void setPackageItemStateId(Integer packageItemStateId) {
        this.packageItemStateId = packageItemStateId;
    }

    public List<Object> getPhotoRequests() {
        return photoRequests;
    }

    public void setPhotoRequests(List<Object> photoRequests) {
        this.photoRequests = photoRequests;
    }

}
