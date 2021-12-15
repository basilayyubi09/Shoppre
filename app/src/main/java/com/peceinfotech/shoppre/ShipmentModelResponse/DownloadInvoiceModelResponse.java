package com.peceinfotech.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadInvoiceModelResponse {

    @SerializedName("invoice_object")
    @Expose
    private String invoiceObject;

    public String getInvoiceObject() {
        return invoiceObject;
    }

    public void setInvoiceObject(String invoiceObject) {
        this.invoiceObject = invoiceObject;
    }

}
