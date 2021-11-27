package com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.PackageDetailsAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageItem;
import com.peceinfotech.shoppre.LockerModelResponse.ReturnPackageResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageDetails extends Fragment {

    RecyclerView packageDetailsRecycler;
    List<PackageItem> list;
    PackageDetailsAdapter packageDetailsAdapter;
    List<Integer> ids;
    MaterialButton floatingBtn;
    JsonArray jsonArray = new JsonArray();
    String type;
    Integer packageId;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_details, container, false);
        packageDetailsRecycler = view.findViewById(R.id.packageDetailsRecycler);
        floatingBtn = view.findViewById(R.id.floatingBtn);
        sharedPrefManager = new SharedPrefManager(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            list = (List<PackageItem>) bundle.getSerializable("list");
            packageId = bundle.getInt("id");

        }

        ids = new ArrayList<>();


        packageDetailsAdapter = new PackageDetailsAdapter(list, getContext(), new PackageDetailsAdapter.GetData() {
            @Override
            public void dotsVisiblity(String type1, Integer id, int position) {

                type = type1;
                for (int i = 0; i < list.size(); i++) {
                    View view1 = packageDetailsRecycler.getChildAt(i);

                    CheckBox checkBox = view1.findViewById(R.id.packageDetailCheckbox);
                    ImageView dots = view1.findViewById(R.id.three_dots);
//                    EditText editText = view1.findViewById(R.id.priceEditText);
//                    View viewVertical = view1.findViewById(R.id.viewVertical);
//                    LinearLayout layoutBg = view1.findViewById(R.id.layoutBg);
//                    ImageView image = view1.findViewById(R.id.image);

                    dots.setVisibility(View.GONE);
                    checkBox.setVisibility(View.VISIBLE);
                    if (position == i) {
                        if (checkBox.isChecked()){
                            ids.add(id);
                            showButton();
                        }
                        else {
                            checkBox.setChecked(true);
                            ids.add(id);
                            showButton();
                        }

                    }

//                    editText.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            layoutBg.setBackground(getActivity().getDrawable(R.drawable.price_per_item_border));
//                            viewVertical.setBackground(getActivity().getDrawable(R.color.text_red));
//                            image.setImageResource(R.mipmap.exclamation);
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                        }
//                    });

                }

                packageDetailsAdapter.notifyDataSetChanged();

            }

            @Override
            public void getId(Integer ida, CheckBox packageDetailCheckbox) {
                for (int i = 0; i < list.size(); i++) {

                    if (packageDetailCheckbox.isChecked()) {
                        if (!ids.contains(ida)) {
                            ids.add(ida);
                            packageDetailCheckbox.setChecked(true);

                        }
                    } else {

                        if (ids.contains(ida)) {
                            ids.remove(ida);
                            packageDetailCheckbox.setChecked(false);
                        }
                    }

                }
                if (ids.size() > 0) {
                    showButton();

                } else {
                    floatingBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void singleProceed(String type1, Integer id) {
                type = type1;
                if (type.equals("return")) {
                    jsonArray.add(id);
                    callReturnPackageApi();
                } else if (type.equals("exchange")) {
//                    Toast.makeText(getActivity(), String.valueOf(jsonArray1)+"\n"+type, Toast.LENGTH_SHORT).show();
                } else if (type.equals("discard")) {
//                    Toast.makeText(getActivity(), String.valueOf(jsonArray1)+"\n"+type, Toast.LENGTH_SHORT).show();
                } else if (type.equals("split")) {
//                    Toast.makeText(getActivity(), String.valueOf(jsonArray1)+"\n"+type, Toast.LENGTH_SHORT).show();
                }
            }


        });
        packageDetailsRecycler.setAdapter(packageDetailsAdapter);


        return view;
    }

    private void callReturnPackageApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("itemIds", jsonArray);
        jsonObject.addProperty("message1", "return pickup will be done by seller");
        jsonObject.addProperty("message2", "");
        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3().getAppApi()
                .returnPackage("Bearer "+sharedPrefManager.getBearerToken(), packageId, jsonObject.toString());
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 201) {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), "S", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), "R", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showButton() {
        floatingBtn.setVisibility(View.VISIBLE);
        if (type.equals("return")) {
            floatingBtn.setText("Return Item(s)");
        } else if (type.equals("exchange")) {
            floatingBtn.setText("Exchange Item(s)");
        } else if (type.equals("discard")) {
            floatingBtn.setText("Discard Item(s)");
        } else if (type.equals("split")) {
            floatingBtn.setText("Split Item(s)");
        }
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("return")) {
                    floatingBtn.setText("Return Item(s)");
                } else if (type.equals("exchange")) {
                    floatingBtn.setText("Exchange Item(s)");
                } else if (type.equals("discard")) {
                    floatingBtn.setText("Discard Item(s)");
                } else if (type.equals("split")) {
                    floatingBtn.setText("Split Item(s)");
                }
                while (jsonArray.size() > 0) {
                    jsonArray.remove(0);
                }
                for (int i = 0; i < ids.size(); i++) {

                    if (!jsonArray.toString().contains(String.valueOf(ids.get(i)))) {
                        jsonArray.add(ids.get(i));
                    }

                }
                Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
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
                } else {
//                    callListingApi();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}