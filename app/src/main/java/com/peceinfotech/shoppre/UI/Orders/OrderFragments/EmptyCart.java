package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.CartGroupAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.AddOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.OrderModuleResponses.ProductItem;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmptyCart extends Fragment {

    SharedPrefManager sharedPrefManager;
    RecyclerView cartRecycler;
    CardView itemCartCard, productCartCard;
    ImageView downwardTriangle, upwardTriangle;
    MaterialButton proceedToCartBtn;
    int flag = 1;
    List<Order> list = new ArrayList<>();
    LinearLayout dropdownLayout;
    TextView selectField , orderTotal , shoppreFee , total;
    String url, name, color, size, price, countString, selectedString;
    EditText urlField, nameField, colorField, priceField, sizeField;
    TextView minus, plus, countField;
    MaterialCheckBox check;
    int count = 1;
    boolean isLiquid = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_cart, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        cartRecycler = view.findViewById(R.id.cartRecycler);
        itemCartCard = view.findViewById(R.id.itemInCartCard);
        productCartCard = view.findViewById(R.id.productCard);
        orderTotal = view.findViewById(R.id.orderTotal);
        shoppreFee = view.findViewById(R.id.shoppreFee);
        total = view.findViewById(R.id.total);
        downwardTriangle = view.findViewById(R.id.downwardTriangle);
        upwardTriangle = view.findViewById(R.id.upwardTriangle);
        proceedToCartBtn = view.findViewById(R.id.proceedToCartBtn);
        dropdownLayout = view.findViewById(R.id.dropdownLayut);
        selectField = view.findViewById(R.id.selectField);
        urlField = view.findViewById(R.id.urlField);
        nameField = view.findViewById(R.id.nameField);
        check = view.findViewById(R.id.check);
        plus = view.findViewById(R.id.plus);
        minus = view.findViewById(R.id.minus);
        countField = view.findViewById(R.id.countField);
        colorField = view.findViewById(R.id.colorField);
        priceField = view.findViewById(R.id.priceField);
        sizeField = view.findViewById(R.id.sizeField);



        getTextFromFields();

        count = Integer.parseInt(String.valueOf(countString));

        dropdownLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), dropdownLayout);
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public boolean onMenuItemClick(MenuItem item) {
                        selectField.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;
                countField.setText(String.valueOf(count));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count == 1) {
                    countField.setText("1");
                } else {
                    count -= 1;
                    countField.setText("" + count);
                }
            }
        });


        downwardTriangle.setVisibility(View.VISIBLE);

        itemCartCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {

                    productCartCard.setVisibility(View.VISIBLE);
                    upwardTriangle.setVisibility(View.VISIBLE);
                    downwardTriangle.setVisibility(View.GONE);

                    flag = 0;

                } else if (flag == 0) {
                    productCartCard.setVisibility(View.GONE);
                    upwardTriangle.setVisibility(View.GONE);
                    downwardTriangle.setVisibility(View.VISIBLE);

                    flag = 1;
                }
            }
        });

        proceedToCartBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                getTextFromFields();
                if (!validateUrl() || !validateName() || !validatePrice()) {
                    return;
                } else {

                    if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
//                        OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderSummaryFragment(), null)
//                                .addToBackStack(null).commit();

                        LoadingDialog.showLoadingDialog(getActivity(), "");
                        callAddOrderApi();
                    }
                }


            }
        });


        return view;
    }



    private void callAddOrderApi() {

        /*
        "color": "Black",
                "if_item_unavailable": "Cancel this item, purchase all other available items",
                "name": "3",
                "orderId": "116",
                "price_amount": 20,
                "quantity": 2,
                "shopperType": "ps",
                "size": "M",
                "url": "https://www.amazon.in/Redmi-10-Prime-extendable-Adaptive",
                "is_liquid": false
*/

        JsonObject object = new JsonObject();

        object.addProperty("color", color);
        object.addProperty("if_item_unavailable", selectedString);
        object.addProperty("name", name);
        object.addProperty("orderId", 1);
        object.addProperty("price_amount", price);
        object.addProperty("quantity", countString);
        object.addProperty("shopperType", "ps");
        object.addProperty("size", size);
        object.addProperty("url", url);
        object.addProperty("is_liquid", isLiquid);

        Call<AddOrderResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().addOrder("Bearer " + sharedPrefManager.getBearerToken()
                        , object.toString());
        call.enqueue(new Callback<AddOrderResponse>() {
            @Override
            public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                if (response.code() == 200) {
                    sharedPrefManager.storeOrderCode(response.body().getOrders().get(0).getOrderCode());
                    LoadingDialog.cancelLoading();
                    list = response.body().getOrders();
                    CartGroupAdapter cartGroupAdapter = new CartGroupAdapter(list, getContext());
                    cartRecycler.setAdapter(cartGroupAdapter);
                    cartGroupAdapter.notifyDataSetChanged();

                    productCartCard.setVisibility(View.VISIBLE);
                    upwardTriangle.setVisibility(View.VISIBLE);
                    downwardTriangle.setVisibility(View.GONE);

                    total.setText(String.valueOf("₹ "+response.body().getOrders().get(0).getSubTotal()));
                    shoppreFee.setText("₹ "+String.valueOf(response.body().getOrders().get(0).getPersonalShopperCost()));
                    orderTotal.setText("₹ "+String.valueOf(response.body().getOrders().get(0).getPriceAmount()));
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {

                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<AddOrderResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),
                        t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
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
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callAddOrderApi();
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

    @SuppressLint("ResourceAsColor")
    private void getTextFromFields() {
        url = urlField.getText().toString();
        name = nameField.getText().toString();
        size = sizeField.getText().toString();
        color = colorField.getText().toString();
        price = priceField.getText().toString();
        countString = countField.getText().toString();
        selectedString = selectField.getText().toString();
        if (check.isChecked()) {
            isLiquid = true;
        } else isLiquid = false;

    }

    @SuppressLint("ResourceAsColor")
    private Boolean validateName() {


        if (name.isEmpty()) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Enter Product's Name", Snackbar.LENGTH_LONG);

            snackbar.show();
            return false;
        } else {

            return true;
        }

    }

    @SuppressLint("ResourceAsColor")
    private Boolean validateUrl() {


        if (url.isEmpty()) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Enter Product's Url", Snackbar.LENGTH_LONG);

            snackbar.show();
            return false;
        } else {

            return true;
        }

    }

    @SuppressLint("ResourceAsColor")
    private Boolean validatePrice() {


        if (price.isEmpty()) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Enter Product's price", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        } else {

            return true;
        }

    }
}