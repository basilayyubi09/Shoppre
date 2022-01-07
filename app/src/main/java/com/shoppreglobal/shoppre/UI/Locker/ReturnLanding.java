package com.shoppreglobal.shoppre.UI.Locker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;


public class ReturnLanding extends Fragment {

    LinearLayout sellerPickerBorder, shipItToSellerBorder;
    ImageView sellerPickerCheckImage, shipItToSellerCheckImage;
    MaterialButton return_landing_proceed_btn;
    int flag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_return_landing, container, false);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.lockerMenu).setChecked(true);
        sellerPickerBorder = view.findViewById(R.id.sellerPickerBorder);
        shipItToSellerBorder = view.findViewById(R.id.shipItToSellerBorder);

        sellerPickerCheckImage = view.findViewById(R.id.sellerPickerCheckImage);
        shipItToSellerCheckImage = view.findViewById(R.id.shipItToSellerCheckImage);
        return_landing_proceed_btn = view.findViewById(R.id.return_landing_proceed_btn);

        sellerPickerBorder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                flag = 1;

                sellerPickerBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_check_border));
                sellerPickerCheckImage.setVisibility(View.VISIBLE);

                shipItToSellerBorder.setBackground(getResources().getDrawable(R.drawable.return_landing_background));
                shipItToSellerCheckImage.setVisibility(View.INVISIBLE);

            }
        });

        shipItToSellerBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;

                sellerPickerBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_layout_radius));
                sellerPickerCheckImage.setVisibility(View.INVISIBLE);

                shipItToSellerBorder.setBackground(getResources().getDrawable(R.drawable.ship_it_to_seller_check_border));
                shipItToSellerCheckImage.setVisibility(View.VISIBLE);


            }
        });

        return_landing_proceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1){

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new LockerReadyToShip(), null)
                            .addToBackStack(null).commit();

                }else if(flag == 2){

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipItToSellerLanding(), null)
                            .addToBackStack(null).commit();

                }else {
                    flag = 0;
                    Toast.makeText(getContext(), "Please select any one to Proceed", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
}