package com.shoppreglobal.shoppre.UI.Locker.ViewPackageTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.LockerAdapters.PackageUpdateAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.AddCommentResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.GetCommentsResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.EngageRetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageUpdates extends Fragment {

    RecyclerView packageUpdatesRecycler;
    List<GetCommentsResponse> list = new ArrayList<>();
    Integer id;
    PackageUpdateAdapter packageUpdateAdapter;
    ImageView send;
    EditText text;
    String textString;
    SharedPrefManager sharedPrefManager;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_updates, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        packageUpdatesRecycler = view.findViewById(R.id.packageUpdatesRecycler);
        send = view.findViewById(R.id.send);
        text = view.findViewById(R.id.text);

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
        }
        LoadingDialog.showLoadingDialog(getActivity(), "");
        callGetCommentsApi();


        send.setOnClickListener(new View.OnClickListener() {
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





        return view;
    }

    private boolean validateBtn() {
        textString = text.getText().toString();
        if (textString.equals("")) {
            return false;
        } else {
            return true;
        }

    }

    private void callAddComment() {
        JsonObject object = new JsonObject();
        object.addProperty("comments" , textString);

        Call<AddCommentResponse> call = EngageRetrofitClient
                .getInstance3().getAppApi().addPackageComment("Bearer "+sharedPrefManager.getBearerToken()
                        ,id  ,object.toString());
        call.enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                if (response.code()==201){
                    text.setText("");
                    callGetCommentsApi();

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

    private void callGetCommentsApi() {
        Call<List<GetCommentsResponse>> call= EngageRetrofitClient
                .getInstance3().getAppApi().getPackageComments(id);

        call.enqueue(new Callback<List<GetCommentsResponse>>() {
            @Override
            public void onResponse(Call<List<GetCommentsResponse>> call, Response<List<GetCommentsResponse>> response) {
                if (response.code()==200){

                    list = response.body();



                    packageUpdateAdapter = new PackageUpdateAdapter(list, getContext());
                    packageUpdatesRecycler.setAdapter(packageUpdateAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
                    layoutManager.scrollToPosition(0);
                    packageUpdatesRecycler.smoothScrollToPosition(0);
                    packageUpdatesRecycler.setLayoutManager(layoutManager);



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
}