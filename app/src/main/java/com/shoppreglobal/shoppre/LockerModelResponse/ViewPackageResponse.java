package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.OrderModuleResponses.Customer;
import com.shoppreglobal.shoppre.OrderModuleResponses.PackageState;
import com.shoppreglobal.shoppre.OrderModuleResponses.Store;

import java.io.Serializable;
import java.util.List;

public class ViewPackageResponse implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("invoice_code")
    @Expose
    private String invoiceCode;

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @SerializedName("weight")
    @Expose
    private Float weight;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("personal_shopper_cost")
    @Expose
    private Object personalShopperCost;
    @SerializedName("delivery_charge")
    @Expose
    private Object deliveryCharge;
    @SerializedName("sales_tax")
    @Expose
    private Object salesTax;
    @SerializedName("payment_gateway_fee")
    @Expose
    private Object paymentGatewayFee;
    @SerializedName("sub_total")
    @Expose
    private Object subTotal;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("buy_if_price_changed")
    @Expose
    private Object buyIfPriceChanged;
    @SerializedName("order_code")
    @Expose
    private Object orderCode;
    @SerializedName("invoice")
    @Expose
    private Object invoice;
    @SerializedName("PackageState")
    @Expose
    private PackageState packageState;
    @SerializedName("PackageItems")
    @Expose
    private List<PackageItem> packageItems = null;
    @SerializedName("Store")
    @Expose
    private Store store;
    @SerializedName("Customer")
    @Expose
    private Customer customer;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("allPackageItemIds")
    @Expose
    private List<Integer> allPackageItemIds = null;
    @SerializedName("stateNameAndColor")
    @Expose
    private StateNameAndColor stateNameAndColor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
    }

    public Object getPersonalShopperCost() {
        return personalShopperCost;
    }

    public void setPersonalShopperCost(Object personalShopperCost) {
        this.personalShopperCost = personalShopperCost;
    }

    public Object getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Object deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Object getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Object salesTax) {
        this.salesTax = salesTax;
    }

    public Object getPaymentGatewayFee() {
        return paymentGatewayFee;
    }

    public void setPaymentGatewayFee(Object paymentGatewayFee) {
        this.paymentGatewayFee = paymentGatewayFee;
    }

    public Object getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Object subTotal) {
        this.subTotal = subTotal;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Object getBuyIfPriceChanged() {
        return buyIfPriceChanged;
    }

    public void setBuyIfPriceChanged(Object buyIfPriceChanged) {
        this.buyIfPriceChanged = buyIfPriceChanged;
    }

    public Object getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Object orderCode) {
        this.orderCode = orderCode;
    }

    public Object getInvoice() {
        return invoice;
    }

    public void setInvoice(Object invoice) {
        this.invoice = invoice;
    }

    public PackageState getPackageState() {
        return packageState;
    }

    public void setPackageState(PackageState packageState) {
        this.packageState = packageState;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public List<Integer> getAllPackageItemIds() {
        return allPackageItemIds;
    }

    public void setAllPackageItemIds(List<Integer> allPackageItemIds) {
        this.allPackageItemIds = allPackageItemIds;
    }

    public StateNameAndColor getStateNameAndColor() {
        return stateNameAndColor;
    }

    public void setStateNameAndColor(StateNameAndColor stateNameAndColor) {
        this.stateNameAndColor = stateNameAndColor;
    }
}
