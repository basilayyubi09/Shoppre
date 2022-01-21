package com.shoppreglobal.shoppre.Adapters.LockerAdapters;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageItem;
import com.shoppreglobal.shoppre.LockerModelResponse.ReturnPackageResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;
import com.shoppreglobal.shoppre.Utils.ViewPhotoDialog;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageDetailsAdapter extends RecyclerView.Adapter<PackageDetailsAdapter.viewHolder> {

    List<PackageItem> list;
    Context context;
    private PopupWindow mDropdown = null;
    SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
    GetData getData;
    public boolean isEdit = false;
    Integer packageId;
    String obj;
    String[] menu = {"RETURN", "EXCHANGE", "DISCARD", "SPLIT PACKAGE", "SHIP IN INDIA"};
    ArrayAdapter arrayAdapter;

    public PackageDetailsAdapter(List<PackageItem> list, Context context, Integer packageId, GetData getData) {
        this.list = list;
        this.context = context;
        this.getData = getData;
        this.packageId = packageId;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_details_single_layout, parent, false);
        return new viewHolder(view, getData);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        PackageItem packageDetailsResponse = list.get(position);
        String url = packageDetailsResponse.getObject();
        holder.packageItemImage.getDrawable();
        holder.packageItemName.setText(packageDetailsResponse.getName());
        holder.packageItemId.setText("Item ID: #" + String.valueOf(packageDetailsResponse.getId()));
        holder.packageQuantity.setText("Qty:" + String.valueOf(packageDetailsResponse.getQuantity()));
        if (packageDetailsResponse.getPhotoRequests().isEmpty()) {
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(50)))
                    .into(holder.packageItemImage);
        } else {
            Glide.with(context)
                    .load(url)
                    .into(holder.packageItemImage);
        }

        holder.priceEditText.setText("₹ " + String.valueOf(packageDetailsResponse.getPriceAmount()));
        holder.secondPriceEditText.setText("₹ " + String.valueOf(packageDetailsResponse.getPriceAmount()));
        holder.thirdPriceEditText.setText("₹ " + String.valueOf(packageDetailsResponse.getPriceAmount()));

        holder.packageDetailCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData.getId(packageDetailsResponse.getId(), holder.packageDetailCheckbox);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getData.click(packageDetailsResponse.getId(),position , holder.secondLayoutBg , holder.layoutBg);

                if (!isEdit) {
                    isEdit = true;
                    holder.secondLayoutBg.setVisibility(View.VISIBLE);
                    holder.layoutBg.setVisibility(View.GONE);
                    holder.secondPriceEditText.setEnabled(true);

                    holder.secondPriceEditText.setSelection(holder.secondPriceEditText.getText().length());
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(holder.secondPriceEditText, InputMethodManager.SHOW_IMPLICIT);


                }
            }
        });

        holder.secondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData.click(packageDetailsResponse.getQuantity(),
                        packageDetailsResponse.getPackageId()
                        , packageDetailsResponse.getId(), position
                        , holder.secondLayoutBg
                        , holder.thirdLayoutBg, holder.thirdPriceEditText, holder.secondPriceEditText);

            }
        });


        holder.three_dots.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);

                PopupMenu popupMenu = new PopupMenu(wrapper, v);

                popupMenu.setGravity(Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.three_dots_menu, popupMenu.getMenu());

                if (list.size() < 2) {
                    popupMenu.getMenu().findItem(R.id.split_package).setVisible(false);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.return_menu:

                                showDialog("return", packageDetailsResponse.getId());


                                break;
                            case R.id.exchange:
                                showDialog("exchange", packageDetailsResponse.getId());
                                break;
                            case R.id.discard:
                                showDialog("discard", packageDetailsResponse.getId());
                                break;
                            case R.id.split_package:

                                showDialog("split", packageDetailsResponse.getId());
                                break;


                            case R.id.ship_to_india:

//                                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipWithinIndiaFragment(), null)
//                                        .addToBackStack(null).commit();

                                break;
                        }

                        return true;
                    }

                    private void showDialog(String type, Integer id) {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.return_item_dialog_box);

                        MaterialButton addMoreProductBtn = dialog.findViewById(R.id.addMoreProductBtn);
                        ImageView close = dialog.findViewById(R.id.close);
                        TextView string = dialog.findViewById(R.id.string);
                        if (list.size() < 2) {
                            addMoreProductBtn.setVisibility(View.GONE);
                        } else {
                            addMoreProductBtn.setVisibility(View.VISIBLE);
                        }
                        if (type.equals("return")) {
                            string.setText(R.string.return_item_string);
                        } else if (type.equals("exchange")) {
                            string.setText(R.string.exchange);
                        } else if (type.equals("discard")) {
                            string.setText(R.string.discard);
                        } else if (type.equals("split")) {
                            string.setText(R.string.split);
                        }

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                getData.dotsVisiblity(type, id, position);

                                dialog.dismiss();

                            }
                        });

                        MaterialButton continueBtn = dialog.findViewById(R.id.proceedWith1ItemBtn);
                        continueBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData.singleProceed(type, id);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }
                });
                popupMenu.show();

            }
        });


        holder.viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewPhotoDialog viewPhotoDialog = new ViewPhotoDialog(list.size(), new ViewPhotoDialog.Click() {
                    @Override
                    public void fiveRupeesClick(MaterialButton unlockPhotoBtn
                            , TextView orText
                            , ImageView mainPhoto
                            , ImageView secondPhoto
                            , ImageView thirdPhoto, TextView popUpAdditionalText) {
                        if (packageDetailsResponse.getObject().equals(null)) {
                            obj = null;
                        } else obj = packageDetailsResponse.getObject();
                        callStandardPhotoApi(packageId
                                , unlockPhotoBtn,
                                orText,
                                packageDetailsResponse.getId(), obj
                                , mainPhoto, popUpAdditionalText);

                    }

                    @Override
                    public void proceed(Integer id) {
                        getData.dotsVisiblity("multiPhoto", id, position);
                    }

                    @Override
                    public void multi(Integer id) {
                        getData.singleProceed("singlePhoto", id);
                    }

                    @Override
                    public void Dismiss(Dialog dialog) {
                        dialog.dismiss();
                        getData.callApi();
                    }
                });
                viewPhotoDialog.showDialog(context, packageDetailsResponse);


            }
        });


        ////////////////////////////////////////////////////////

        arrayAdapter = new ArrayAdapter(context, R.layout.pop_menu, menu);
        holder.threeDotSanple.setAdapter(arrayAdapter);


    }

    private void callStandardPhotoApi(Integer packageId
            , MaterialButton unlockPhotoBtn
            , TextView orText, Integer id, String obj, ImageView mainPhoto, TextView popUpAdditionalText) {
        LoadingDialog.showLoadingDialog(context, "");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("comments", "Standard Photo Requested");
        jsonObject.add("itemId", jsonArray);
        jsonObject.addProperty("state_id", "");
        jsonObject.addProperty("type", "standard_photo");

        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().standardPhoto("Bearer " + sharedPrefManager.getBearerToken()
                        , packageId
                        , jsonObject.toString());
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 200) {
                    unlockPhotoBtn.setVisibility(View.GONE);
                    orText.setVisibility(View.GONE);

                    if (obj.equals(null)) {

                    } else {
                        Glide.with(context)
                                .load(obj)
                                .into(mainPhoto);
                    }

                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "Something Went Wrong Please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView packageItemImage, image, three_dots, secondImage, thirdImage;
        TextView packageItemName, packageItemId, packageQuantity;
        CheckBox packageDetailCheckbox;
        GetData getData;
        EditText priceEditText, secondPriceEditText, thirdPriceEditText;
        View viewVertical, secondViewVertical, thirdViewVertical;
        LinearLayout layoutBg, secondLayoutBg, thirdLayoutBg, viewImage;
        Spinner threeDotSanple;

        public viewHolder(@NonNull View itemView, GetData getData) {
            super(itemView);

            this.getData = getData;
            packageItemImage = itemView.findViewById(R.id.packageItemImage);
            viewVertical = itemView.findViewById(R.id.viewVertical);
            image = itemView.findViewById(R.id.image);
            secondImage = itemView.findViewById(R.id.secondImage);
            thirdImage = itemView.findViewById(R.id.thirdImage);
            layoutBg = itemView.findViewById(R.id.layoutBg);
            thirdLayoutBg = itemView.findViewById(R.id.thirdLayoutBg);
            secondViewVertical = itemView.findViewById(R.id.secondViewVertical);
            thirdViewVertical = itemView.findViewById(R.id.thirdViewVertical);
            secondPriceEditText = itemView.findViewById(R.id.secondPriceEditText);
            thirdPriceEditText = itemView.findViewById(R.id.thirdPriceEditText);
            secondLayoutBg = itemView.findViewById(R.id.secondLayoutBg);
            viewImage = itemView.findViewById(R.id.packageDetailsImageLayout);
            priceEditText = itemView.findViewById(R.id.priceEditText);

            packageItemName = itemView.findViewById(R.id.packageItemName);
            packageItemId = itemView.findViewById(R.id.packageItemId);
            packageQuantity = itemView.findViewById(R.id.packageQuantity);

            three_dots = itemView.findViewById(R.id.three_dots);
            packageDetailCheckbox = itemView.findViewById(R.id.packageDetailCheckbox);

            threeDotSanple = itemView.findViewById(R.id.threeDotsSample);


        }


    }

    public interface GetData {
        void dotsVisiblity(String type, Integer id, int position);

        void getId(Integer id, CheckBox packageDetailCheckbox);

        void singleProceed(String type, Integer id);

        void click(Double quantity, Integer packageId, Integer id, int position, LinearLayout secondLayoutBg, LinearLayout LayoutBg, EditText thirdPriceEditText, EditText s);

        void callApi();

    }

}
