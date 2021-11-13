package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShippingRateResponse implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("CountryWeightMargin")
    @Expose
    private CountryWeightMargin countryWeightMargin;
    @SerializedName("CountryBlocklistedItems")
    @Expose
    private List<Object> countryBlocklistedItems = null;
    @SerializedName("CountryZones")
    @Expose
    private List<CountryZone> countryZones = null;
    @SerializedName("customer_rate")
    @Expose
    private Double customerRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryWeightMargin getCountryWeightMargin() {
        return countryWeightMargin;
    }

    public void setCountryWeightMargin(CountryWeightMargin countryWeightMargin) {
        this.countryWeightMargin = countryWeightMargin;
    }

    public List<Object> getCountryBlocklistedItems() {
        return countryBlocklistedItems;
    }

    public void setCountryBlocklistedItems(List<Object> countryBlocklistedItems) {
        this.countryBlocklistedItems = countryBlocklistedItems;
    }

    public List<CountryZone> getCountryZones() {
        return countryZones;
    }

    public void setCountryZones(List<CountryZone> countryZones) {
        this.countryZones = countryZones;
    }

    public Double getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(Double customerRate) {
        this.customerRate = customerRate;
    }
}
