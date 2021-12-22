package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShipmentMeta implements Serializable {

    @SerializedName("storage_amount")
    @Expose
    private Integer storageAmount;
    @SerializedName("photo_amount")
    @Expose
    private Integer photoAmount;
    @SerializedName("pickup_amount")
    @Expose
    private Integer pickupAmount;
    @SerializedName("special_handling_amount")
    @Expose
    private Integer specialHandlingAmount;
    @SerializedName("receive_mail_amount")
    @Expose
    private Integer receiveMailAmount;
    @SerializedName("split_package_amount")
    @Expose
    private Integer splitPackageAmount;
    @SerializedName("wrong_address_amount")
    @Expose
    private Integer wrongAddressAmount;
    @SerializedName("consolidation_charge_amount")
    @Expose
    private Integer consolidationChargeAmount;
    @SerializedName("optsAmount")
    @Expose
    private Integer optsAmount;
    @SerializedName("scan_document_amount")
    @Expose
    private Integer scanDocumentAmount;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("repacking_charge_amount")
    @Expose
    private Integer repackingChargeAmount;
    @SerializedName("sticker_charge_amount")
    @Expose
    private Integer stickerChargeAmount;
    @SerializedName("extra_packing_charge_amount")
    @Expose
    private Integer extraPackingChargeAmount;
    @SerializedName("original_ship_box_charge__amount")
    @Expose
    private Object originalShipBoxChargeAmount;
    @SerializedName("gift_wrap_charge_amount")
    @Expose
    private Integer giftWrapChargeAmount;
    @SerializedName("gift_note_charge_amount")
    @Expose
    private Integer giftNoteChargeAmount;
    @SerializedName("insurance_amount")
    @Expose
    private Object insuranceAmount;
    @SerializedName("other_charge_amount")
    @Expose
    private Integer otherChargeAmount;
    @SerializedName("payment_delay_charges")
    @Expose
    private Object paymentDelayCharges;
    @SerializedName("liquid_charge_amount")
    @Expose
    private Integer liquidChargeAmount;
    @SerializedName("overweight_charge_amount")
    @Expose
    private Object overweightChargeAmount;
    @SerializedName("shipment_id")
    @Expose
    private Object shipmentId;
    @SerializedName("express_processing_charge_amount")
    @Expose
    private Integer expressProcessingChargeAmount;
    @SerializedName("remote_area_charge_amount")
    @Expose
    private Integer remoteAreaChargeAmount;
    @SerializedName("commercial_charge_amount")
    @Expose
    private Integer commercialChargeAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepackingChargeAmount() {
        return repackingChargeAmount;
    }

    public void setRepackingChargeAmount(Integer repackingChargeAmount) {
        this.repackingChargeAmount = repackingChargeAmount;
    }

    public Integer getStickerChargeAmount() {
        return stickerChargeAmount;
    }

    public void setStickerChargeAmount(Integer stickerChargeAmount) {
        this.stickerChargeAmount = stickerChargeAmount;
    }

    public Integer getExtraPackingChargeAmount() {
        return extraPackingChargeAmount;
    }

    public void setExtraPackingChargeAmount(Integer extraPackingChargeAmount) {
        this.extraPackingChargeAmount = extraPackingChargeAmount;
    }

    public Object getOriginalShipBoxChargeAmount() {
        return originalShipBoxChargeAmount;
    }

    public void setOriginalShipBoxChargeAmount(Object originalShipBoxChargeAmount) {
        this.originalShipBoxChargeAmount = originalShipBoxChargeAmount;
    }

    public Integer getGiftWrapChargeAmount() {
        return giftWrapChargeAmount;
    }

    public void setGiftWrapChargeAmount(Integer giftWrapChargeAmount) {
        this.giftWrapChargeAmount = giftWrapChargeAmount;
    }

    public Integer getGiftNoteChargeAmount() {
        return giftNoteChargeAmount;
    }

    public void setGiftNoteChargeAmount(Integer giftNoteChargeAmount) {
        this.giftNoteChargeAmount = giftNoteChargeAmount;
    }

    public Object getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Object insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Integer getOtherChargeAmount() {
        return otherChargeAmount;
    }

    public void setOtherChargeAmount(Integer otherChargeAmount) {
        this.otherChargeAmount = otherChargeAmount;
    }

    public Object getPaymentDelayCharges() {
        return paymentDelayCharges;
    }

    public void setPaymentDelayCharges(Object paymentDelayCharges) {
        this.paymentDelayCharges = paymentDelayCharges;
    }

    public Integer getLiquidChargeAmount() {
        return liquidChargeAmount;
    }

    public void setLiquidChargeAmount(Integer liquidChargeAmount) {
        this.liquidChargeAmount = liquidChargeAmount;
    }

    public Object getOverweightChargeAmount() {
        return overweightChargeAmount;
    }

    public void setOverweightChargeAmount(Object overweightChargeAmount) {
        this.overweightChargeAmount = overweightChargeAmount;
    }

    public Object getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Object shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Integer getExpressProcessingChargeAmount() {
        return expressProcessingChargeAmount;
    }

    public void setExpressProcessingChargeAmount(Integer expressProcessingChargeAmount) {
        this.expressProcessingChargeAmount = expressProcessingChargeAmount;
    }

    public Integer getRemoteAreaChargeAmount() {
        return remoteAreaChargeAmount;
    }

    public void setRemoteAreaChargeAmount(Integer remoteAreaChargeAmount) {
        this.remoteAreaChargeAmount = remoteAreaChargeAmount;
    }

    public Integer getCommercialChargeAmount() {
        return commercialChargeAmount;
    }

    public void setCommercialChargeAmount(Integer commercialChargeAmount) {
        this.commercialChargeAmount = commercialChargeAmount;
    }

    public Integer getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(Integer storageAmount) {
        this.storageAmount = storageAmount;
    }

    public Integer getPhotoAmount() {
        return photoAmount;
    }

    public void setPhotoAmount(Integer photoAmount) {
        this.photoAmount = photoAmount;
    }

    public Integer getPickupAmount() {
        return pickupAmount;
    }

    public void setPickupAmount(Integer pickupAmount) {
        this.pickupAmount = pickupAmount;
    }

    public Integer getSpecialHandlingAmount() {
        return specialHandlingAmount;
    }

    public void setSpecialHandlingAmount(Integer specialHandlingAmount) {
        this.specialHandlingAmount = specialHandlingAmount;
    }

    public Integer getReceiveMailAmount() {
        return receiveMailAmount;
    }

    public void setReceiveMailAmount(Integer receiveMailAmount) {
        this.receiveMailAmount = receiveMailAmount;
    }

    public Integer getSplitPackageAmount() {
        return splitPackageAmount;
    }

    public void setSplitPackageAmount(Integer splitPackageAmount) {
        this.splitPackageAmount = splitPackageAmount;
    }

    public Integer getWrongAddressAmount() {
        return wrongAddressAmount;
    }

    public void setWrongAddressAmount(Integer wrongAddressAmount) {
        this.wrongAddressAmount = wrongAddressAmount;
    }

    public Integer getConsolidationChargeAmount() {
        return consolidationChargeAmount;
    }

    public void setConsolidationChargeAmount(Integer consolidationChargeAmount) {
        this.consolidationChargeAmount = consolidationChargeAmount;
    }

    public Integer getOptsAmount() {
        return optsAmount;
    }

    public void setOptsAmount(Integer optsAmount) {
        this.optsAmount = optsAmount;
    }

    public Integer getScanDocumentAmount() {
        return scanDocumentAmount;
    }

    public void setScanDocumentAmount(Integer scanDocumentAmount) {
        this.scanDocumentAmount = scanDocumentAmount;
    }

}
