package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShopperOrdersResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.VertualAddress;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Shipment.ShippingCalculator;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelfShopper extends Fragment {

    SharedPrefManager sharedPrefManager;
    LinearLayout shopForMeBorder, shopForMySelfBorder;
    ImageView shopForMeCheckImage, shopForMySelfCheckImage;
    MaterialButton personalShopProceedBtn;
    CardView selfShopperVirtualAddress, selfShopperShippingCalculator, selfShopperFaqAndHelp;

    int flag = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_shopper, container, false);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        shopForMeBorder = view.findViewById(R.id.shopForMeBorder);
        shopForMySelfBorder = view.findViewById(R.id.shopForMySelfBorder);
        shopForMeCheckImage = view.findViewById(R.id.shopForMeCheckImage);
        shopForMySelfCheckImage = view.findViewById(R.id.shopForMySelfCheckImage);
        personalShopProceedBtn = view.findViewById(R.id.personalShopProceedBtn);
        selfShopperVirtualAddress = view.findViewById(R.id.selfShopperVirtualAddress);
        selfShopperShippingCalculator = view.findViewById(R.id.selfShopperShippingCalculator);
        selfShopperFaqAndHelp = view.findViewById(R.id.selfShopperFaqAndHelp);

        selfShopperVirtualAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new VertualAddress(), null)
                        .addToBackStack(null).commit();
            }
        });

        selfShopperShippingCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShippingCalculator(), null)
                        .addToBackStack(null).commit();
            }
        });


        shopForMeBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 1;

                shopForMeBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_check_border));
                shopForMeCheckImage.setVisibility(View.VISIBLE);


                shopForMySelfBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_layout_radius_2));
                shopForMySelfCheckImage.setVisibility(View.INVISIBLE);


            }
        });

        shopForMySelfBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 2;

                shopForMySelfBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_check_border_2));
                shopForMySelfCheckImage.setVisibility(View.VISIBLE);

                shopForMeBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_layout_radius));
                shopForMeCheckImage.setVisibility(View.INVISIBLE);


            }
        });

        personalShopProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callCreateShopper();


                } else if (flag == 2) {

                    if (savedInstanceState != null) return;
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopperPlaceOrderFargment(), null)
                            .addToBackStack(null).commit();

                } else {

                    flag = 0;
                    Toast.makeText(getContext(), "Please Select to Proceed", Toast.LENGTH_SHORT).show();

                }


            }
        });


        return view;
    }

    private void callCreateShopper() {
        Call<ShopperOrdersResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().shopperOrder("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<ShopperOrdersResponse>() {
            @Override
            public void onResponse(Call<ShopperOrdersResponse> call, Response<ShopperOrdersResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", response.body().getShopperOrder().getId());
                    bundle.putString("type", "new");
                    EmptyCart emptyCart = new EmptyCart();
                    emptyCart.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, emptyCart, null)
                            .commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShopperOrdersResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRefreshTokenApi() {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callCreateShopper();

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