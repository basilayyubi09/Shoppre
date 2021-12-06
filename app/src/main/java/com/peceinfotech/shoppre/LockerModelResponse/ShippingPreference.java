package com.peceinfotech.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingPreference {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_basic_photo")
    @Expose
    private Object isBasicPhoto;
    @SerializedName("is_advanced_photo")
    @Expose
    private Object isAdvancedPhoto;
    @SerializedName("is_scan_document")
    @Expose
    private Object isScanDocument;
    @SerializedName("is_repacking")
    @Expose
    private Boolean isRepacking;
    @SerializedName("is_sticker")
    @Expose
    private Object isSticker;
    @SerializedName("is_extra_packing")
    @Expose
    private Boolean isExtraPacking;
    @SerializedName("is_original_box")
    @Expose
    private Boolean isOriginalBox;
    @SerializedName("is_gift_wrap")
    @Expose
    private Object isGiftWrap;
    @SerializedName("is_gift_note")
    @Expose
    private Object isGiftNote;
    @SerializedName("is_mark_personal_use")
    @Expose
    private Object isMarkPersonalUse;
    @SerializedName("is_include_invoice")
    @Expose
    private Object isIncludeInvoice;
    @SerializedName("max_weight")
    @Expose
    private Object maxWeight;
    @SerializedName("tax_id")
    @Expose
    private Object taxId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getIsBasicPhoto() {
        return isBasicPhoto;
    }

    public void setIsBasicPhoto(Object isBasicPhoto) {
        this.isBasicPhoto = isBasicPhoto;
    }

    public Object getIsAdvancedPhoto() {
        return isAdvancedPhoto;
    }

    public void setIsAdvancedPhoto(Object isAdvancedPhoto) {
        this.isAdvancedPhoto = isAdvancedPhoto;
    }

    public Object getIsScanDocument() {
        return isScanDocument;
    }

    public void setIsScanDocument(Object isScanDocument) {
        this.isScanDocument = isScanDocument;
    }

    public Boolean getIsRepacking() {
        return isRepacking;
    }

    public void setIsRepacking(Boolean isRepacking) {
        this.isRepacking = isRepacking;
    }

    public Object getIsSticker() {
        return isSticker;
    }

    public void setIsSticker(Object isSticker) {
        this.isSticker = isSticker;
    }

    public Boolean getIsExtraPacking() {
        return isExtraPacking;
    }

    public void setIsExtraPacking(Boolean isExtraPacking) {
        this.isExtraPacking = isExtraPacking;
    }

    public Boolean getIsOriginalBox() {
        return isOriginalBox;
    }

    public void setIsOriginalBox(Boolean isOriginalBox) {
        this.isOriginalBox = isOriginalBox;
    }

    public Object getIsGiftWrap() {
        return isGiftWrap;
    }

    public void setIsGiftWrap(Object isGiftWrap) {
        this.isGiftWrap = isGiftWrap;
    }

    public Object getIsGiftNote() {
        return isGiftNote;
    }

    public void setIsGiftNote(Object isGiftNote) {
        this.isGiftNote = isGiftNote;
    }

    public Object getIsMarkPersonalUse() {
        return isMarkPersonalUse;
    }

    public void setIsMarkPersonalUse(Object isMarkPersonalUse) {
        this.isMarkPersonalUse = isMarkPersonalUse;
    }

    public Object getIsIncludeInvoice() {
        return isIncludeInvoice;
    }

    public void setIsIncludeInvoice(Object isIncludeInvoice) {
        this.isIncludeInvoice = isIncludeInvoice;
    }

    public Object getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Object maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Object getTaxId() {
        return taxId;
    }

    public void setTaxId(Object taxId) {
        this.taxId = taxId;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
