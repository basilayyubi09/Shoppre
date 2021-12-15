package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncomingPkg {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_doc")
    @Expose
    private Boolean isDoc;
    @SerializedName("store_name")
    @Expose
    private Object storeName;
    @SerializedName("tracking_code")
    @Expose
    private Object trackingCode;
    @SerializedName("invoice_code")
    @Expose
    private Object invoiceCode;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("weight")
    @Expose
    private Object weight;
    @SerializedName("price_amount")
    @Expose
    private Object priceAmount;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("package_type")
    @Expose
    private String packageType;
    @SerializedName("total_quantity")
    @Expose
    private Integer totalQuantity;
    @SerializedName("personal_shopper_cost")
    @Expose
    private Object personalShopperCost;
    @SerializedName("delivery_charge")
    @Expose
    private Object deliveryCharge;
    @SerializedName("sales_tax")
    @Expose
    private Object salesTax;
    @SerializedName("payment_gateway_fee")
    @Expose
    private Object paymentGatewayFee;
    @SerializedName("sub_total")
    @Expose
    private Object subTotal;
    @SerializedName("amount_paid")
    @Expose
    private Object amountPaid;
    @SerializedName("seller_invoice")
    @Expose
    private Object sellerInvoice;
    @SerializedName("wallet")
    @Expose
    private Object wallet;
    @SerializedName("promo_code")
    @Expose
    private Object promoCode;
    @SerializedName("promo_discount")
    @Expose
    private Object promoDiscount;
    @SerializedName("promo_info")
    @Expose
    private Object promoInfo;
    @SerializedName("if_promo_unavailable")
    @Expose
    private Object ifPromoUnavailable;
    @SerializedName("instruction")
    @Expose
    private Object instruction;
    @SerializedName("payment_gateway_name")
    @Expose
    private Object paymentGatewayName;
    @SerializedName("payment_status")
    @Expose
    private Object paymentStatus;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("return_send")
    @Expose
    private Object returnSend;
    @SerializedName("splitting_directions")
    @Expose
    private Object splittingDirections;
    @SerializedName("order_code")
    @Expose
    private Object orderCode;
    @SerializedName("buy_if_price_changed")
    @Expose
    private Object buyIfPriceChanged;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("package_received_date")
    @Expose
    private Object packageReceivedDate;
    @SerializedName("is_restricted_item")
    @Expose
    private Object isRestrictedItem;
    @SerializedName("is_wrong_item")
    @Expose
    private Object isWrongItem;
    @SerializedName("rack_number")
    @Expose
    private Object rackNumber;
    @SerializedName("is_independence_offer")
    @Expose
    private Integer isIndependenceOffer;
    @SerializedName("payment_completed_date")
    @Expose
    private Object paymentCompletedDate;
    @SerializedName("payment_gateway_id")
    @Expose
    private Integer paymentGatewayId;
    @SerializedName("exchange_directions")
    @Expose
    private Object exchangeDirections;
    @SerializedName("upload_type")
    @Expose
    private Integer uploadType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("shipment_id")
    @Expose
    private Object shipmentId;
    @SerializedName("package_state_id")
    @Expose
    private Integer packageStateId;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("store_id")
    @Expose
    private Object storeId;
    @SerializedName("PackageState")
    @Expose
    private PackageState packageState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDoc() {
        return isDoc;
    }

    public void setIsDoc(Boolean isDoc) {
        this.isDoc = isDoc;
    }

    public Object getStoreName() {
        return storeName;
    }

    public void setStoreName(Object storeName) {
        this.storeName = storeName;
    }

    public Object getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(Object trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Object getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(Object invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Object getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Object priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Object getPersonalShopperCost() {
        return personalShopperCost;
    }

    public void setPersonalShopperCost(Object personalShopperCost) {
        this.personalShopperCost = personalShopperCost;
    }

    public Object getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Object deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Object getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Object salesTax) {
        this.salesTax = salesTax;
    }

    public Object getPaymentGatewayFee() {
        return paymentGatewayFee;
    }

    public void setPaymentGatewayFee(Object paymentGatewayFee) {
        this.paymentGatewayFee = paymentGatewayFee;
    }

    public Object getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Object subTotal) {
        this.subTotal = subTotal;
    }

    public Object getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Object amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Object getSellerInvoice() {
        return sellerInvoice;
    }

    public void setSellerInvoice(Object sellerInvoice) {
        this.sellerInvoice = sellerInvoice;
    }

    public Object getWallet() {
        return wallet;
    }

    public void setWallet(Object wallet) {
        this.wallet = wallet;
    }

    public Object getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(Object promoCode) {
        this.promoCode = promoCode;
    }

    public Object getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(Object promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public Object getPromoInfo() {
        return promoInfo;
    }

    public void setPromoInfo(Object promoInfo) {
        this.promoInfo = promoInfo;
    }

    public Object getIfPromoUnavailable() {
        return ifPromoUnavailable;
    }

    public void setIfPromoUnavailable(Object ifPromoUnavailable) {
        this.ifPromoUnavailable = ifPromoUnavailable;
    }

    public Object getInstruction() {
        return instruction;
    }

    public void setInstruction(Object instruction) {
        this.instruction = instruction;
    }

    public Object getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(Object paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public Object getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Object getReturnSend() {
        return returnSend;
    }

    public void setReturnSend(Object returnSend) {
        this.returnSend = returnSend;
    }

    public Object getSplittingDirections() {
        return splittingDirections;
    }

    public void setSplittingDirections(Object splittingDirections) {
        this.splittingDirections = splittingDirections;
    }

    public Object getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Object orderCode) {
        this.orderCode = orderCode;
    }

    public Object getBuyIfPriceChanged() {
        return buyIfPriceChanged;
    }

    public void setBuyIfPriceChanged(Object buyIfPriceChanged) {
        this.buyIfPriceChanged = buyIfPriceChanged;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Object getPackageReceivedDate() {
        return packageReceivedDate;
    }

    public void setPackageReceivedDate(Object packageReceivedDate) {
        this.packageReceivedDate = packageReceivedDate;
    }

    public Object getIsRestrictedItem() {
        return isRestrictedItem;
    }

    public void setIsRestrictedItem(Object isRestrictedItem) {
        this.isRestrictedItem = isRestrictedItem;
    }

    public Object getIsWrongItem() {
        return isWrongItem;
    }

    public void setIsWrongItem(Object isWrongItem) {
        this.isWrongItem = isWrongItem;
    }

    public Object getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(Object rackNumber) {
        this.rackNumber = rackNumber;
    }

    public Integer getIsIndependenceOffer() {
        return isIndependenceOffer;
    }

    public void setIsIndependenceOffer(Integer isIndependenceOffer) {
        this.isIndependenceOffer = isIndependenceOffer;
    }

    public Object getPaymentCompletedDate() {
        return paymentCompletedDate;
    }

    public void setPaymentCompletedDate(Object paymentCompletedDate) {
        this.paymentCompletedDate = paymentCompletedDate;
    }

    public Integer getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(Integer paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    public Object getExchangeDirections() {
        return exchangeDirections;
    }

    public void setExchangeDirections(Object exchangeDirections) {
        this.exchangeDirections = exchangeDirections;
    }

    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Object getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Object shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Integer getPackageStateId() {
        return packageStateId;
    }

    public void setPackageStateId(Integer packageStateId) {
        this.packageStateId = packageStateId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getStoreId() {
        return storeId;
    }

    public void setStoreId(Object storeId) {
        this.storeId = storeId;
    }

    public PackageState getPackageState() {
        return packageState;
    }

    public void setPackageState(PackageState packageState) {
        this.packageState = packageState;
    }
}
