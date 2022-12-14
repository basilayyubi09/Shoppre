package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.OrderSummaryAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    List<Order> list;
    OrderSummaryAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recycle;
    MaterialButton orderSummaryProceedBtn;
    Integer id;
    String orderCode;
    String[] ac, ins, pc;
    int flag = 0;
    Integer shoppreId;
    NestedScrollView main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        recycle = view.findViewById(R.id.recycle);
        main = view.findViewById(R.id.main);
        orderSummaryProceedBtn = view.findViewById(R.id.orderSummaryProceedBtn);

        setupUI(main);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            shoppreId = bundle.getInt("id");
            list = (List<Order>) bundle.getSerializable("list");
        }
        linearLayoutManager = new LinearLayoutManager(getActivity());


        recycle.setLayoutManager(linearLayoutManager);
        adapter = new OrderSummaryAdapter(list, getActivity());
        recycle.setAdapter(adapter);

        ins = new String[list.size()];
        pc = new String[list.size()];
        ac = new String[list.size()];
        orderSummaryProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonArray jsonArray = new JsonArray();
                for (int i = 0; i < list.size(); i++) {
                    View view1 = recycle.getChildAt(i);

                    Spinner spinner = view1.findViewById(R.id.dropdown);
                    EditText charges = view1.findViewById(R.id.charges);
                    EditText instructionField = view1.findViewById(R.id.instruction);

                    id = list.get(i).getId();
                    orderCode = list.get(i).getOrderCode();
                    ac[i] = charges.getText().toString();
                    ins[i] = instructionField.getText().toString();


                    pc[i] = spinner.getSelectedItem().toString();
                    if (pc[i].equals("Select an option")) {

                        pc[i] = "";
                    }
                    if (ac[i].equals("") || ins[i].equals("") || pc[i].equals("")) {
                        flag = 0;
                    } else {
                        JsonObject jsonObject = new JsonObject();

                        flag = 1;
                        jsonObject.addProperty("id", id);
                        jsonObject.addProperty("order_code", orderCode);
                        jsonObject.addProperty("additional_charges", Integer.parseInt(ac[i]));
                        jsonObject.addProperty("instruction", ins[i]);
                        jsonObject.addProperty("buy_if_price_changed", pc[i]);
                        jsonArray.add(jsonObject);
                    }

                }


                if (flag == 0) {
                    Toast.makeText(getActivity(), "Please fill all the details to continue", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callSubmitOrderApi(jsonArray, list);
                }

            }
        });

        return view;
    }

    private void callSubmitOrderApi(JsonArray jsonArray, List<Order> list) {


        Call<ResponseBody> call = RetrofitClient3
                .getInstance3()
                .getAppApi().submitOrder("Bearer " + sharedPrefManager.getBearerToken(), jsonArray.toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", shoppreId);

                    bundle.putSerializable("list", (Serializable) list);
                    FinalOrderSummaryFragment finalOrder = new FinalOrderSummaryFragment();
                    finalOrder.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, finalOrder, null)
                            .addToBackStack(null).commit();

                } else if (response.code() == 401) {
                    callRefreshTokenAPi(jsonArray, list);
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callRefreshTokenAPi(JsonArray jsonArray, List<Order> list) {

        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callSubmitOrderApi(jsonArray, list);

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