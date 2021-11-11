package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItem implements Serializable {

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
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("if_item_unavailable")
    @Expose
    private String ifItemUnavailable;
    @SerializedName("OrderItemState")
    @Expose
    private OrderItemState orderItemState;


    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;


    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }



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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIfItemUnavailable() {
        return ifItemUnavailable;
    }

    public void setIfItemUnavailable(String ifItemUnavailable) {
        this.ifItemUnavailable = ifItemUnavailable;
    }

    public OrderItemState getOrderItemState() {
        return orderItemState;
    }

    public void setOrderItemState(OrderItemState orderItemState) {
        this.orderItemState = orderItemState;
    }

}
