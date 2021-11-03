package com.peceinfotech.shoppre.UI.Shipment;

import static com.peceinfotech.shoppre.R.drawable.*;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;


public class ShippingCalculator extends Fragment {


    TextView yesButton, noButton;
    LinearLayout yesNoButtonLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_calculator, container, false);

        yesButton = view.findViewById(R.id.yesButton);
        noButton = view.findViewById(R.id.noButton);
        yesNoButtonLayout = view.findViewById(R.id.yesNoButtonLayout);



        yesButton.setBackground(getResources().getDrawable(yes_btn_bg));
        yesButton.setTextColor(getResources().getColor(R.color.white));
        noButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onClick(View v) {


                    noButton.setTextColor(getResources().getColor(R.color.white));
                    yesButton.setTextColor(getResources().getColor(R.color.black));
//                noButton.setBackgroundColor(getResources().getColor(R.color.selected_btn_color));
//                yesButton.setBackgroundColor(getResources().getColor(R.color.unselected_btn_color));
//                yesNoButtonLayout.setBackgroundResource(edit_text_bg);
                noButton.setBackground(getResources().getDrawable(no_btn_bg));
                yesButton.setBackground(getResources().getDrawable(yes_btn_bg1));


            }
        });


        yesButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onClick(View v) {


                yesButton.setTextColor(getResources().getColor(R.color.white));
                noButton.setTextColor(getResources().getColor(R.color.black));

//                yesButton.setBackgroundColor(getResources().getColor(R.color.selected_btn_color));
//                noButton.setBackgroundColor(getResources().getColor(R.color.unselected_btn_color));
//                yesNoButtonLayout.setBackgroundResource(yes_btn_bg);
                yesButton.setBackground(getResources().getDrawable(yes_btn_bg));
                noButton.setBackground(getResources().getDrawable(no_btn_bg1));



            }
        });


        return view;
    }
}