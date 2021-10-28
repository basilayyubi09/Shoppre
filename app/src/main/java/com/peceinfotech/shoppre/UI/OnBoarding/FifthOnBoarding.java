package com.peceinfotech.shoppre.UI.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FifthOnBoarding extends Fragment {

    private Button startShopping;
    ImageView backBtn4;
    TextView lockerNo;
    SharedPrefManager sharedPrefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth_on_boarding, container, false);

        startShopping = view.findViewById(R.id.startShoppingBtn);
        backBtn4 = view.findViewById(R.id.back_arrow4);
        lockerNo = view.findViewById(R.id.lockerNo);

        sharedPrefManager = new SharedPrefManager(getActivity());


        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.on), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            String token = sharedPrefManager.getBearerToken();
            callMeApi(token);
        }

        startShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , OrderActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        backBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
    private void callMeApi(String token) {

        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTAwNTU4LCJzZXNzaW9uX2lkIjoxMTMzMCwiaWF0IjoxNjM1NDIzNzAyLCJleHAiOjE2MzU0MjczMDJ9.UDJAah-VmB2fkgIob3PRD5-HueU2wuBF8PXW0mcRYXY
        Call<MeResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getUser("Bearer "+token);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                Toast.makeText(getActivity(), "Bearer "+token, Toast.LENGTH_SHORT).show();
                if (response.code() == 200){
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeFirstName(response.body().getFirstName());
                    sharedPrefManager.storeLastName(response.body().getLastName());
                    sharedPrefManager.storeFullName(response.body().getName());
                    sharedPrefManager.storeEmail(response.body().getEmail());
                    sharedPrefManager.storeId(response.body().getId());
                    sharedPrefManager.storeSalutation(response.body().getSalutation());
                    sharedPrefManager.storeVirtualAddressCode(response.body().getVirtualAddressCode());

                    Toast.makeText(getActivity(), response.body().getSalutation()
                            +"\n"+response.body().getFirstName()+"\n" +
                            response.body().getLastName()+"\n"+
                            response.body().getName()+"\n"+
                            response.body().getEmail()+"\n"+
                            response.body().getVirtualAddressCode(), Toast.LENGTH_LONG).show();
                }
                else  if(response.code() == 401){
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), "not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}