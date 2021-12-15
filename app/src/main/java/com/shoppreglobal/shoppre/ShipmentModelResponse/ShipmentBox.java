package com.shoppreglobal.shoppre.ShipmentModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipmentBox {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("box_length")
    @Expose
    private Float boxLength;
    @SerializedName("box_width")
    @Expose
    private Float boxWidth;
    @SerializedName("box_height")
    @Expose
    private Float boxHeight;
    @SerializedName("volumetric_weight")
    @Expose
    private Float volumetricWeight;
    @SerializedName("actual_weight")
    @Expose
    private Float actualWeight;
    @SerializedName("final_weight")
    @Expose
    private Float finalWeight;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("shipment_id")
    @Expose
    private Integer shipmentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getBoxLength() {
        return boxLength;
    }

    public void setBoxLength(Float boxLength) {
        this.boxLength = boxLength;
    }

    public Float getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(Float boxWidth) {
        this.boxWidth = boxWidth;
    }

    public Float getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(Float boxHeight) {
        this.boxHeight = boxHeight;
    }

    public Float getVolumetricWeight() {
        return volumetricWeight;
    }

    public void setVolumetricWeight(Float volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }

    public Float getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Float actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Float getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(Float finalWeight) {
        this.finalWeight = finalWeight;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }
}
