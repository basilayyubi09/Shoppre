package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.CartGroupAdapter;
import com.shoppreglobal.shoppre.LockerModelResponse.VerifyLinkResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.AddOrderResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.CartModelResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.DeleteOrderResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderItem;
import com.shoppreglobal.shoppre.OrderModuleResponses.UpdateOrderResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmptyCart extends Fragment {

    SharedPrefManager sharedPrefManager;
    RecyclerView cartRecycler, productItemRecycler;
    CardView itemCartCard, productCartCard;
    ImageView downwardTriangle, upwardTriangle;
    MaterialButton proceedToCartBtn;
    int flag = 1;
    MaterialCardView liquidCard, ePharmacy, dontPlace;
    List<Order> list = new ArrayList<>();
    LinearLayout dropdownLayout, addMore;
    TextView orderTotal, shoppreFee, total;
    String url, name, color, size, price, countString, selectedString;
    EditText urlField, nameField, colorField, priceField, sizeField;
    TextView minus, plus, countField;
    MaterialCheckBox check;
    int count = 1, id;
    boolean isUpdate = false;
    boolean isLiquid = false;
    boolean goNext = false;
    TextView productCount, addMoreText;
    ImageView cartImage;
    CartGroupAdapter cartGroupAdapter;
    FrameLayout badgeCartImage;
    Integer productCountInt = 0;
    OrderItem orderFromAdapter;
    TextView badgeTextView, selectAnOptionTextView;
    MaterialCardView fifteen;
    FrameLayout main;
    Spinner selectAnOptionSpinner;
    Integer orderId, deleteOrderId, deleteItemId;

    String[] selectAnOptionSpinnerItems = {"Select an option", "Cancel this item & purchase all the other         \navailable items", "Cancel all the items from this site"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_cart, container, false);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        cartRecycler = view.findViewById(R.id.cartRecycler);
        itemCartCard = view.findViewById(R.id.itemInCartCard);
        productCartCard = view.findViewById(R.id.productCard);
        orderTotal = view.findViewById(R.id.orderTotal);
        main = view.findViewById(R.id.main);
        ePharmacy = view.findViewById(R.id.ePharmacy);
        dontPlace = view.findViewById(R.id.dontPlace);
        addMoreText = view.findViewById(R.id.addMoreText);
        shoppreFee = view.findViewById(R.id.shoppreFee);
        total = view.findViewById(R.id.total);
        productItemRecycler = view.findViewById(R.id.productItemRecycler);
        addMore = view.findViewById(R.id.addMore);
        fifteen = view.findViewById(R.id.fifteen);

        productCount = view.findViewById(R.id.productCount);
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

        setupUI(main);
        final List<String> selectAnOptionList = new ArrayList<>(Arrays.asList(selectAnOptionSpinnerItems));

        final ArrayAdapter<String> selectAnOptionAdapter = new ArrayAdapter<String>(getContext(), R.layout.select_an_option_spinner_text, selectAnOptionList) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView selectAnOptionTextView = (TextView) view;
                if (position == 0) {
                    selectAnOptionTextView.setVisibility(View.GONE);
                    selectAnOptionTextView.setTextColor(Color.GRAY);
                } else {
                    selectAnOptionTextView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        urlField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    callVerifyLinkApi(s);
                }

            }
        });
        selectAnOptionAdapter.setDropDownViewResource(R.layout.select_an_option_spinner_text);
        selectAnOptionSpinner.setAdapter(selectAnOptionAdapter);

        selectAnOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
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

            } else {
                id = bundle.getInt("id");
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
                if (!validateUrl() || !validateName() || !validatePrice() || !validateString()) {
                    return;
                } else {

                    if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
//                        OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderSummaryFragment(), null)
//                                .addToBackStack(null).commit();

                        if (isUpdate) {
                            if (goNext) {
                                callUpdateOrder();
                            } else {

                            }


                        } else {

                            if (goNext) {

                                LoadingDialog.showLoadingDialog(getActivity(), "");
                                callAddOrderApi();
                            } else {
                            }
                        }
                    }
                }


            }
        });

        proceedToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    bundle.putInt("id", id);
                    OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
                    orderSummaryFragment.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, orderSummaryFragment, null)
                            .addToBackStack(null).commit();

                }
            }
        });


        return view;
    }

    private void callVerifyLinkApi(Editable s) {
        String abcd = "=" + urlField.getText().toString();

        Call<VerifyLinkResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().verifyLink(abcd);
        call.enqueue(new Callback<VerifyLinkResponse>() {
            @Override
            public void onResponse(Call<VerifyLinkResponse> call, Response<VerifyLinkResponse> response) {
                if (response.code() == 200) {

                    if (response.body().getStore() == null) {
                        dontPlace.setVisibility(View.VISIBLE);
                        goNext = true;

                    } else if (response.body().getStore().getIsEPharmacy() != null) {
                        Toast.makeText(getActivity(), " pharmacy", Toast.LENGTH_SHORT).show();
                        dontPlace.setVisibility(View.GONE);
                        ePharmacy.setVisibility(View.VISIBLE);
                        goNext = true;
                    } else {
                        goNext = true;
                        dontPlace.setVisibility(View.GONE);
                        ePharmacy.setVisibility(View.GONE);
                    }


                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyLinkResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

                    if (!list.isEmpty()) {
                        cartGroupAdapter = new CartGroupAdapter(list, getContext(), new CartGroupAdapter.SecondInterface() {
                            @Override
                            public void second(OrderItem order, Integer id) {
                                LoadingDialog.cancelLoading();
                                orderFromAdapter = order;
                                orderId = id;
                                setdata();

                            }

                            @Override
                            public void delete(Integer orderId, Integer itemId) {
                                deleteOrderId = orderId;
                                deleteItemId = itemId;
                                LoadingDialog.showLoadingDialog(getActivity(), "");
                                callDeleteApi();
                            }
                        });
                        dontPlace.setVisibility(View.GONE);
                        ePharmacy.setVisibility(View.GONE);
                        cartRecycler.setAdapter(cartGroupAdapter);
                        cartGroupAdapter.notifyDataSetChanged();
                        productCartCard.setVisibility(View.VISIBLE);
                        upwardTriangle.setVisibility(View.VISIBLE);
                        downwardTriangle.setVisibility(View.GONE);
                        cartImage.setVisibility(View.GONE);
                        badgeCartImage.setVisibility(View.VISIBLE);

                        proceedToCartBtn.setEnabled(true);
                        proceedToCartBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.button_blue)));
                        int totalCount = 0, shoppreTotal = 0, orderTotalCount = 0, totalBadgeQuantity = 0;
                        for (int i = 0; i < list.size(); i++) {

                            totalBadgeQuantity = totalBadgeQuantity + list.get(i).getOrderItems().size();
                            totalCount = totalCount + list.get(i).getSubTotal();
                            shoppreTotal = shoppreTotal + list.get(i).getPersonalShopperCost();
                            orderTotalCount = orderTotalCount + list.get(i).getPriceAmount();
                            productCountInt = list.get(i).getOrderItems().size();
                            productCount.setText(String.valueOf(totalBadgeQuantity + 1));

                        }
                        if (productCountInt >= 14) {
                            fifteen.setVisibility(View.VISIBLE);
                        } else {
                            fifteen.setVisibility(View.GONE);
                        }
                        badgeTextView.setText(String.valueOf(totalBadgeQuantity));
                        total.setText(String.valueOf("₹ " + totalCount));
                        shoppreFee.setText("₹ " + String.valueOf(shoppreTotal));
                        orderTotal.setText("₹ " + String.valueOf(orderTotalCount));
                        clearFields();
                        View targetView = itemCartCard;
                        targetView.getParent().requestChildFocus(targetView, targetView);

                    } else {
                        cartGroupAdapter = new CartGroupAdapter(list, getActivity(), new CartGroupAdapter.SecondInterface() {
                            @Override
                            public void second(OrderItem order, Integer id) {

                            }

                            @Override
                            public void delete(Integer orderId, Integer itemId) {

                            }
                        });
                        cartRecycler.setAdapter(cartGroupAdapter);
                        productCountInt = 1;
                        productCount.setText(String.valueOf(1));
                        badgeTextView.setText(String.valueOf(0));
                        total.setText(String.valueOf("₹ " + 00));
                        shoppreFee.setText("₹ " + String.valueOf(00));
                        orderTotal.setText("₹ " + String.valueOf(00));
                        productCartCard.setVisibility(View.GONE);
                        upwardTriangle.setVisibility(View.GONE);
                        downwardTriangle.setVisibility(View.VISIBLE);
                        cartImage.setVisibility(View.VISIBLE);
                        badgeCartImage.setVisibility(View.GONE);
                        proceedToCartBtn.setEnabled(false);
                        proceedToCartBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.grey)));
                    }
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

        isUpdate = false;
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
//                    id = list.get(0).getId();
                    if (!list.isEmpty()) {
                        proceedToCartBtn.setEnabled(true);
                        proceedToCartBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_blue)));
                        CartGroupAdapter cartGroupAdapter = new CartGroupAdapter(list, getContext(), new CartGroupAdapter.SecondInterface() {
                            @Override
                            public void second(OrderItem order, Integer id) {
                                orderFromAdapter = order;
                                orderId = id;

                                setdata();
                            }

                            @Override
                            public void delete(Integer orderId, Integer itemId) {
                                deleteOrderId = orderId;
                                deleteItemId = itemId;
                                LoadingDialog.showLoadingDialog(getActivity(), "");
                                callDeleteApi();
                            }
                        });
                        dontPlace.setVisibility(View.GONE);
                        ePharmacy.setVisibility(View.GONE);
                        cartRecycler.setAdapter(cartGroupAdapter);
                        cartGroupAdapter.notifyDataSetChanged();

                        productCartCard.setVisibility(View.VISIBLE);
                        upwardTriangle.setVisibility(View.VISIBLE);
                        downwardTriangle.setVisibility(View.GONE);
                        cartImage.setVisibility(View.GONE);
                        badgeCartImage.setVisibility(View.VISIBLE);

                        int totalCount = 0, shoppreTotal = 0, orderTotalCount = 0;
                        Integer totalBadgeQuantity = 0;
                        for (int i = 0; i < list.size(); i++) {

                            totalBadgeQuantity = totalBadgeQuantity + list.get(i).getOrderItems().size();
                            totalCount = totalCount + list.get(i).getSubTotal();
                            shoppreTotal = shoppreTotal + list.get(i).getPersonalShopperCost();
                            orderTotalCount = orderTotalCount + list.get(i).getPriceAmount();
                            productCountInt = list.get(i).getOrderItems().size();
                            productCount.setText(String.valueOf(totalBadgeQuantity + 1));

                        }
                        if (productCountInt > 14) {
                            fifteen.setVisibility(View.VISIBLE);
                        } else {
                            fifteen.setVisibility(View.GONE);
                        }
                        badgeTextView.setText(String.valueOf(totalBadgeQuantity));
                        total.setText(String.valueOf("₹ " + totalCount));
                        shoppreFee.setText("₹ " + String.valueOf(shoppreTotal));
                        orderTotal.setText("₹ " + String.valueOf(orderTotalCount));
                        clearFields();
                    } else {
                        cartGroupAdapter = new CartGroupAdapter(list, getActivity(), new CartGroupAdapter.SecondInterface() {
                            @Override
                            public void second(OrderItem order, Integer id) {

                            }

                            @Override
                            public void delete(Integer orderId, Integer itemId) {

                            }
                        });
                        cartRecycler.setAdapter(cartGroupAdapter);
                        productCountInt = 1;
                        productCount.setText(String.valueOf(1));
                        badgeTextView.setText(String.valueOf(0));
                        total.setText(String.valueOf("₹ " + 00));
                        shoppreFee.setText("₹ " + String.valueOf(00));
                        orderTotal.setText("₹ " + String.valueOf(00));
                        productCartCard.setVisibility(View.GONE);
                        upwardTriangle.setVisibility(View.GONE);
                        downwardTriangle.setVisibility(View.VISIBLE);
                        cartImage.setVisibility(View.VISIBLE);
                        badgeCartImage.setVisibility(View.GONE);
                        proceedToCartBtn.setEnabled(false);
                        proceedToCartBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.grey)));
                    }


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

    private void setdata() {
        View targetView = priceField;
        targetView.getParent().requestChildFocus(targetView, targetView);
        addMoreText.setText("Update Product");
        isUpdate = true;
        urlField.setText(orderFromAdapter.getUrl());
        nameField.setText(orderFromAdapter.getName());
        sizeField.setText(orderFromAdapter.getSize());
        colorField.setText(orderFromAdapter.getColor());
        priceField.setText(String.valueOf(orderFromAdapter.getPriceAmount()));
        countField.setText(String.valueOf(orderFromAdapter.getQuantity()));
        check.setChecked(false);

    }

    private void callDeleteApi() {

//        Toast.makeText(context.getApplicationContext(), String.valueOf(itemId ), Toast.LENGTH_SHORT).show();
        Call<List<DeleteOrderResponse>> call = RetrofitClient3
                .getInstance3()
                .getAppApi().deleteOrder("Bearer " + sharedPrefManager.getBearerToken(),
                        deleteOrderId, deleteItemId);
        call.enqueue(new Callback<List<DeleteOrderResponse>>() {
            @Override
            public void onResponse(Call<List<DeleteOrderResponse>> call, Response<List<DeleteOrderResponse>> response) {
                if (response.code() == 200) {


                    callCartApi(id);
                } else if (response.code() == 401) {
                    callRefreshTokenApi("delete");
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DeleteOrderResponse>> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callUpdateOrder() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        getTextFromFields();
//        {
//            "color": "Blue",
//                "id": 502,
//                "if_item_unavailable": "Cancel this item, purchase all other available items",
//                "is_liquid": null,
//                "name": "men dress",
//                "order_id": 366,
//                "price_amount": 300,
//                "quantity": 1,
//                "size": "s",
//                "url": "https://www.amazo"
//        }
        JsonObject object = new JsonObject();

        object.addProperty("color", color);
        object.addProperty("id", orderFromAdapter.getId());
        object.addProperty("if_item_unavailable", selectedString);
        object.addProperty("is_liquid", isLiquid);
        object.addProperty("name", name);
        object.addProperty("order_id", orderId);
        object.addProperty("price_amount", price);
        object.addProperty("quantity", countString);
        object.addProperty("size", size);
        object.addProperty("url", url);
        Call<UpdateOrderResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().updateOrder("Bearer " + sharedPrefManager.getBearerToken(), String.valueOf(orderId), object.toString());
        call.enqueue(new Callback<UpdateOrderResponse>() {
            @Override
            public void onResponse(Call<UpdateOrderResponse> call, Response<UpdateOrderResponse> response) {
                if (response.code() == 200) {
                    isUpdate = false;
                    addMoreText.setText("Add More Product");
                    callCartApi(id);
                } else if (response.code() == 401) {
                    callRefreshTokenApi("update");
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateOrderResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void clearFields() {
        urlField.setText("");
        nameField.setText("");
        sizeField.setText("");
        colorField.setText("");
        priceField.setText("");
        countField.setText("1");
        check.setChecked(false);
        final List<String> selectAnOptionList = new ArrayList<>(Arrays.asList(selectAnOptionSpinnerItems));

        final ArrayAdapter<String> selectAnOptionAdapter = new ArrayAdapter<String>(getContext(), R.layout.select_an_option_spinner_text, selectAnOptionList) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView selectAnOptionTextView = (TextView) view;
                if (position == 0) {
                    selectAnOptionTextView.setVisibility(View.GONE);
                    selectAnOptionTextView.setTextColor(Color.GRAY);
                } else {
                    selectAnOptionTextView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        selectAnOptionAdapter.setDropDownViewResource(R.layout.select_an_option_spinner_text);
        selectAnOptionSpinner.setAdapter(selectAnOptionAdapter);
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
                    } else if (code.equals("update")) {
                        callUpdateOrder();
                    } else if (code.equals("delete")) {
                        callDeleteApi();
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

    private boolean validateString() {
        if (selectedString.equals("Select an option")) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Select one Option", Snackbar.LENGTH_LONG);

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

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}