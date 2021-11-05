package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;


public class SelfShopper extends Fragment {

    LinearLayout shopForMeBorder, shopForMySelfBorder;
    ImageView shopForMeCheckImage, shopForMySelfCheckImage;
    MaterialButton personalShopProceedBtn;

    int flag = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_shopper, container, false);

        shopForMeBorder = view.findViewById(R.id.shopForMeBorder);
        shopForMySelfBorder = view.findViewById(R.id.shopForMySelfBorder);
        shopForMeCheckImage = view.findViewById(R.id.shopForMeCheckImage);
        shopForMySelfCheckImage = view.findViewById(R.id.shopForMySelfCheckImage);
        personalShopProceedBtn = view.findViewById(R.id.personalShopProceedBtn);


        shopForMeBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 1;

                shopForMeBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_check_border));
                shopForMeCheckImage.setVisibility(View.VISIBLE);


                shopForMySelfBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_layout_radius_2));
                shopForMySelfCheckImage.setVisibility(View.INVISIBLE);


            }
        });

        shopForMySelfBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 2;

                shopForMySelfBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_check_border_2));
                shopForMySelfCheckImage.setVisibility(View.VISIBLE);

                shopForMeBorder.setBackground(getResources().getDrawable(R.drawable.shop_for_me_layout_radius));
                shopForMeCheckImage.setVisibility(View.INVISIBLE);


            }
        });

        personalShopProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag==1){

                    if (savedInstanceState != null)  return;
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new EmptyCart(), null)
                            .addToBackStack(null).commit();

                }else if (flag == 2){

                    if (savedInstanceState != null)  return;
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopperPlaceOrderFargment(), null)
                            .addToBackStack(null).commit();

                }else {

                    flag = 0;
                    Toast.makeText(getContext(), "Please Select to Proceed", Toast.LENGTH_SHORT).show();

                }


            }
        });


        return view;
    }
}