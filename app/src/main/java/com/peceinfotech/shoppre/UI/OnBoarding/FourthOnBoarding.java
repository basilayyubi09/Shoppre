package com.peceinfotech.shoppre.UI.OnBoarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.peceinfotech.shoppre.R;

public class FourthOnBoarding extends Fragment {

    private Button unlockBtn;
    ImageView backBtn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth_on_boarding, container, false);

        unlockBtn = view.findViewById(R.id.unlock_btn);
        backBtn3 = view.findViewById(R.id.back_arrow3);

        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnBoarding.fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.entry_right_to_left, R.anim.exit_right_to_left, R.anim.entry_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.onboarding_container,new FifthOnBoarding(),null).addToBackStack(null).commit();

            }
        });

        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoarding.fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.entry_left_to_right, R.anim.exit_left_to_right, R.anim.entry_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.onboarding_container,new ThirdOnBoarding(),null).commit();
            }
        });

        return view;
    }
}