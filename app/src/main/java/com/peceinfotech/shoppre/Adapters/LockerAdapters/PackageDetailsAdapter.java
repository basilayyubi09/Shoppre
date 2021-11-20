package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import static com.facebook.FacebookSdk.getApplicationContext;

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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.LockerModelResponse.PackageItem;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Locker.ReturnLanding;
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
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PackageItem packageDetailsResponse = list.get(position);
        String url = (String) packageDetailsResponse.getUrl();
        holder.packageItemImage.getDrawable();
        holder.packageItemName.setText(packageDetailsResponse.getName());
        holder.packageItemId.setText("Item ID: #"+String.valueOf(packageDetailsResponse.getId()));
        holder.packageQuantity.setText("Qty:"+String.valueOf(packageDetailsResponse.getQuantity()));
        Glide.with(context)
                .load(url)
                .into(holder.packageItemImage);
        holder.packageItemPrice.setText(String.valueOf(packageDetailsResponse.getPriceAmount()));


        holder.three_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);

                popupMenu.getMenuInflater().inflate(R.menu.three_dots_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.return_menu:
                                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();

                                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReturnLanding(), null)
                                        .addToBackStack(null).commit();

//                                showDialog();


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

                                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipWithinIndiaFragment(), null)
                                        .addToBackStack(null).commit();

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

                                getData.dotsVisiblity();

                                dialog.dismiss();

                            }
                        });


                        dialog.show();

                    }
                });
                popupMenu.show();

            }
        });

        holder.viewPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewPhotoDialog viewPhotoDialog = new ViewPhotoDialog();
                viewPhotoDialog.showDialog(context);

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView packageItemImage, editPackageItem, three_dots ;
        TextView packageItemName, packageItemId, packageQuantity, packageItemPrice, viewPhotoBtn;
        CheckBox packageDetailCheckbox;
        GetData getData;

        public viewHolder(@NonNull View itemView, GetData getData) {
            super(itemView);

            this.getData = getData;
            packageItemImage = itemView.findViewById(R.id.packageItemImage);
            editPackageItem = itemView.findViewById(R.id.editPackageItem);
            packageItemName = itemView.findViewById(R.id.packageItemName);
            packageItemId = itemView.findViewById(R.id.packageItemId);
            packageQuantity = itemView.findViewById(R.id.packageQuantity);
            packageItemPrice = itemView.findViewById(R.id.packageItemPrice);
            three_dots = itemView.findViewById(R.id.three_dots);
            packageDetailCheckbox = itemView.findViewById(R.id.packageDetailCheckbox);
            viewPhotoBtn = itemView.findViewById(R.id.viewPhotoBtn);

        }


    }

    public interface GetData {
        void dotsVisiblity();
    }

}
