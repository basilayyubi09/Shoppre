package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitOrder {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("additional_charges")
    @Expose
    private String additionalCharges;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("buy_if_price_changed")
    @Expose
    private String buyIfPriceChanged;

    public SubmitOrder(Integer id, String orderCode, String additionalCharges, String instruction, String buyIfPriceChanged) {
        this.id = id;
        this.orderCode = orderCode;
        this.additionalCharges = additionalCharges;
        this.instruction = instruction;
        this.buyIfPriceChanged = buyIfPriceChanged;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(String additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getBuyIfPriceChanged() {
        return buyIfPriceChanged;
    }

    public void setBuyIfPriceChanged(String buyIfPriceChanged) {
        this.buyIfPriceChanged = buyIfPriceChanged;
    }
}
