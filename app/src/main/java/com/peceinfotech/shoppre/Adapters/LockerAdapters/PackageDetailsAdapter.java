package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.LockerModelResponse.PackageItem;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Locker.ShipWithinIndiaFragment;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;
import com.peceinfotech.shoppre.Utils.ViewPhotoDialog;

import java.util.List;

public class PackageDetailsAdapter extends RecyclerView.Adapter<PackageDetailsAdapter.viewHolder> {

    List<PackageItem> list;
    Context context;
    private PopupWindow mDropdown = null;
    SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
    GetData getData;
    public boolean isEdit = false;

    public PackageDetailsAdapter(List<PackageItem> list, Context context, GetData getData) {
        this.list = list;
        this.context = context;
        this.getData = getData;
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
        String url = (String) packageDetailsResponse.getUrl();
        holder.packageItemImage.getDrawable();
        holder.packageItemName.setText(packageDetailsResponse.getName());
        holder.packageItemId.setText("Item ID: #" + String.valueOf(packageDetailsResponse.getId()));
        holder.packageQuantity.setText("Qty:" + String.valueOf(packageDetailsResponse.getQuantity()));
        Glide.with(context)
                .load(url)
                .into(holder.packageItemImage);
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

                }
            }
        });

        holder.secondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData.click(packageDetailsResponse.getQuantity(), packageDetailsResponse.getPackageId(), packageDetailsResponse.getId(), position
                        , holder.secondLayoutBg
                        , holder.thirdLayoutBg, holder.thirdPriceEditText , holder.secondPriceEditText);

//                getData.click(packageDetailsResponse.getId(),position , holder.image , holder.priceEditText);
//                holder.thirdLayoutBg.setVisibility(View.VISIBLE);
//                holder.secondLayoutBg.setVisibility(View.GONE);
//                holder.thirdPriceEditText.setEnabled(true);
            }
        });

//        holder.thirdImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getData.click(packageDetailsResponse.getId(),position , holder.image , holder.priceEditText);
//                holder.layoutBg.setVisibility(View.VISIBLE);
//                holder.thirdLayoutBg.setVisibility(View.GONE);
//                holder.priceEditText.setEnabled(true);
//            }
//        });

        holder.three_dots.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context, v);

                popupMenu.getMenuInflater().inflate(R.menu.three_dots_menu, popupMenu.getMenu());

                if (list.size() < 2) {
                    popupMenu.getMenu().findItem(R.id.split_package).setVisible(false);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.return_menu:
//                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
//
//                                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReturnLanding(), null)
//                                        .addToBackStack(null).commit();
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

                                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipWithinIndiaFragment(), null)
                                        .addToBackStack(null).commit();

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

                ViewPhotoDialog viewPhotoDialog = new ViewPhotoDialog();
                viewPhotoDialog.showDialog(context, packageDetailsResponse);


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


        }


    }

    public interface GetData {
        void dotsVisiblity(String type, Integer id, int position);

        void getId(Integer id, CheckBox packageDetailCheckbox);

        void singleProceed(String type, Integer id);

        void click(Integer quantity, Integer packageId, Integer id, int position, LinearLayout secondLayoutBg, LinearLayout LayoutBg, EditText thirdPriceEditText, EditText s);


    }

}
