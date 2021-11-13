package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.OrderAdapter.ParentFinalOrderSummaryAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.CartModelResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinalOrderSummaryFragment extends Fragment {


    SharedPrefManager sharedPrefManager;
    List<Order> list = new ArrayList<>();
    RecyclerView recyclerView;
    TextView orderTotal , shoppreFee , total;
    ParentFinalOrderSummaryAdapter adapter ;
    MaterialButton proceedToPayBtn;
    Integer shoppreId;
    int totalCount = 0 , subTotal = 0 , shoppreTotal=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_order_summary, container, false);

        orderTotal = view.findViewById(R.id.orderTotal);
        total = view.findViewById(R.id.total);
        recyclerView = view.findViewById(R.id.recycleFinal);
        proceedToPayBtn = view.findViewById(R.id.proceedToPayBtn);
        shoppreFee = view.findViewById(R.id.shoppreFee);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            shoppreId = bundle.getInt("id");
//            list = (List<Order>) bundle.getSerializable("list");



        }
        LoadingDialog.showLoadingDialog(getActivity() , "");
        callCartApi(shoppreId);








        proceedToPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ThankYouFragment())
                        .addToBackStack(null).commit();

            }
        });


        return view;
    }
    private void callCartApi(Integer shoppreId) {
        Call<CartModelResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().getCartItem("Bearer " + sharedPrefManager.getBearerToken(), shoppreId);
        call.enqueue(new Callback<CartModelResponse>() {
            @Override
            public void onResponse(Call<CartModelResponse> call, Response<CartModelResponse> response) {
                if (response.code() == 200) {
                    list = response.body().getOrders();
                    for (int i=0 ; i<list.size() ; i++){
                        shoppreTotal = shoppreTotal+ list.get(i).getPersonalShopperCost();
                        totalCount = totalCount+ list.get(i).getSubTotal();
                        subTotal = subTotal+ list.get(i).getPriceAmount();
                    }
                    total.setText(String.valueOf(totalCount));
                    shoppreFee.setText(String.valueOf(shoppreTotal));
                    orderTotal.setText(String.valueOf(subTotal));
                    adapter = new ParentFinalOrderSummaryAdapter(list , getActivity() );
                    recyclerView.setAdapter(adapter);
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi(shoppreId);
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
    private void callRefreshTokenApi(Integer sId) {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callCartApi(sId);

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