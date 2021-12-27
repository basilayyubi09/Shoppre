package com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.PaymentSummaryViewPagerAdapter;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.BoxWeightAdapter;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.BoxWeight;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentDetails;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentUpdates;
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

    TextView tvPackageLevelCharges, tvPackageConsolidation, tvViewPhoto, tvSplitPackage, tvPackageReturn, tvWrongAddress, tvScanDocument, tvReceiveMail, tvPickupAmount;
    LinearLayout viewPhotoLayout, splitPackageLayout, packageReturnLayout, wrongAddressLayout, scanDocumentLayout, receiveMailLayout, pickUpAmountLayout;
    LinearLayout discardShoeBoxLayout, expressProcessingLayout, specialItemClearanceLayout, giftNoteLayout, giftWrapLayout, overWeightLayout, commercialShipmentLayout, outSideDeliveryAreaLayout, pickupChargesLayout;

    TextView tvShipmentLevelCharges, tvDiscardShoesBox, tvExpressProcessing, tvSpecialItemClearance, tvGiftNote, tvGiftWrap, tvOverWeight, tvCommercialShipment, tvOutsideDeliveryArea, tvPickupCharges;
    TextView tvDelayCharges, tvAdditionalStorage, tvPaymentDelayFees, tvServiceChargeTotal, tvShippingCharge, tvServiceCharge, tvDiscountOrAdditional, tvPackageLevelDiscount, tvShipmentLevelCharges2, tvMemberShipSavings, tvShippingChargeDiscount, tvFinalShippingCharges, actualWeightText;
    LinearLayout additionalStorageLayout, paymentDelayLayout, packageLevelLayout, packageLevelChargesLayout, boxWeight, shippingLevelLayout;

    ViewPager viewPager;
    PaymentSummaryViewPagerAdapter viewPagerAdapter;
    TabLayout paymentSummaryTabLayout;
    ShipmentDetails shipmentDetails;
    ShipmentUpdates shipmentUpdates;
    View vPickUp1, vPickUp2, receiveMailView, splitPackageView, shipmentLevelChargesLayout;
    RecyclerView boxRecycler;
    BoxWeightAdapter boxWeightAdapter;
    List<BoxWeight> list;
    int size;

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
        tvMemberShipSavings = view.findViewById(R.id.tvMemberShipSavings);
        packageLevelLayout = view.findViewById(R.id.packageLevelLayout);
        tvShippingChargeDiscount = view.findViewById(R.id.tvShippingChargeDiscount);
        packageLevelChargesLayout = view.findViewById(R.id.packageLevelChargesLayout);
        vPickUp1 = view.findViewById(R.id.vPickupView);
        vPickUp2 = view.findViewById(R.id.vPickupView2);
        receiveMailView = view.findViewById(R.id.receiveMailView);
        splitPackageView = view.findViewById(R.id.splitPackageView);
        shipmentLevelChargesLayout = view.findViewById(R.id.shipmentLevelChargesLayout);
        tvFinalShippingCharges = view.findViewById(R.id.tvFinalShippingCharges);
        boxRecycler = view.findViewById(R.id.boxRecycler);
        boxWeight = view.findViewById(R.id.boxWeight);
        actualWeightText = view.findViewById(R.id.actualWeightText);
        actualWeightLayout = view.findViewById(R.id.actualWeightLayout);
        boxDimensionLayout = view.findViewById(R.id.boxDimensionLayout);
        volumetricWeightLayout = view.findViewById(R.id.volumetricWeightLayout);
        shippingLevelLayout = view.findViewById(R.id.shippingLevelLayout);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            shipmentId = bundle.getInt("shipmentId");
            size = bundle.getInt("size");

        }

        paymentSummaryTabLayout = view.findViewById(R.id.paymentSumaryTabLayout);
        viewPager = view.findViewById(R.id.paymentSummaryViewPager);

        shipmentDetails = new ShipmentDetails();
        shipmentUpdates = new ShipmentUpdates();

        Bundle bundle1 = new Bundle();
        bundle1.putInt("id",shipmentId);
        shipmentDetails.setArguments(bundle1);
        shipmentUpdates.setArguments(bundle1);

        viewPagerAdapter = new PaymentSummaryViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(shipmentDetails, "Shipment Details" + " (" + String.valueOf(size) + ")");
        viewPagerAdapter.addFragment(shipmentUpdates, "Shipment Updates");
        viewPager.setAdapter(viewPagerAdapter);

        paymentSummaryTabLayout.setupWithViewPager(viewPager);



        LoadingDialog.showLoadingDialog(getActivity(), "");
        callPaymentSummaryApi();


        shippingChargesDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    hideableLayout.setVisibility(View.VISIBLE);
                    dropBtn.setImageDrawable(getContext().getDrawable(R.drawable.down_black_play_btn));
                    flag = 2;
                } else if (flag == 2) {
                    hideableLayout.setVisibility(View.GONE);
                    dropBtn.setImageDrawable(getContext().getDrawable(R.drawable.black_play_btn));
                    flag = 1;
                }
            }
        });

        serviceChargesSummaryDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag2 == 3) {
                    hideableLayout2.setVisibility(View.VISIBLE);
                    dropBtn2.setImageDrawable(getContext().getDrawable(R.drawable.down_black_play_btn));
                    flag2 = 4;
                } else if (flag2 == 4) {
                    hideableLayout2.setVisibility(View.GONE);
                    dropBtn2.setImageDrawable(getContext().getDrawable(R.drawable.black_play_btn));
                    flag2 = 3;
                }
            }
        });


        return view;
    }

    private void callPaymentSummaryApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ShipmentDetailsModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentDetails("Bearer " + sharedPrefManager.getBearerToken(), shipmentId);
        call.enqueue(new Callback<ShipmentDetailsModelResponse>() {
            @Override
            public void onResponse(Call<ShipmentDetailsModelResponse> call, Response<ShipmentDetailsModelResponse> response) {
                if (response.code() == 200) {
                    modelResponse = response.body();

                    setPaymentSummaryDetails();
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                } else {
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
        int shippingChargeTotal = 0;

        addressName.setText(modelResponse.getShipment().getCustomerName());
        address.setText(modelResponse.getShipment().getAddress());
        addressPhoneNo.setText(modelResponse.getShipment().getPhone());




        if (modelResponse.getShippingChargeSummary().getTotalItemValue()!=null){
            if (modelResponse.getShippingChargeSummary().getTotalItemValue()!=0){
                tvTotalItemValue.setText(String.valueOf("₹" + modelResponse.getShippingChargeSummary().getTotalItemValue()));
            }else {
                totalItemLayout.setVisibility(View.GONE);
            }
        }else {
            totalItemLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getShippingChargeSummary().getTotalPackageCount()!=null){
            if(modelResponse.getShippingChargeSummary().getTotalPackageCount()!=0){
                tvTotalPackageCount.setText(String.valueOf(modelResponse.getShippingChargeSummary().getTotalPackageCount()));
            }else {
                totalPackageCountLayout.setVisibility(View.GONE);
            }
        }else {
            totalPackageCountLayout.setVisibility(View.GONE);
        }

        if (modelResponse.getShippingChargeSummary().getBoxWeights().size()==1){

            actualWeightLayout.setVisibility(LinearLayout.VISIBLE);
            boxDimensionLayout.setVisibility(View.VISIBLE);
            volumetricWeightLayout.setVisibility(View.VISIBLE);

                tvActualWeight.setText(String.valueOf(modelResponse.getShippingChargeSummary().getBoxWeights().get(0).getActualWeight()));
                tvBoxDimenson.setText(String.valueOf(modelResponse.getShippingChargeSummary().getBoxWeights().get(0).getBoxLength()+"*"+modelResponse.getShippingChargeSummary().getBoxWeights().get(0).getBoxWidth()+"*"+modelResponse.getShippingChargeSummary().getBoxWeights().get(0).getBoxHeight()+" cm"));
                tvVolumetricWeight.setText(String.valueOf(modelResponse.getShippingChargeSummary().getBoxWeights().get(0).getVolumetricWeight()));
                boxRecycler.setVisibility(View.GONE);
                boxWeight.setVisibility(View.GONE);

        }else {

            actualWeightLayout.setVisibility(LinearLayout.GONE);
            boxDimensionLayout.setVisibility(View.GONE);
            volumetricWeightLayout.setVisibility(View.GONE);



            boxWeight.setVisibility(View.VISIBLE);
            list = modelResponse.getShippingChargeSummary().getBoxWeights();

            boxWeightAdapter = new BoxWeightAdapter(list, getActivity());
            boxRecycler.setAdapter(boxWeightAdapter);
        }




//        if (modelResponse.getShippingChargeSummary().getBoxWeights().size() < 1) {
//            actualWeightLayout.setVisibility(View.GONE);
//        } else {
//            for (int i = 0; i < modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++) {
//                sum += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getActualWeight();
//            }
//            (String.valueOf(sum + " Kg"));
//
//        }
//        if (modelResponse.getShippingChargeSummary().getBoxWeights().size() < 1) {
//            boxDimensionLayout.setVisibility(View.GONE);
//        } else {
//            int boxLength = 0;
//            for (int i = 0; i < modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++) {
//                boxLength += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getBoxLength();
//            }
//            int boxWidth = 0;
//            for (int i = 0; i < modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++) {
//                boxWidth += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getBoxWidth();
//            }
//            int boxHeight = 0;
//            for (int i = 0; i < modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++) {
//                boxHeight += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getBoxHeight();
//            }
//            tvBoxDimenson.setText(String.valueOf(boxLength + "*" + boxWidth + "*" + boxHeight + " cm"));
//        }
//        if (modelResponse.getShippingChargeSummary().getBoxWeights().size() < 1) {
//            volumetricWeightLayout.setVisibility(View.GONE);
//        } else {
//            for (int i = 0; i < modelResponse.getShippingChargeSummary().getBoxWeights().size(); i++) {
//                volumetricWeight += modelResponse.getShippingChargeSummary().getBoxWeights().get(i).getVolumetricWeight();
//            }
//            tvVolumetricWeight.setText(String.valueOf(volumetricWeight + " Kg"));
//        }


        if (modelResponse.getShippingChargeSummary().getFinalChargeableWeight()!=null){
            if (modelResponse.getShippingChargeSummary().getFinalChargeableWeight()!=0){
                tvFinalChargeableWeight.setText(String.valueOf(modelResponse.getShippingChargeSummary().getFinalChargeableWeight() + " Kg"));
            }else {
                finalChargeableLayout.setVisibility(View.GONE);
            }
        }else {
            finalChargeableLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getShippingChargeSummary().getSubTotal()!=null){
            if (modelResponse.getShippingChargeSummary().getSubTotal()!=0){
                tvSubTotal.setText(String.valueOf("₹" + modelResponse.getShippingChargeSummary().getSubTotal()));
            }else {
                subTotalLayout.setVisibility(View.GONE);
            }
        }else {
            subTotalLayout.setVisibility(View.GONE);
        }



        if (modelResponse.getShippingChargeSummary().getShippingDiscount()!=null){
            if (modelResponse.getShippingChargeSummary().getShippingDiscount()!=0){
                tvShippingDiscount.setText(String.valueOf("₹" + modelResponse.getShippingChargeSummary().getShippingDiscount()));
            }else {
                shippingDiscountLayout.setVisibility(View.GONE);
            }
        }else {
            shippingDiscountLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()!=null){
            if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()!=0){
                tvBasicShippingChargeTotal.setText(String.valueOf("₹" + modelResponse.getShippingChargeSummary().getBasicShippingCosts()));
            }else {
                basicShippingChargeTotalLayout.setVisibility(View.GONE);
            }
        }else {
            basicShippingChargeTotalLayout.setVisibility(View.GONE);
        }



        if (modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()!=null){
            if (modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()!=0){
                tvMemberShipDiscount.setText(String.valueOf("-₹" + modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()));
            }else {
                memberShipDiscountLayout.setVisibility(View.GONE);
            }
        }else {
            memberShipDiscountLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()!=null){
            if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()!=0){
                tvShippingChargeTotal.setText(String.valueOf("₹ "+modelResponse.getShippingChargeSummary().getBasicShippingCosts()));
            }else {
                tvShippingChargeTotal.setText("₹0");
            }
        }else {
            tvShippingChargeTotal.setText("₹0");
        }

//        if (modelResponse.getShippingChargeSummary().getBasicShippingCosts() == null || modelResponse.getShippingChargeSummary().getBasicShippingCosts() == 0) {
//            tvShippingChargeTotal.setText("₹0");
//        } else {
//            shippingChargeTotal = (int) (modelResponse.getShippingChargeSummary().getBasicShippingCosts() - modelResponse.getServiceChargesSummary().getMembershipTotalDiscount());
//            tvShippingChargeTotal.setText(String.valueOf("₹" + shippingChargeTotal));
//        }



        if (modelResponse.getServiceChargesSummary().getOriginalPlcChargesTotal()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcChargesTotal()!=0){
                tvPackageLevelCharges.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcChargesTotal()));
            }else {
                tvPackageLevelCharges.setText("₹0");
            }
        }else {
            tvPackageLevelCharges.setText("₹0");
        }



//////package Consolidation
        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getConsolidationChargeAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getConsolidationChargeAmount()!=0){
                tvPackageConsolidation.setText(String.valueOf("₹"+modelResponse.getServiceChargesSummary().getShipmentMeta().getConsolidationChargeAmount()));
            }else {
                packageConsolidationLayout.setVisibility(View.GONE);
            }
        }else {
            packageConsolidationLayout.setVisibility(View.GONE);
        }


        ////////

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPhotoAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPhotoAmount()!=0){
                tvViewPhoto.setText(String.valueOf("₹"+modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPhotoAmount()));
            }else {
                viewPhotoLayout.setVisibility(View.GONE);
            }
        }else {
            viewPhotoLayout.setVisibility(View.GONE);
        }

        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSplitPackageAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSplitPackageAmount()!=0){
                tvSplitPackage.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getSplitPackageAmount()));
            }else {
                splitPackageLayout.setVisibility(View.GONE);
            }
        }else {
            splitPackageLayout.setVisibility(View.GONE);
        }




        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPackageReturn()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPackageReturn()!=0){
                tvPackageReturn.setText(String.valueOf("₹"+modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPackageReturn()));
            }else {
                packageReturnLayout.setVisibility(View.GONE);
            }
        }else {
            packageReturnLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getWrongAddressAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getWrongAddressAmount()!=0){
                tvWrongAddress.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getWrongAddressAmount()));
            }else {
                wrongAddressLayout.setVisibility(View.GONE);
            }
        }else {
            wrongAddressLayout.setVisibility(View.GONE);
        }



        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getScanDocumentAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getScanDocumentAmount()!=0){
                tvScanDocument.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getScanDocumentAmount()));
            }else {
                scanDocumentLayout.setVisibility(View.GONE);
            }
        }else {
            scanDocumentLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getReceiveMailAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getReceiveMailAmount()!=0){
                tvReceiveMail.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getReceiveMailAmount()));
            }else {
                receiveMailLayout.setVisibility(View.GONE);
            }
        }else {
            receiveMailLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()!=0){
                tvPickupAmount.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()));
            }else {
                pickUpAmountLayout.setVisibility(View.GONE);
            }
        }else {
            pickUpAmountLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()!=0){
                tvShipmentLevelCharges.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()));
            }else {
                shipmentLevelChargesLayout.setVisibility(View.GONE);
            }
        }else {
            shipmentLevelChargesLayout.setVisibility(View.GONE);
        }

///////discard ShoesBox condition here

        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getRepackingChargeAmount()!=null) {
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getRepackingChargeAmount()!=0) {
                tvDiscardShoesBox.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getRepackingChargeAmount()));
            } else {
                discardShoeBoxLayout.setVisibility(View.GONE);
            }
        } else {
            discardShoeBoxLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getExpressProcessingChargeAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getExpressProcessingChargeAmount()!=0){
                tvExpressProcessing.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getExpressProcessingChargeAmount()));
            }else {
                expressProcessingLayout.setVisibility(View.GONE);
            }
        }else {
            expressProcessingLayout.setVisibility(View.GONE);
        }



        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getLiquidChargeAmount()!=null) {
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getLiquidChargeAmount()!=0) {
                tvSpecialItemClearance.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getLiquidChargeAmount()));
            } else {
                specialItemClearanceLayout.setVisibility(View.GONE);
            }
        } else {
            specialItemClearanceLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftNoteChargeAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftNoteChargeAmount()!=0){
                tvGiftNote.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftNoteChargeAmount()));
            }else {
                giftNoteLayout.setVisibility(View.GONE);
            }
        }else {
            giftNoteLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftWrapChargeAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftWrapChargeAmount()!=0){
                tvGiftWrap.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getGiftWrapChargeAmount()));
            }else {
                giftWrapLayout.setVisibility(View.GONE);
            }
        }else {
            giftWrapLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getOverweightChargeAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getOverweightChargeAmount()!=0){
                tvOverWeight.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getOverweightChargeAmount()));
            }else {
                overWeightLayout.setVisibility(View.GONE);
            }
        }else {
            overWeightLayout.setVisibility(View.GONE);
        }


       if (modelResponse.getServiceChargesSummary().getShipmentMeta().getCommercialChargeAmount()!=null){
           if (modelResponse.getServiceChargesSummary().getShipmentMeta().getCommercialChargeAmount()!=0){
               tvCommercialShipment.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getCommercialChargeAmount()));
           }else {
               commercialShipmentLayout.setVisibility(View.GONE);
           }
       }else {
           commercialShipmentLayout.setVisibility(View.GONE);
       }


        ////////////Outside Delivery Area Condition here

        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getRemoteAreaChargeAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getRemoteAreaChargeAmount()!=0){
                tvOutsideDeliveryArea.setText(String.valueOf("₹"+modelResponse.getServiceChargesSummary().getShipmentMeta().getRemoteAreaChargeAmount()));
            }else {
                outSideDeliveryAreaLayout.setVisibility(View.GONE);
            }
        }else {
            outSideDeliveryAreaLayout.setVisibility(View.GONE);
        }


        if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()!=0){
                tvPickupCharges.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getOriginalPlcCharges().getPickupAmount()));
            }else {
                pickupChargesLayout.setVisibility(View.GONE);
            }
        }else {
            pickupChargesLayout.setVisibility(View.GONE);
        }

       if (modelResponse.getServiceChargesSummary().getDelayCharges()!=null){
           if (modelResponse.getServiceChargesSummary().getDelayCharges()!=0){
               tvDelayCharges.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getDelayCharges()));
           }else {
               tvDelayCharges.setText("0");
           }
       }else {
           tvDelayCharges.setText("0");
       }



        /////additional Storage fees condition here

//        if (modelResponse.getServiceChargesSummary().getActualPlcCharges().getStorageAmount()!=null){
//            if (modelResponse.getServiceChargesSummary().getActualPlcCharges().getStorageAmount()!=0){
//                tvAdditionalStorage.setText(modelResponse.getServiceChargesSummary().getActualPlcCharges().getStorageAmount());
//            }else {
//                additionalStorageLayout.setVisibility(View.GONE);
//            }
//        }else {
//            additionalStorageLayout.setVisibility(View.GONE);
//        }


        if (modelResponse.getServiceChargesSummary().getShipmentMeta().getPaymentDelayCharges()!=null) {
            if (modelResponse.getServiceChargesSummary().getShipmentMeta().getPaymentDelayCharges() == 0) {
                paymentDelayLayout.setVisibility(View.GONE);
            } else {
                tvPaymentDelayFees.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentMeta().getPaymentDelayCharges()));
            }
        } else {
            paymentDelayLayout.setVisibility(View.GONE);
        }

        if (modelResponse.getServiceChargesSummary().getServiceChargesTotal()!=null){
            if (modelResponse.getServiceChargesSummary().getServiceChargesTotal()!=0){
                tvServiceChargeTotal.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getServiceChargesTotal()));
            }else {
                tvServiceChargeTotal.setText("₹0");
            }
        }else {
            tvServiceChargeTotal.setText("₹0");
        }




            if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()!=null){
                if (modelResponse.getShippingChargeSummary().getBasicShippingCosts()!=0){
                    tvShippingCharge.setText(String.valueOf(modelResponse.getShippingChargeSummary().getBasicShippingCosts()));
                }else {
                    shippingLevelLayout.setVisibility(View.GONE);
                }
            }else {
                shippingLevelLayout.setVisibility(View.GONE);
            }

///////////////////////////////////////////////////////////////////////

        if (modelResponse.getServiceChargesSummary().getServiceChargesTotal()!=null){
            if (modelResponse.getServiceChargesSummary().getServiceChargesTotal()!=0){
                tvServiceCharge.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getServiceChargesTotal()));
            }else {
                tvServiceCharge.setText("₹0");
            }
        }else {
            tvServiceCharge.setText("₹0");
        }


        if (modelResponse.getServiceChargesSummary().getDiscountOrAdditionalCharges()!=null){
            if (modelResponse.getServiceChargesSummary().getDiscountOrAdditionalCharges()!=0){
                tvDiscountOrAdditional.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getDiscountOrAdditionalCharges()));
            }else {
                tvDiscountOrAdditional.setText("₹0");
            }
        }else {
            tvDiscountOrAdditional.setText("₹0");
        }


        if (modelResponse.getServiceChargesSummary().getPackageLevelDiscount()!=null){
            if (modelResponse.getServiceChargesSummary().getPackageLevelDiscount()!=0){
                tvPackageLevelDiscount.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getPackageLevelDiscount()));
            }else {
                packageLevelLayout.setVisibility(View.GONE);
            }
        }else {
            packageLevelLayout.setVisibility(View.GONE);
        }



        if (modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()!=null){
            if (modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()!=0){
                tvShipmentLevelCharges2.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getShipmentLevelChargesAmount()));
            }else {
                tvShipmentLevelCharges2.setText("₹0");
            }
        }else {
            tvShipmentLevelCharges2.setText("₹0");
        }


        if (modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()!=null){
            if (modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()!=0){
                tvMemberShipSavings.setText(String.valueOf("₹" + modelResponse.getServiceChargesSummary().getMembershipTotalDiscount()));
            }else {
                tvMemberShipSavings.setText("₹0");
            }
        }else {
            tvMemberShipSavings.setText("₹0");
        }




//        tvShippingChargeDiscount.setText("Shipping Charge Discount " + "(" + modelResponse.getServiceChargesSummary().getMembershipShippingDiscount() + "%)");


        if (modelResponse.getShipment().getEstimatedAmount()!=null){
            if (modelResponse.getShipment().getEstimatedAmount()!=0){
                tvFinalShippingCharges.setText(String.valueOf("₹ "+modelResponse.getShipment().getEstimatedAmount()));
            }else {
                tvFinalShippingCharges.setText("₹ 00.00");
            }
        }else {
            tvFinalShippingCharges.setText("₹ 00.00");
        }



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