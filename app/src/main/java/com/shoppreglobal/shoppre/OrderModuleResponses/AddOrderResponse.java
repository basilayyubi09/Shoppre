package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddOrderResponse {
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("itemsCount")
    @Expose
    private Integer itemsCount;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }
}
