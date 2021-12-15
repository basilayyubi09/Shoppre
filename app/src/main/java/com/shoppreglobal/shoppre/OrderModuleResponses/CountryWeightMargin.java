package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryWeightMargin implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fedex_margin")
    @Expose
    private Integer fedexMargin;
    @SerializedName("dhl_margin")
    @Expose
    private Double dhlMargin;
    @SerializedName("dtdc_margin")
    @Expose
    private String dtdcMargin;
    @SerializedName("ShippingPartner")
    @Expose
    private ShippingPartner shippingPartner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFedexMargin() {
        return fedexMargin;
    }

    public void setFedexMargin(Integer fedexMargin) {
        this.fedexMargin = fedexMargin;
    }

    public Double getDhlMargin() {
        return dhlMargin;
    }

    public void setDhlMargin(Double dhlMargin) {
        this.dhlMargin = dhlMargin;
    }

    public String getDtdcMargin() {
        return dtdcMargin;
    }

    public void setDtdcMargin(String dtdcMargin) {
        this.dtdcMargin = dtdcMargin;
    }

    public ShippingPartner getShippingPartner() {
        return shippingPartner;
    }

    public void setShippingPartner(ShippingPartner shippingPartner) {
        this.shippingPartner = shippingPartner;
    }
}
