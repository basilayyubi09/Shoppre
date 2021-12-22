package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("amount")
    @Expose
    private Float amount;
    @SerializedName("payment_gateway_id")
    @Expose
    private Object paymentGatewayId;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Object getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(Object paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }
}
