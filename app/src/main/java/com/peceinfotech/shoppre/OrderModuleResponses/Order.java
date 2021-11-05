package com.peceinfotech.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price_amount")
    @Expose
    private Integer priceAmount;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("total_quantity")
    @Expose
    private Integer totalQuantity;
    @SerializedName("personal_shopper_cost")
    @Expose
    private Integer personalShopperCost;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("sales_tax")
    @Expose
    private String salesTax;
    @SerializedName("payment_gateway_fee")
    @Expose
    private String paymentGatewayFee;
    @SerializedName("sub_total")
    @Expose
    private Integer subTotal;
    @SerializedName("amount_paid")
    @Expose
    private String amountPaid;
    @SerializedName("seller_invoice")
    @Expose
    private String sellerInvoice;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("promo_discount")
    @Expose
    private String promoDiscount;
    @SerializedName("promo_info")
    @Expose
    private String promoInfo;
    @SerializedName("if_promo_unavailable")
    @Expose
    private String ifPromoUnavailable;
    @SerializedName("instruction")
    @Expose
    private String instruction;
    @SerializedName("payment_gateway_name")
    @Expose
    private String paymentGatewayName;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("buy_if_price_changed")
    @Expose
    private String buyIfPriceChanged;
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;
    @SerializedName("payment_completed_date")
    @Expose
    private String paymentCompletedDate;
    @SerializedName("payment_gateway_id")
    @Expose
    private Integer paymentGatewayId;
    @SerializedName("website_charges_price_change")
    @Expose
    private Integer websiteChargesPriceChange;
    @SerializedName("ps_fee_price_change")
    @Expose
    private Integer psFeePriceChange;
    @SerializedName("credit_coupon_benifits")
    @Expose
    private Integer creditCouponBenifits;
    @SerializedName("object_invoice")
    @Expose
    private String objectInvoice;
    @SerializedName("is_cod_payment")
    @Expose
    private String isCodPayment;
    @SerializedName("additional_charges")
    @Expose
    private Integer additionalCharges;
    @SerializedName("shopper_order_id")
    @Expose
    private Integer shopperOrderId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("order_state_id")
    @Expose
    private Integer orderStateId;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("Store")
    @Expose
    private Store store;
    @SerializedName("OrderState")
    @Expose
    private OrderState orderState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Integer priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getPersonalShopperCost() {
        return personalShopperCost;
    }

    public void setPersonalShopperCost(Integer personalShopperCost) {
        this.personalShopperCost = personalShopperCost;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public String getPaymentGatewayFee() {
        return paymentGatewayFee;
    }

    public void setPaymentGatewayFee(String paymentGatewayFee) {
        this.paymentGatewayFee = paymentGatewayFee;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getSellerInvoice() {
        return sellerInvoice;
    }

    public void setSellerInvoice(String sellerInvoice) {
        this.sellerInvoice = sellerInvoice;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(String promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public String getPromoInfo() {
        return promoInfo;
    }

    public void setPromoInfo(String promoInfo) {
        this.promoInfo = promoInfo;
    }

    public String getIfPromoUnavailable() {
        return ifPromoUnavailable;
    }

    public void setIfPromoUnavailable(String ifPromoUnavailable) {
        this.ifPromoUnavailable = ifPromoUnavailable;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBuyIfPriceChanged() {
        return buyIfPriceChanged;
    }

    public void setBuyIfPriceChanged(String buyIfPriceChanged) {
        this.buyIfPriceChanged = buyIfPriceChanged;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentCompletedDate() {
        return paymentCompletedDate;
    }

    public void setPaymentCompletedDate(String paymentCompletedDate) {
        this.paymentCompletedDate = paymentCompletedDate;
    }

    public Integer getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(Integer paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    public Integer getWebsiteChargesPriceChange() {
        return websiteChargesPriceChange;
    }

    public void setWebsiteChargesPriceChange(Integer websiteChargesPriceChange) {
        this.websiteChargesPriceChange = websiteChargesPriceChange;
    }

    public Integer getPsFeePriceChange() {
        return psFeePriceChange;
    }

    public void setPsFeePriceChange(Integer psFeePriceChange) {
        this.psFeePriceChange = psFeePriceChange;
    }

    public Integer getCreditCouponBenifits() {
        return creditCouponBenifits;
    }

    public void setCreditCouponBenifits(Integer creditCouponBenifits) {
        this.creditCouponBenifits = creditCouponBenifits;
    }

    public String getObjectInvoice() {
        return objectInvoice;
    }

    public void setObjectInvoice(String objectInvoice) {
        this.objectInvoice = objectInvoice;
    }

    public String getIsCodPayment() {
        return isCodPayment;
    }

    public void setIsCodPayment(String isCodPayment) {
        this.isCodPayment = isCodPayment;
    }

    public Integer getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(Integer additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public Integer getShopperOrderId() {
        return shopperOrderId;
    }

    public void setShopperOrderId(Integer shopperOrderId) {
        this.shopperOrderId = shopperOrderId;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderStateId() {
        return orderStateId;
    }

    public void setOrderStateId(Integer orderStateId) {
        this.orderStateId = orderStateId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
