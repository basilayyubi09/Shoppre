package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.OrderAdapter.ParentFinalOrderSummaryAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.CartModelResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinalOrderSummaryFragment extends Fragment {


    SharedPrefManager sharedPrefManager;
    List<Order> list = new ArrayList<>();
    RecyclerView recyclerView;
    TextView orderTotal, shoppreFee, total;
    ParentFinalOrderSummaryAdapter adapter;
    MaterialButton proceedToPayBtn;
    Integer shoppreId;
    CheckBox check;
    int totalCount, subTotal, shoppreTotal;
    String url;
    String base64;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_order_summary, container, false);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);
        totalCount = 0;
        subTotal = 0;
        shoppreTotal = 0;
        orderTotal = view.findViewById(R.id.orderTotal);
        total = view.findViewById(R.id.total);
        check = view.findViewById(R.id.check);
        recyclerView = view.findViewById(R.id.recycleFinal);
        proceedToPayBtn = view.findViewById(R.id.proceedToPayBtn);
        shoppreFee = view.findViewById(R.id.shoppreFee);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            shoppreId = bundle.getInt("id");
//            list = (List<Order>) bundle.getSerializable("list");


        }
        LoadingDialog.showLoadingDialog(getActivity(), "");
        callCartApi();


        proceedToPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (check.isChecked()) {
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putInt("id" , shoppreId);
//                    bundle1.putString("type","order");
//                    ThankYouFragment thankYouFragment = new ThankYouFragment();
//                    thankYouFragment.setArguments(bundle1);
//                    OrderActivity.fragmentManager.beginTransaction()
//                            .replace(R.id.orderFrameLayout, thankYouFragment)
//                            .addToBackStack(null).commit();

                    payAuthorizeApi();

                } else {
                    Toast.makeText(getActivity(), "Please agree Terms & Condition to continue ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    private void payAuthorizeApi() {
        JsonObject jsonObject = new JsonObject();
        String email = sharedPrefManager.getEmail();
        jsonObject.addProperty("grant_type", "loginAs");
        jsonObject.addProperty("username", email);
        jsonObject.addProperty("app_id", 28);
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<String> call = RetrofitClient.getInstance()
                .getApi().payAuthorize("Bearer " + sharedPrefManager.getBearerToken(), jsonObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + "&type=ps&params=" + base64)));

                    url = response.body();
                    LoadingDialog.cancelLoading();
                    Log.d("urlllllllll", String.valueOf(url));

//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url + "&type=ps&params=" + base64);
//                    WebViewFragment pay = new WebViewFragment();
//                    pay.setArguments(bundle);
//                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, pay, null)
//                            .addToBackStack(null).commit();

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url + "&type=ps&params=" + base64));
                    startActivity(i);

//                    Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
//                    defaultBrowser.setData(Uri.parse(url + "&type=ps&params=" + base64));
//                    startActivity(defaultBrowser);

                } else if (response.code() == 401) {
                    callRefreshTokenApi("auth");
                    LoadingDialog.cancelLoading();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callCartApi() {
        Call<CartModelResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().getCartItem("Bearer " + sharedPrefManager.getBearerToken(), shoppreId);
        call.enqueue(new Callback<CartModelResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CartModelResponse> call, Response<CartModelResponse> response) {
                if (response.code() == 200) {
                    list = response.body().getOrders();
                    JsonArray idList = new JsonArray();

                    JsonArray jsonArray = new JsonArray();
                    JsonArray idArray = new JsonArray();

                    for (int i = 0; i < list.size(); i++) {

                        idList.add(list.get(i).getOrderItems().get(i).getId());
                        shoppreTotal = shoppreTotal + list.get(i).getPersonalShopperCost();
                        totalCount = totalCount + list.get(i).getSubTotal();
                        subTotal = subTotal + list.get(i).getPriceAmount();
                        total.setText("₹ " + String.valueOf(totalCount));
                        shoppreFee.setText("₹ " + String.valueOf(shoppreTotal));
                        orderTotal.setText("₹ " + String.valueOf(subTotal));
                    }
                    for (int i = 0; i < list.size(); i++) {
                        idArray.add(list.get(i).getId());
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("id", list.get(i).getId());
                        jsonObject.addProperty("amount", list.get(i).getSubTotal());
                        jsonObject.addProperty("psAmount", list.get(i).getPersonalShopperCost());
                        jsonObject.addProperty("priceAmount", list.get(i).getPriceAmount());
                        jsonObject.addProperty("storeName", list.get(i).getStore().getName());
                        jsonArray.add(jsonObject);
                    }
                    Log.d("Array", jsonArray.toString());

                    JsonObject jsonObjectToSend = new JsonObject();
                    jsonObjectToSend.add("id", idList);
                    jsonObjectToSend.addProperty("amount", totalCount);
                    jsonObjectToSend.add("objectData", jsonArray);
                    jsonObjectToSend.addProperty("customer_id", sharedPrefManager.getId());
                    jsonObjectToSend.add("object_id", idList);
                    jsonObjectToSend.addProperty("axis_banned", false);
                    jsonObjectToSend.addProperty("type", "ps");
                    jsonObjectToSend.addProperty("cancelUrl", "paymentorders://Orders");
                    jsonObjectToSend.addProperty("ps_fee", shoppreTotal);


                    Log.d("json to send", jsonObjectToSend.toString());

                    byte[] encrpt = new byte[0];
                    try {
                        encrpt = jsonObjectToSend.toString().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    base64 = Base64.encodeToString(encrpt, Base64.DEFAULT);
                    Log.d("Base 64 String", base64);


                    adapter = new ParentFinalOrderSummaryAdapter(list, getActivity());
                    recyclerView.setAdapter(adapter);
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi("cart");
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartModelResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callRefreshTokenApi(String auth) {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    if (auth.equals("auth")) {
                        payAuthorizeApi();
                    } else {
                        callCartApi();
                    }


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