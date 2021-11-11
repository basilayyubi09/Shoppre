package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopperOrdersResponse {
    @SerializedName("shopperOrder")
    @Expose
    private ShopperOrder shopperOrder;

    public ShopperOrder getShopperOrder() {
        return shopperOrder;
    }

    public void setShopperOrder(ShopperOrder shopperOrder) {
        this.shopperOrder = shopperOrder;
    }
}
