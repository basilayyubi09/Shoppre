package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.OrderModuleResponses.Facets;

import java.util.List;

public class PreviousShipmentModelResponse {
    @SerializedName("shipments")
    @Expose
    private List<Shipment> shipments = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("facets")
    @Expose
    private Facets facets;

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }
}
