package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.LockerModelResponse.PackageDetailsResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.List;

public class PackageDetailsAdapter extends RecyclerView.Adapter<PackageDetailsAdapter.viewHolder>{

    List<PackageDetailsResponse> list;
    Context context;
    private PopupWindow mDropdown = null;
    SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());

    public PackageDetailsAdapter(List<PackageDetailsResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_details_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PackageDetailsResponse packageDetailsResponse = list.get(position);

        holder.packageItemImage.getDrawable();
        holder.packageItemName.setText(packageDetailsResponse.getPackageItemName());
        holder.packageItemId.setText(packageDetailsResponse.getPackageItemId());
        holder.packageQuantity.setText(packageDetailsResponse.getPackageItemQuantity());
        holder.packageItemPrice.setText(packageDetailsResponse.getPackageItemPrice());


        holder.three_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);

                popupMenu.getMenuInflater().inflate(R.menu.three_dots_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.return_menu:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
//                                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.return_item_dialog_box, null);
//
//                                MaterialButton addMoreProductBtn, proceedWith1ItemBtn;
//                                addMoreProductBtn = dialogView.findViewById(R.id.addMoreProductBtn);
//                                proceedWith1ItemBtn = dialogView.findViewById(R.id.proceedWith1ItemBtn);
//
//                                addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        holder.three_dots.setVisibility(View.GONE);
//                                        holder.packageDetailCheckbox.setVisibility(View.VISIBLE);
//                                    }
//                                });
//
//                                builder.setView(dialogView);
//                                builder.setCancelable(true);
//                                builder.show();
                                showDialog();


                                break;
                            case R.id.exchange:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.discard:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.split_package:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.ship_to_india:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return true;
                    }

                    private void showDialog() {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.return_item_dialog_box);

                        MaterialButton addMoreProductBtn = dialog.findViewById(R.id.addMoreProductBtn);

                        addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                for (int i=0; i<list.size(); i++){
//                                    holder.three_dots.setVisibility(View.GONE);
//                                    holder.packageDetailCheckbox.setVisibility(View.VISIBLE);

                                    notifyDataSetChanged();
                                }

                                dialog.dismiss();

                            }
                        });


                            dialog.show();

                        }
                });
                popupMenu.show();

            }
        });



    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView packageItemImage, editPackageItem, three_dots;
        TextView packageItemName, packageItemId, packageQuantity, packageItemPrice;
        CheckBox packageDetailCheckbox;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            packageItemImage = itemView.findViewById(R.id.packageItemImage);
            editPackageItem = itemView.findViewById(R.id.editPackageItem);
            packageItemName = itemView.findViewById(R.id.packageItemName);
            packageItemId = itemView.findViewById(R.id.packageItemId);
            packageQuantity = itemView.findViewById(R.id.packageQuantity);
            packageItemPrice = itemView.findViewById(R.id.packageItemPrice);
            three_dots = itemView.findViewById(R.id.three_dots);
            packageDetailCheckbox = itemView.findViewById(R.id.packageDetailCheckbox);

        }


    }

}
