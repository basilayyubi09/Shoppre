package com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.OrderAdapter.OrderUpdateAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.AddCommentResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.GetCommentsResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderUpdateResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.EngageRetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderUpdates extends Fragment {

    RecyclerView orderUpdatesRecycler;
    EditText text;
    ImageView sendBtn;
    String textString;
    List<GetCommentsResponse> list = new ArrayList<>();
    Bundle bundle;
    SharedPrefManager sharedPrefManager;
    String orderCode;
    OrderUpdateAdapter orderUpdateAdapter;
    String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_updates, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        orderUpdatesRecycler = view.findViewById(R.id.orderUpdatesRecycler);
        text = view.findViewById(R.id.text);
        sendBtn = view.findViewById(R.id.sendBtn);

        textString = text.getText().toString();
        bundle = this.getArguments();


        if (bundle != null) {
            orderCode = bundle.getString("orderCode");
            id = bundle.getString("id");
        }

        LoadingDialog.showLoadingDialog(getActivity(), "");
        callSendChatApi();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateBtn()) {
                    return;
                } else {
                    LoadingDialog.showLoadingDialog(getActivity() , "");
                    callAddComment();
                }
            }
        });
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.shoppre_ic, "Shoppre Team", "Order Arrived at Facility", " 19 Dec 2020, 4:05 PM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "Where is my order?", "22 Dec 2020, 09:45 AM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.shoppre_ic, "Shoppre Team", "The Order is at our warehouse ready to be shipped", "24 Dec 2020, 3:26 IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));





        return view;
    }

    private void callSendChatApi() {
        Call<List<GetCommentsResponse>> call= EngageRetrofitClient
                .getInstance3().getAppApi().getComments(id);

        call.enqueue(new Callback<List<GetCommentsResponse>>() {
            @Override
            public void onResponse(Call<List<GetCommentsResponse>> call, Response<List<GetCommentsResponse>> response) {
                if (response.code()==200){

                     list = response.body();
                    orderUpdateAdapter = new OrderUpdateAdapter(list, getContext());
                    orderUpdatesRecycler.setAdapter(orderUpdateAdapter);
                    LoadingDialog.cancelLoading();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetCommentsResponse>> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callAddComment() {
        JsonObject object = new JsonObject();
        object.addProperty("comments" , textString);

        Call<AddCommentResponse> call = EngageRetrofitClient
                .getInstance3().getAppApi().addComment("Bearer "+sharedPrefManager.getBearerToken()
              ,id  ,object.toString());
        call.enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                if (response.code()==201){
                    text.setText("");
                    callSendChatApi();

                }
                else if (response.code()==401){
                    callRefreshTokenApi();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateBtn() {
        textString = text.getText().toString();
        if (textString.equals("")) {
            return false;
        } else {
            return true;
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
                    callAddComment();
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