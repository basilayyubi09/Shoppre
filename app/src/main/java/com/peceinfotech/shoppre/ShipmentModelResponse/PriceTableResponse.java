package com.peceinfotech.shoppre.ShipmentModelResponse;

public class PriceTableResponse {

    String tableKg;
    String priceInr;
    String priceUsd;

    public PriceTableResponse(String tableKg, String priceInr, String priceUsd) {
        this.tableKg = tableKg;
        this.priceInr = priceInr;
        this.priceUsd = priceUsd;
    }

    public String getTableKg() {
        return tableKg;
    }

    public void setTableKg(String tableKg) {
        this.tableKg = tableKg;
    }

    public String getPriceInr() {
        return priceInr;
    }

    public void setPriceInr(String priceInr) {
        this.priceInr = priceInr;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }
}
