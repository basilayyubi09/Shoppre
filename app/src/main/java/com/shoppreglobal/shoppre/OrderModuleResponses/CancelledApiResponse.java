package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancelledApiResponse {
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("facets")
    @Expose
    private Facets facets;
    @SerializedName("oldfacets")
    @Expose
    private Oldfacets oldfacets;
    @SerializedName("pendingOrders")
    @Expose
    private List<PendingOrder> pendingOrders = null;
    @SerializedName("incomingPkgs")
    @Expose
    private List<IncomingPkg> incomingPkgs = null;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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

    public Oldfacets getOldfacets() {
        return oldfacets;
    }

    public void setOldfacets(Oldfacets oldfacets) {
        this.oldfacets = oldfacets;
    }

    public List<PendingOrder> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(List<PendingOrder> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public List<IncomingPkg> getIncomingPkgs() {
        return incomingPkgs;
    }

    public void setIncomingPkgs(List<IncomingPkg> incomingPkgs) {
        this.incomingPkgs = incomingPkgs;
    }
}
