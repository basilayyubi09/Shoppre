package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.CancelShopperResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderState__1;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.EmptyCart;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.SelfShopper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingDialog {


    public void showDialog(Context context, int id, Integer shoppreId, OrderState__1 orderState){
        final Dialog dialog = new Dialog(context);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.landing_dialog);


        MaterialButton continueOrder = dialog.findViewById(R.id.continueOrder);
        MaterialButton createNewOrder = dialog.findViewById(R.id.createNewOrder);

        continueOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                EmptyCart emptyCart = new EmptyCart();

                bundle.putInt("id" , shoppreId);
                bundle.putString("type" , "exist");
                emptyCart.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, emptyCart, null)
                        .addToBackStack(null).commit();
//                Toast.makeText(context.getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });
        createNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callCancelOrderApi(context , id , sharedPrefManager.getBearerToken());

            }
        });

        dialog.show();

    }

    private void callCancelOrderApi(Context context, int id, String bearerToken) {
        LoadingDialog.showLoadingDialog(context , "");

        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id" , id);
        jsonArray.add(jsonObject);


        Call<CancelShopperResponse> call = RetrofitClient3
                .getInstance3().getAppApi().cancel("Bearer "+ bearerToken ,jsonArray.toString());
        call.enqueue(new Callback<CancelShopperResponse>() {
            @Override
            public void onResponse(Call<CancelShopperResponse> call, Response<CancelShopperResponse> response) {
                if (response.code()==201){
                    LoadingDialog.cancelLoading();
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopper(), null)
                        .addToBackStack(null).commit();
                }
                else if (response.code()==401){
                    callRefreshTokenApi(context , id );

                }
                else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(context.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CancelShopperResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(context.getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopper(), null)
//                        .addToBackStack(null).commit();
//                Toast.makeText(context.getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
    }

    private void callRefreshTokenApi(Context context, int id) {
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context.getApplicationContext());
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
                        callCancelOrderApi(context , id , sharedPrefManager.getBearerToken());
                    } else {
                        LoadingDialog.cancelLoading();
                        Toast.makeText(context.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(context.getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }

}
