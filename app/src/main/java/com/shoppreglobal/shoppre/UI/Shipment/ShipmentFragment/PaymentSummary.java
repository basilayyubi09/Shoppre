package com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentSummary extends Fragment {

    LinearLayout shippingChargesDropdown, serviceChargesSummaryDropdown, hideableLayout, hideableLayout2, totalItemLayout, packageCountLayout, totalPackageCountLayout;
    LinearLayout actualWeightLayout, boxDimensionLayout, volumetricWeightLayout, finalChargeableLayout, subTotalLayout, shippingDiscountLayout, basicShippingChargeTotalLayout, memberShipDiscountLayout, packageConsolidationLayout;
    ImageView dropBtn, dropBtn2;
    int shipmentId;
    SharedPrefManager sharedPrefManager;
    ShipmentDetailsModelResponse modelResponse;
    TextView addressName, address, addressPhoneNo, tvTotalItemValue, tvTotalPackageCount, tvActualWeight, tvBoxDimenson, tvVolumetricWeight, tvFinalChargeableWeight;
    TextView tvSubTotal, tvShippingDiscount, tvBasicShippingChargeTotal, tvMemberShipDiscount, tvShippingChargeTotal;

    TextView tvPackageLevelCharges,tvPackageConsolidation, tvViewPhoto, tvSplitPackage, tvPackageReturn, tvWrongAddress, tvScanDocument, tvReceiveMail, tvPickupAmount;
    LinearLayout viewPhotoLayout, splitPackageLayout, packageReturnLayout, wrongAddressLayout, scanDocumentLayout, receiveMailLayout, pickUpAmountLayout;
    LinearLayout discardShoeBoxLayout, expressProcessingLayout, specialItemClearanceLayout, giftNoteLayout, giftWrapLayout, overWeightLayout, commercialShipmentLayout, outSideDeliveryAreaLayout, pickupChargesLayout;

    TextView tvShipmentLevelCharges, tvDiscardShoesBox, tvExpressProcessing, tvSpecialItemClearance, tvGiftNote, tvGiftWrap, tvOverWeight, tvCommercialShipment, tvOutsideDeliveryArea, tvPickupCharges;
    TextView tvDelayCharges, tvAdditionalStorage, tvPaymentDelayFees, tvServiceChargeTotal, tvShippingCharge, tvServiceCharge, tvDiscountOrAdditional, tvPackageLevelDiscount, tvShipmentLevelCharges2;
    LinearLayout additionalStorageLayout, paymentDelayLayout;

    int flag = 1;
    int flag2 = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_summary, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        shippingChargesDropdown = view.findViewById(R.id.shippingChargesDropdown);
        serviceChargesSummaryDropdown = view.findViewById(R.id.serviceChargesSummaryDropdown);
        hideableLayout = view.findViewById(R.id.hideableLayout);
        dropBtn = view.findViewById(R.id.dropBtn);
        hideableLayout2 = view.findViewById(R.id.hideableLayout2);
        dropBtn2 = view.findViewById(R.id.dropBtn2);
        addressName = view.findViewById(R.id.addressName);
        address = view.findViewById(R.id.address);
        addressPhoneNo = view.findViewById(R.id.addressPhone);
        tvTotalItemValue = view.findViewById(R.id.tvTotalItemValue);
        tvTotalPackageCount = view.findViewById(R.id.tvTotalPackageCount);
        totalItemLayout = view.findViewById(R.id.totalItemLayout);
        totalPackageCountLayout = view.findViewById(R.id.totalPackageCountLayout);
        tvActualWeight = view.findViewById(R.id.actualWeight);
        tvFinalChargeableWeight = view.findViewById(R.id.tvFinalChargeWeight);
        tvSubTotal = view.findViewById(R.id.tvSubTotal);
        tvShippingDiscount = view.findViewById(R.id.tvShippingDiscount);
        tvBasicShippingChargeTotal = view.findViewById(R.id.tvBasicShippingChargeTotal);
        tvMemberShipDiscount = view.findViewById(R.id.tvMemberShipDiscount);
        tvShippingChargeTotal = view.findViewById(R.id.tvShippingChargeTotal);
        finalChargeableLayout = view.findViewById(R.id.finalChargeWeightLayout);
        subTotalLayout = view.findViewById(R.id.subTotalLayout);
        shippingDiscountLayout = view.findViewById(R.id.shippingDiscountLayout);
        basicShippingChargeTotalLayout = view.findViewById(R.id.basicShippingChargeTotalLayout);
        memberShipDiscountLayout = view.findViewById(R.id.memberShipDiscountLayout);
        tvBoxDimenson = view.findViewById(R.id.tvBoxDimension);
        tvVolumetricWeight = view.findViewById(R.id.tvVolumetricWeight);
        tvFinalChargeableWeight = view.findViewById(R.id.tvFinalChargeWeight);
        tvPackageLevelCharges = view.findViewById(R.id.tvPackageLevelCharges);
        tvViewPhoto = view.findViewById(R.id.tvViewPhoto);
        tvSplitPackage = view.findViewById(R.id.tvSplitPackage);
        tvPackageReturn = view.findViewById(R.id.tvPackageReturn);
        tvWrongAddress = view.findViewById(R.id.tvWrongAddress);
        tvScanDocument = view.findViewById(R.id.tvScanDocument);
        tvReceiveMail = view.findViewById(R.id.tvReceiveMail);
        tvPickupAmount = view.findViewById(R.id.tvPickUpAmount);
        tvPackageConsolidation = view.findViewById(R.id.tvPackageConsolidation);
        packageConsolidationLayout = view.findViewById(R.id.packageConsolidationLayout);
        viewPhotoLayout = view.findViewById(R.id.viewPhotoLayout);
        splitPackageLayout = view.findViewById(R.id.splitPackageLayout);
        packageReturnLayout = view.findViewById(R.id.packageReturnLayout);
        wrongAddressLayout = view.findViewById(R.id.wrongAddressLayout);
        scanDocumentLayout = view.findViewById(R.id.scanDocumnetLayout);
        receiveMailLayout = view.findViewById(R.id.receiveMailLayout);
        pickUpAmountLayout = view.findViewById(R.id.pickUpAmountLayout);
        discardShoeBoxLayout = view.findViewById(R.id.discardShoesBoxLayout);
        expressProcessingLayout = view.findViewById(R.id.expressProcessingLayout);
        specialItemClearanceLayout = view.findViewById(R.id.specialItemClearanceLayout);
        giftNoteLayout = view.findViewById(R.id.giftNoteLayout);
        giftWrapLayout = view.findViewById(R.id.giftWrapLayout);
        overWeightLayout = view.findViewById(R.id.overWeightLayout);
        commercialShipmentLayout = view.findViewById(R.id.commercialShipmentLayout);
        outSideDeliveryAreaLayout = view.findViewById(R.id.outSideDeliveryAreaLayout);
        pickupChargesLayout = view.findViewById(R.id.pickUpChargesLayout);
        tvShipmentLevelCharges = view.findViewById(R.id.tvShipmentLevelCharges);
        tvDiscardShoesBox = view.findViewById(R.id.tvDiscardShoesBox);
        tvExpressProcessing = view.findViewById(R.id.tvExpressProcessing);
        tvSpecialItemClearance = view.findViewById(R.id.tvSpecialItemClearance);
        tvGiftNote = view.findViewById(R.id.tvGiftNote);
        tvGiftWrap = view.findViewById(R.id.tvGiftWrap);
        tvOverWeight = view.findViewById(R.id.tvOverWeight);
        tvCommercialShipment = view.findViewById(R.id.tvCommercialShipment);
        tvOutsideDeliveryArea = view.findViewById(R.id.tvOutSideDeliveryArea);
        tvPickupCharges = view.findViewById(R.id.tvPickUpCharges);
        tvDelayCharges = view.findViewById(R.id.tvDelayCharges);
        tvAdditionalStorage = view.findViewById(R.id.tvAdditionalStorage);
        tvPaymentDelayFees = view.findViewById(R.id.tvPaymentDelay);
        additionalStorageLayout = view.findViewById(R.id.additionalStorageLayout);
        paymentDelayLayout = view.findViewById(R.id.paymentDelayLayout);
        tvServiceChargeTotal = view.findViewById(R.id.tvServiceChargeTotal);
        tvShippingCharge = view.findViewById(R.id.tvShippingCharge);
        tvServiceCharge = view.findViewById(R.id.tvServiceCharge);
        tvDiscountOrAdditional = view.findViewById(R.id.tvDiscountOrAdditional);
        tvPackageLevelDiscount = view.findViewById(R.id.tvPackageLevelDiscount);
        tvShipmentLevelCharges2 = view.findViewById(R.id.tvShipmentLevelCharges2);


        Bundle bundle = this.getArguments();
        if (bundle!=null){
           shipmentId = bundle.getInt("shipmentId");
        }


        callPaymentSummaryApi();


        shippingChargesDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1){
                    hideableLayout.setVisibility(View.VISIBLE);
                    dropBtn.setImageDrawable(getContext().getDrawable(R.drawable.down_black_play_btn));
                    flag = 2;
                }else if (flag==2){
                    hideableLayout.setVisibility(View.GONE);
                    dropBtn.setImageDrawable(getContext().getDrawable(R.drawable.black_play_btn));
                    flag=1;
                }
            }
        });
        
        serviceChargesSummaryDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag2==3){
                    hideableLayout2.setVisibility(View.VISIBLE);
                    dropBtn2.setImageDrawable(getContext().getDrawable(R.drawable.down_black_play_btn));
                    flag2=4;
                }else if (flag2==4){
                    hideableLayout2.setVisibility(View.GONE);
                    dropBtn2.setImageDrawable(getContext().getDrawable(R.drawable.black_play_btn));
                    flag2=3;
                }
            }
        });


        return view;
    }

    private void callPaymentSummaryApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ShipmentDetailsModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentDetails("Bearer "+sharedPrefManager.getBearerToken(), shipmentId);
        call.enqueue(new Callback<ShipmentDetailsModelResponse>() {
            @Override
            public void onResponse(Call<ShipmentDetailsModelResponse> call, Response<ShipmentDetailsModelResponse> response) {
                if (response.code()==200){
                    modelResponse = response.body();

                    setPaymentSummaryDetails();
                    LoadingDialog.cancelLoading();

                }else if (response.code()==401){
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShipmentDetailsModelResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                LoadingDialog.cancelLoading();
            }
        });
    }

    private void setPaymentSummaryDetails() {

        int sum = 0;










        int volumetricWeight = 0;





        addressName.setText(modelResponse.getShipment().getCustomerName());
        address.setText(modelResponse.getShipment().getAddress());
        addressPhoneNo.setText(modelResponse.getShipment().getPhone());

        if (modelResponse.getShippingChargeSummary().getTotalItemValue()==null || modelResponse.getShippingChargeSummary().getTotalItemValue()==0){
            totalItemLayout.setVisibility(View.GONE);
        }else {
            tvTotalItemValue.setText(String.valueOf("₹"+modelResponse.getShippingChargeSummary().getTotalItemValue()));
        }

        if (modelResponse.getPackages().size()<1){
            totalPackageCountLayout.setVisibility(View.GONE);
        }else {
            tvTotalPackageCount.setText(String.valueOf(modelResponse.getPackages().size()));
        }

        if (modelResponse.getShippingChargeSummary().getBoxWeights().size()<1){
            actualWeightLayout.setVisibility(View.GONE);
        }else {
            for (int i = 0; i<modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++){
                sum += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getActualWeight();
            }
            tvActualWeight.setText(String.valueOf(sum));
        }

        if (modelResponse.getShippingChargeSummary().getBoxWeights().size()<1){
            boxDimensionLayout.setVisibility(View.GONE);
        }else {
            int boxLength = 0;
            for (int i=0; i<modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++){
                boxLength += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getBoxLength();
            }
            int boxWidth = 0;
            for (int i=0; i<modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++){
                boxWidth += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getBoxWidth();
            }
            int boxHeight = 0;
            for (int i=0; i<modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++){
                boxHeight += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getBoxHeight();
            }
            tvBoxDimenson.setText(String.valueOf(boxLength+"*"+boxWidth+"*"+boxHeight+" cm"));
        }

        if (modelResponse.getShippingChargeSummary().getBoxWeights().size()<1){
            volumetricWeightLayout.setVisibility(View.GONE);
        }else {
            for (int i=0; i<modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++){
                volumetricWeight += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getVolumetricWeight();
            }
            tvVolumetricWeight.setText(String.valueOf(volumetricWeight));
        }

        if (modelResponse.getShippingChargeSummary().getFinalChargeableWeight()==0){
            finalChargeableLayout.setVisibility(View.GONE);
        }else {
            tvFinalChargeableWeight.setText(String.valueOf(modelResponse.getShippingChargeSummary().getFinalChargeableWeight()));
        }

        if (modelResponse.getShippingChargeSummary().getSubTotal()==0){
            subTotalLayout.setVisibility(View.GONE);
        }else {
            tvSubTotal.setText(String.valueOf("₹"+modelResponse.getShippingChargeSummary().getSubTotal()));
        }

        if (modelResponse.getShippingChargeSummary().getShippingDiscount()==0){
            shippingDiscountLayout.setVisibility(View.GONE);
        }else {
            tvShippingDiscount.setText(String.valueOf("₹"+modelResponse.getShippingChargeSummary().getShippingDiscount()));
        }
        if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()==0){
            basicShippingChargeTotalLayout.setVisibility(View.GONE);
        }else {
            tvBasicShippingChargeTotal.setText(String.valueOf("₹"+modelResponse.getShippingChargeSummary().getBasicShippingCosts()));
        }

        if (modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()==0){
            memberShipDiscountLayout.setVisibility(View.GONE);
        }else {
            tvMemberShipDiscount.setText(String.valueOf("-₹"+modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()));
        }

        int shippingChargeTotal = (int) (modelResponse.getShippingChargeSummary().getBasicShippingCosts()-modelResponse.getServiceChargesSummary().getMembershipTotalDiscount());
        tvShippingChargeTotal.setText(String.valueOf(shippingChargeTotal));

        tvPackageLevelCharges.setText(String.valueOf(modelResponse.getShipment().getPackageLevelChargesAmount()));

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getConsolidationCharge()==0){
            packageConsolidationLayout.setVisibility(View.GONE);
        }else {
            tvPackageConsolidation.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getConsolidationCharge()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPhotoAmount()==0){
            viewPhotoLayout.setVisibility(View.GONE);
        }else {
            tvViewPhoto.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPhotoAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSplitPackageAmount()==0){
            splitPackageLayout.setVisibility(View.GONE);
        }else {
            tvSplitPackage.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSplitPackageAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPackageReturn()==0){
            packageReturnLayout.setVisibility(View.GONE);
        }else {
            tvPackageReturn.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPackageReturn()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getWrongAddressAmount()==0){
            wrongAddressLayout.setVisibility(View.GONE);
        }else {
            tvWrongAddress.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getWrongAddressAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getScanDocumentAmount()==0){
            scanDocumentLayout.setVisibility(View.GONE);
        }else {
            tvScanDocument.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getScanDocumentAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getReceiveMailAmount()==0){
            receiveMailLayout.setVisibility(View.GONE);
        }else {
            tvReceiveMail.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getReceiveMailAmount()));
        }

        tvPickupAmount.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()));

        tvShipmentLevelCharges.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()));
///////discard ShoesBox condition here





        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getExpressProcessingChargeAmount()==0){
                expressProcessingLayout.setVisibility(View.GONE);
        }else {
            tvExpressProcessing.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentMeta().getExpressProcessingChargeAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSpecialHandlingAmount()==0){
            specialItemClearanceLayout.setVisibility(View.GONE);
        }else {
            tvSpecialItemClearance.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSpecialHandlingAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftNoteChargeAmount()==0){
            giftNoteLayout.setVisibility(View.GONE);
        }else {
            tvGiftNote.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftNoteChargeAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftWrapChargeAmount()==0){
            giftWrapLayout.setVisibility(View.GONE);
        }else {
            tvGiftWrap.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftWrapChargeAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getOverweightChargeAmount()==null){
            overWeightLayout.setVisibility(View.GONE);
        }else {
            tvOverWeight.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentMeta().getOverweightChargeAmount()));
        }

        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getCommercialChargeAmount()==0){
            commercialShipmentLayout.setVisibility(View.GONE);
        }else {
            tvCommercialShipment.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentMeta().getCommercialChargeAmount()));
        }
        ////////////Outside Delivery Area Condition here








        tvPickupCharges.setText(String.valueOf(modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()));

        tvDelayCharges.setText(String.valueOf(modelResponse.getServiceChargesSummary().getDelayCharges()));

        /////additional Storage fees condition here






        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getPaymentDelayCharges()==null){
            paymentDelayLayout.setVisibility(View.GONE);
        }else {
            tvPaymentDelayFees.setText(String.valueOf(modelResponse.getServiceChargesSummary().getShipmentMeta().getPaymentDelayCharges()));
        }

        tvServiceChargeTotal.setText(String.valueOf(modelResponse.getServiceChargesSummary().getServiceChargesTotal()));


    }


    private void callRefreshTokenApi() {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}