package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.Arrays;
import java.util.List;

public class PackageDetailsAdapter extends RecyclerView.Adapter<PackageDetailsAdapter.viewHolder> {

    List<PackageItem> list;
    Context context;
    private PopupWindow mDropdown = null;
    SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
    GetData getData;
    int flag = 0;
    String[] menuItems = {"RETURN", "EXCHANGE", "DISCARD", "SPLIT PACKAGE", "SHIP IN INDIA"};

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
        holder.priceEditText.setText("₹ "+String.valueOf(packageDetailsResponse.getPriceAmount()));


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
                        TextView string= dialog.findViewById(R.id.string);
                        if (type.equals("return")){
                            string.setText(R.string.return_item_string);
                        }
                        else if (type.equals("exchange")){
                            string.setText(R.string.exchange);
                        }
                        else if (type.equals("discard")){
                            string.setText(R.string.discard);
                        }
                        else if (type.equals("split")){
                            string.setText(R.string.split);
                        }

                        addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                getData.dotsVisiblity(type , id , position);

                                dialog.dismiss();

                            }
                        });

                        MaterialButton continueBtn = dialog.findViewById(R.id.proceedWith1ItemBtn);
                        continueBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData.singleProceed(type , id);
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }
                });
                popupMenu.show();

            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.viewPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewPhotoDialog viewPhotoDialog = new ViewPhotoDialog();
                viewPhotoDialog.showDialog(context);

            }
        });

//        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.dropdown_items, menuItems);
//        holder.three_dots_spinner.setAdapter(arrayAdapter);
//
//
//
//        holder.three_dots_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String text = parent.getItemAtPosition(position).toString();
//
//                if (position==0){
//                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//                }else if (position==1){
//                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//                }else if (position==2){
//                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//                }else if (position==3){
//                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        PowerMenuItem title1 = new PowerMenuItem("aaaa", false);
//        PowerMenuItem title2 = new PowerMenuItem("aaaa", false);
//        PowerMenuItem title3 = new PowerMenuItem("aaaa", false);
//        PowerMenuItem title4 = new PowerMenuItem("aaaa", false);
//        PowerMenu powerMenu = new PowerMenu.Builder(context)
//                .addItem(title1) // add an item.
//                .addItem(title2) // aad an item list.
//                .addItem(title3) // aad an item list.
//                .addItem(title4) // aad an item list.
//                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
//                .setMenuRadius(10f) // sets the corner radius.
//                .setMenuShadow(10f) // sets the shadow.
//                .setTextColor(ContextCompat.getColor(context, R.color.black))
//                .setTextGravity(Gravity.LEFT)
//                .setSelectedTextColor(Color.WHITE)
//                .setMenuColor(Color.WHITE)
//
//                .setSelectedMenuColor(ContextCompat.getColor(context, R.color.black))
////                .setOnMenuItemClickListener(onMenuItemClickListener)
//                .build();
//
//        if (list.size()<2){
//
//
//        }

//        powerMenu.setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
//            @Override
//            public void onItemClick(int position, PowerMenuItem item) {
//                powerMenu.setSelectedPosition(position); // change selected item
//                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
//                powerMenu.dismiss();
//            }
//        });

//        holder.three_dots.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                powerMenu.showAsDropDown(v);
//            }
//        });
//
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView packageItemImage, image, three_dots;
        Spinner three_dots_spinner;
        TextView packageItemName, packageItemId, packageQuantity, viewPhotoBtn;
        CheckBox packageDetailCheckbox;
        GetData getData;
        EditText priceEditText;
        View viewVertical;
        LinearLayout layoutBg;

        public viewHolder(@NonNull View itemView, GetData getData) {
            super(itemView);

            this.getData = getData;
            packageItemImage = itemView.findViewById(R.id.packageItemImage);
            viewVertical = itemView.findViewById(R.id.viewVertical);
            image = itemView.findViewById(R.id.image);
            layoutBg = itemView.findViewById(R.id.layoutBg);
            priceEditText = itemView.findViewById(R.id.priceEditText);

            packageItemName = itemView.findViewById(R.id.packageItemName);
            packageItemId = itemView.findViewById(R.id.packageItemId);
            packageQuantity = itemView.findViewById(R.id.packageQuantity);

            three_dots = itemView.findViewById(R.id.three_dots);
            packageDetailCheckbox = itemView.findViewById(R.id.packageDetailCheckbox);
            viewPhotoBtn = itemView.findViewById(R.id.viewPhotoBtn);
            three_dots_spinner = itemView.findViewById(R.id.three_dots_spinner);

        }


    }

    public interface GetData {
        void dotsVisiblity(String type, Integer id, int position);

        void getId(Integer id, CheckBox packageDetailCheckbox);

        void singleProceed(String type ,Integer id);

    }

}
