package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.AccountResponse.VerifyEmailResponse;
import com.peceinfotech.shoppre.Adapters.OrdersAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderListingResponse;
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

public class OrderFragment extends Fragment {


    MaterialButton addYourFirstOrderBtn , verifyEmailBtn;
    SharedPrefManager sharedPrefManager;
    RecyclerView orderRecycler;
    CardView verifyEmailBox;
    CardView banner , ordersCard;
    List<Order> list;
    LinearLayout orderListing;
    OrdersAdapter ordersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        addYourFirstOrderBtn = view.findViewById(R.id.addYourFirstOrderBtn);
        verifyEmailBox = view.findViewById(R.id.verifyEmailBox);
        verifyEmailBtn = view.findViewById(R.id.verifyEmailBtn);
        banner = view.findViewById(R.id.banner);
        ordersCard = view.findViewById(R.id.ordersCard);
        orderListing = view.findViewById(R.id.orderListing);
        orderRecycler = view.findViewById(R.id.orderRecyclerView);
        sharedPrefManager = new SharedPrefManager(getActivity());
        list = new ArrayList<>();






        LoadingDialog.showLoadingDialog(getActivity(),"");
        callMeApi(sharedPrefManager.getBearerToken());

        LoadingDialog.showLoadingDialog(getActivity(),"");
        callGetOrderListing();

        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LandingDialog alert = new LandingDialog();
//                alert.showDialog(getActivity());
                LoadingDialog.showLoadingDialog(getActivity() , "");
                callVerifyEmailId();
            }
        });
        addYourFirstOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new OrderListing(), null)
                        .addToBackStack(null).commit();

            }
        });



//        list.add(new OrderResponse("Myntra", "#RNDM043", "12 Dec 2020", R.drawable.ic_self_shopper));
//        list.add(new OrderResponse("Amazon.in", "#PSDM043", "15 Dec 2020", R.drawable.ic_personal_shopper));
//        list.add(new OrderResponse("Nyka", "#RNDM032", "16 Dec 2020", R.drawable.ic_self_shopper));
//        list.add(new OrderResponse("Flipkart", "#PSDM054", "18 Dec 2020", R.drawable.ic_personal_shopper));
//        list.add(new OrderResponse("Fabindia" , "#PSDM054" , "20 Dec 2020" , R.drawable.ic_self_shopper));


         ordersAdapter = new OrdersAdapter(list , getContext());
        orderRecycler.setAdapter(ordersAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderRecycler.setLayoutManager(linearLayoutManager);

        int number = orderRecycler.getAdapter().getItemCount();
        if (number==0){
            banner.setVisibility(View.VISIBLE);
            ordersCard.setVisibility(View.VISIBLE);
            orderListing.setVisibility(View.GONE);
        }
        else
        {
            banner.setVisibility(View.GONE);
            ordersCard.setVisibility(View.GONE);
            orderListing.setVisibility(View.VISIBLE);
        }
        ordersAdapter.notifyDataSetChanged();

        return  view;
    }

    private void callGetOrderListing() {
        Call<OrderListingResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().getOrderListing("Bearer "+sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<OrderListingResponse>() {
            @Override
            public void onResponse(Call<OrderListingResponse> call, Response<OrderListingResponse> response) {

                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    list =  response.body().getOrders();
                    ordersAdapter = new OrdersAdapter(list , getContext());
                    orderRecycler.setAdapter(ordersAdapter);
                    int number = orderRecycler.getAdapter().getItemCount();
                    if (number==0){
                        banner.setVisibility(View.VISIBLE);
                        ordersCard.setVisibility(View.VISIBLE);
                        orderListing.setVisibility(View.GONE);
                    }
                    else
                    {
                        banner.setVisibility(View.GONE);
                        ordersCard.setVisibility(View.GONE);
                        orderListing.setVisibility(View.VISIBLE);
                    }
                    ordersAdapter.notifyDataSetChanged();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), "F", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderListingResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMeApi(String bearerToken) {
        Call<MeResponse> call = RetrofitClient
                .getInstance().getApi()
                .getUser("Bearer "+bearerToken);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    if (response.body().getIsEmailVerified()==0){

                        verifyEmailBox.setVisibility(View.VISIBLE);
                    }
                    else if (response.body().getIsEmailVerified()==1){

                        verifyEmailBox.setVisibility(View.GONE);
                    }
                }
                else if (response.code()==401){
                    callRefreshTokenApi();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
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
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                }
                else {
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
    private void callVerifyEmailId() {
        Call<VerifyEmailResponse> call = RetrofitClient.getInstance()
                .getApi().getVerify("Bearer "+sharedPrefManager.getBearerToken(), sharedPrefManager.getId());
        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Verification link has sent to your email.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(response.code()==403){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getError(), Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}