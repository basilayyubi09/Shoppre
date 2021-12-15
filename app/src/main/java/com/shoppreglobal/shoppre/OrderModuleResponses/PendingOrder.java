package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingOrder {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shopper_order_id")
    @Expose
    private Integer shopperOrderId;
    @SerializedName("OrderState")
    @Expose
    private OrderState__1 orderState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopperOrderId() {
        return shopperOrderId;
    }

    public void setShopperOrderId(Integer shopperOrderId) {
        this.shopperOrderId = shopperOrderId;
    }

    public OrderState__1 getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState__1 orderState) {
        this.orderState = orderState;
    }
}
