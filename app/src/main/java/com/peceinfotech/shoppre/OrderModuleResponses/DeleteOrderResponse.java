package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteOrderResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("delivery_charge")
    @Expose
    private Object deliveryCharge;
    @SerializedName("if_promo_unavailable")
    @Expose
    private Object ifPromoUnavailable;
    @SerializedName("promo_code")
    @Expose
    private Object promoCode;
    @SerializedName("promo_info")
    @Expose
    private Object promoInfo;
    @SerializedName("sales_tax")
    @Expose
    private Object salesTax;
    @SerializedName("personal_shopper_cost")
    @Expose
    private Integer personalShopperCost;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("sub_total")
    @Expose
    private Integer subTotal;
    @SerializedName("buy_if_price_changed")
    @Expose
    private Object buyIfPriceChanged;
    @SerializedName("seller_invoice")
    @Expose
    private Object sellerInvoice;
    @SerializedName("OrderItems")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("Store")
    @Expose
    private Store store;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Object deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Object getIfPromoUnavailable() {
        return ifPromoUnavailable;
    }

    public void setIfPromoUnavailable(Object ifPromoUnavailable) {
        this.ifPromoUnavailable = ifPromoUnavailable;
    }

    public Object getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(Object promoCode) {
        this.promoCode = promoCode;
    }

    public Object getPromoInfo() {
        return promoInfo;
    }

    public void setPromoInfo(Object promoInfo) {
        this.promoInfo = promoInfo;
    }

    public Object getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Object salesTax) {
        this.salesTax = salesTax;
    }

    public Integer getPersonalShopperCost() {
        return personalShopperCost;
    }

    public void setPersonalShopperCost(Integer personalShopperCost) {
        this.personalShopperCost = personalShopperCost;
    }

    public Integer getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Object getBuyIfPriceChanged() {
        return buyIfPriceChanged;
    }

    public void setBuyIfPriceChanged(Object buyIfPriceChanged) {
        this.buyIfPriceChanged = buyIfPriceChanged;
    }

    public Object getSellerInvoice() {
        return sellerInvoice;
    }

    public void setSellerInvoice(Object sellerInvoice) {
        this.sellerInvoice = sellerInvoice;
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
}
