package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.CartGroupAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.AddOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.CartModelResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;
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
    MaterialCardView liquidCard;
    List<Order> list = new ArrayList<>();
    LinearLayout dropdownLayout, addMore;
    TextView selectField, orderTotal, shoppreFee, total;
    String url, name, color, size, price, countString, selectedString;
    EditText urlField, nameField, colorField, priceField, sizeField;
    TextView minus, plus, countField;
    MaterialCheckBox check;
    int count = 1, id;
    boolean isLiquid = false;

    ImageView cartImage;
    FrameLayout badgeCartImage;
    TextView badgeTextView, selectAnOptionTextView;

    Spinner selectAnOptionSpinner;

    String[] selectAnOptionSpinnerItems = {"Select an option", "Cancel this item & purchase all the other available items            ", "Cancel all the items from this site"};

    int badgeNumber = 0;


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
        addMore = view.findViewById(R.id.addMore);
        liquidCard = view.findViewById(R.id.liquidCard);
        downwardTriangle = view.findViewById(R.id.downwardTriangle);
        upwardTriangle = view.findViewById(R.id.upwardTriangle);
        proceedToCartBtn = view.findViewById(R.id.proceedToCartBtn);
        dropdownLayout = view.findViewById(R.id.dropdownLayut);
        selectAnOptionSpinner = view.findViewById(R.id.selectAnOptionSpinner);
        urlField = view.findViewById(R.id.urlField);
        nameField = view.findViewById(R.id.nameField);
        check = view.findViewById(R.id.check);
        plus = view.findViewById(R.id.plus);
        minus = view.findViewById(R.id.minus);
        countField = view.findViewById(R.id.countField);
        colorField = view.findViewById(R.id.colorField);
        priceField = view.findViewById(R.id.priceField);
        sizeField = view.findViewById(R.id.sizeField);


        cartImage = view.findViewById(R.id.cartImage);
        badgeCartImage = view.findViewById(R.id.badgeCartImage);
        badgeTextView = view.findViewById(R.id.badgeTextView);
        selectAnOptionTextView = view.findViewById(R.id.selectAnOptionTextView);




        final List<String> selectAnOptionList = new ArrayList<>(Arrays.asList(selectAnOptionSpinnerItems));

        final ArrayAdapter<String> selectAnOptionAdapter = new ArrayAdapter<String>(getContext(), R.layout.select_an_option_spinner_text, selectAnOptionList){

            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                }else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView selectAnOptionTextView = (TextView) view;
                if (position == 0){
                    selectAnOptionTextView.setVisibility(View.GONE);
                    selectAnOptionTextView.setTextColor(Color.GRAY);
                }else {
                    selectAnOptionTextView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        selectAnOptionAdapter.setDropDownViewResource(R.layout.select_an_option_spinner_text);
        selectAnOptionSpinner.setAdapter(selectAnOptionAdapter);

        selectAnOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
//                    Toast.makeText(getContext(), String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropdownLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnOptionSpinner.performClick();
            }
        });



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String type = bundle.getString("type");
            if (type.equals("new")) {
                id = bundle.getInt("id");
                Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            } else {
                id = bundle.getInt("id");
                Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                LoadingDialog.showLoadingDialog(getActivity(), "");
                callCartApi(id);
            }

        }

        getTextFromFields();
        check.setOnClickListener(v -> {
            if (check.isChecked()) {
                liquidCard.setVisibility(View.VISIBLE);
            } else {
                liquidCard.setVisibility(View.GONE);
            }
        });

        count = Integer.parseInt(String.valueOf(countString));

//        dropdownLayout.setOnClickListener(v -> {
//            PopupMenu popup = new PopupMenu(getActivity(), dropdownLayout);
//            popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @SuppressLint("ResourceAsColor")
//                public boolean onMenuItemClick(MenuItem item) {
//                    selectField.setText(item.getTitle());
//                    return true;
//                }
//            });
//
//            popup.show();
//        });

        plus.setOnClickListener(v -> {

            count++;
            countField.setText(String.valueOf(count));
        });

        minus.setOnClickListener(v -> {

            if (count == 1) {
                countField.setText("1");
            } else {
                count -= 1;
                countField.setText("" + count);
            }
        });


        downwardTriangle.setVisibility(View.VISIBLE);

        itemCartCard.setOnClickListener(v -> {

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
        });

        addMore.setOnClickListener(new View.OnClickListener() {
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

    private void callCartApi(int id) {
        Call<CartModelResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().getCartItem("Bearer " + sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<CartModelResponse>() {
            @Override
            public void onResponse(Call<CartModelResponse> call, Response<CartModelResponse> response) {
                if (response.code() == 200) {
                    list = response.body().getOrders();
                    if (!list.isEmpty()){

                        cartImage.setVisibility(View.GONE);
                        badgeCartImage.setVisibility(View.VISIBLE);

                        proceedToCartBtn.setEnabled(true);
                        proceedToCartBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_blue)));
                    }
                    CartGroupAdapter cartGroupAdapter = new CartGroupAdapter(list, getContext());
                    cartRecycler.setAdapter(cartGroupAdapter);
                    cartGroupAdapter.notifyDataSetChanged();
                    productCartCard.setVisibility(View.VISIBLE);
                    upwardTriangle.setVisibility(View.VISIBLE);
                    downwardTriangle.setVisibility(View.GONE);

                    int totalCount = 0, shoppreTotal = 0, orderTotalCount = 0 , totalBadgeQuantity = 0;
                    for (int i = 0; i < list.size(); i++) {

                        totalBadgeQuantity = totalBadgeQuantity+response.body().getOrders().get(i).getTotalQuantity();
                        totalCount = totalCount + response.body().getOrders().get(i).getSubTotal();
                        shoppreTotal = shoppreTotal + response.body().getOrders().get(i).getPersonalShopperCost();
                        orderTotalCount = orderTotalCount + response.body().getOrders().get(i).getPriceAmount();
                    }
                    badgeTextView.setText(String.valueOf(list.size()));
                    total.setText(String.valueOf("₹ " + totalCount));
                    shoppreFee.setText("₹ " + String.valueOf(shoppreTotal));
                    orderTotal.setText("₹ " + String.valueOf(orderTotalCount));
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
        object.addProperty("orderId", id);
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

//                    sharedPrefManager.storeOrderCode(response.body().getOrders().get(0).getOrderCode());
//                    sharedPrefManager.storeOrderId(response.body().getOrders().get(0).getId());
//

                    list = response.body().getOrders();
                    if (!list.isEmpty()){
                        proceedToCartBtn.setEnabled(true);
                        proceedToCartBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_blue)));
                    }



                    CartGroupAdapter cartGroupAdapter = new CartGroupAdapter(list, getContext());
                    cartRecycler.setAdapter(cartGroupAdapter);
                    cartGroupAdapter.notifyDataSetChanged();

                    productCartCard.setVisibility(View.VISIBLE);
                    upwardTriangle.setVisibility(View.VISIBLE);
                    downwardTriangle.setVisibility(View.GONE);

                    int totalCount = 0, shoppreTotal = 0, orderTotalCount = 0;
                    for (int i = 0; i < list.size(); i++) {


                        totalCount = totalCount + response.body().getOrders().get(i).getSubTotal();
                        shoppreTotal = shoppreTotal + response.body().getOrders().get(i).getPersonalShopperCost();
                        orderTotalCount = orderTotalCount + response.body().getOrders().get(i).getPriceAmount();
                    }
                    total.setText(String.valueOf("₹ " + totalCount));
                    shoppreFee.setText("₹ " + String.valueOf(shoppreTotal));
                    orderTotal.setText("₹ " + String.valueOf(orderTotalCount));
                    clearFields();
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {

                    callRefreshTokenApi("add");
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

    private void clearFields() {
         urlField.setText("");
        nameField.setText("");
        sizeField.setText("");
        colorField.setText("");
        priceField.setText("");
        countField.setText("");
        selectField.setText("");
        check.setChecked(false);
    }

    private void callRefreshTokenApi(String code) {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    if (code.equals("add")) {
                        callAddOrderApi();
                    } else if (code.equals("cart")) {
                        callCartApi(id);
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

    @SuppressLint("ResourceAsColor")
    private void getTextFromFields() {
        url = urlField.getText().toString();
        name = nameField.getText().toString();
        size = sizeField.getText().toString();
        color = colorField.getText().toString();
        price = priceField.getText().toString();
        countString = countField.getText().toString();
        selectedString = selectAnOptionSpinner.getSelectedItem().toString();
        if (check.isChecked()) {
            isLiquid = true;

        } else {
            isLiquid = false;

        }

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