package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SlabResponse {
    @SerializedName("weight")
    @Expose
    private Float weight;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("customer_rate")
    @Expose
    private Double customerRate;
    @SerializedName("country_currency_rates")
    @Expose
    private String countryCurrencyRates;

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Double getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(Double customerRate) {
        this.customerRate = customerRate;
    }

    public String getCountryCurrencyRates() {
        return countryCurrencyRates;
    }

    public void setCountryCurrencyRates(String countryCurrencyRates) {
        this.countryCurrencyRates = countryCurrencyRates;
    }
}
