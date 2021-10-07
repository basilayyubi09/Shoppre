package com.peceinfotech.shoppre.UI.OnBoarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.peceinfotech.shoppre.R;

public class ThirdOnBoarding extends Fragment {

    private Button nextBtn3;
    TextView skip3;
    ImageView backBtn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_on_boarding, container, false);

        nextBtn3 = view.findViewById(R.id.next_btn_3);
        skip3 = view.findViewById(R.id.skip3);
        backBtn2 = view.findViewById(R.id.back_arrow2);

        nextBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoardingActivity.fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.entry_right_to_left, R.anim.exit_right_to_left, R.anim.entry_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.onboarding_container,new FourthOnBoarding(),null).addToBackStack(null).commit();
            }
        });

        skip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoardingActivity.fragmentManager.beginTransaction()
                        .replace(R.id.onboarding_container, new FourthOnBoarding(),null).addToBackStack(null).commit();
            }
        });

        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoardingActivity.fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.entry_left_to_right, R.anim.exit_left_to_right, R.anim.entry_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.onboarding_container,new SecoundOnBoarding(),null).commit();
            }
        });

        return view;
    }
}