package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShowOrderResponse implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("personal_shopper_cost")
    @Expose
    private Integer personalShopperCost;
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
    private Integer subTotal;
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;
    @SerializedName("buy_if_price_changed")
    @Expose
    private String buyIfPriceChanged;
    @SerializedName("is_cod_payment")
    @Expose
    private Object isCodPayment;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("additional_charges")
    @Expose
    private Integer additionalCharges;
    @SerializedName("OrderState")
    @Expose
    private OrderState orderState;
    @SerializedName("OrderItems")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("Store")
    @Expose
    private Store store;
    @SerializedName("Customer")
    @Expose
    private Customer customer;
    @SerializedName("seller_invoice")
    @Expose
    private String sellerInvoice;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("allOrderItemIds")
    @Expose
    private List<Object> allOrderItemIds = null;
    @SerializedName("transactionOrders")
    @Expose
    private List<Object> transactionOrders = null;

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

    public Integer getPersonalShopperCost() {
        return personalShopperCost;
    }

    public void setPersonalShopperCost(Integer personalShopperCost) {
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

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getBuyIfPriceChanged() {
        return buyIfPriceChanged;
    }

    public void setBuyIfPriceChanged(String buyIfPriceChanged) {
        this.buyIfPriceChanged = buyIfPriceChanged;
    }

    public Object getIsCodPayment() {
        return isCodPayment;
    }

    public void setIsCodPayment(Object isCodPayment) {
        this.isCodPayment = isCodPayment;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(Integer additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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

    public String getSellerInvoice() {
        return sellerInvoice;
    }

    public void setSellerInvoice(String sellerInvoice) {
        this.sellerInvoice = sellerInvoice;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public List<Object> getAllOrderItemIds() {
        return allOrderItemIds;
    }

    public void setAllOrderItemIds(List<Object> allOrderItemIds) {
        this.allOrderItemIds = allOrderItemIds;
    }

    public List<Object> getTransactionOrders() {
        return transactionOrders;
    }

    public void setTransactionOrders(List<Object> transactionOrders) {
        this.transactionOrders = transactionOrders;
    }
}
