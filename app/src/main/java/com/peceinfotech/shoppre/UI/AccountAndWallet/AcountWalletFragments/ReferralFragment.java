package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.peceinfotech.shoppre.Adapters.AccountAndWallet.ReferralAdapter;
import com.peceinfotech.shoppre.Models.RefferalDummyModel;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class ReferralFragment extends Fragment {

    List<RefferalDummyModel> list;
    ReferralAdapter referralAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView referralRecycle;
    MaterialCardView haveARefCard;
    LinearLayout emptyReferralLayout  , showMoreLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referral, container, false);

        emptyReferralLayout = view.findViewById(R.id.emptyReferralLayout);
        haveARefCard = view.findViewById(R.id.haveARefCard);
        showMoreLayout = view.findViewById(R.id.showMoreLayout);
        referralRecycle = view.findViewById(R.id.referralRecycler);

        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());


        list.add(new RefferalDummyModel(R.drawable.ic_account
                ,"06 Jan 2021"
                ,"christo@shoppreparcels.com"));
        list.add(new RefferalDummyModel(R.drawable.ic_account
                ,"31 Dec 2020"
                ,"punith@shoppreparcels.com"));
        list.add(new RefferalDummyModel(R.drawable.ic_account
                ,"25 Dec 2020"
                ,"bibhas@shoppreparcels.com"));
        list.add(new RefferalDummyModel(R.drawable.ic_account
                ,"08 Dec 2020"
                ,"meena@shopprecommerce.com"));

        referralAdapter = new ReferralAdapter(getContext() , list);
        referralRecycle.setLayoutManager(linearLayoutManager);
        referralRecycle.setAdapter(referralAdapter);
        int number = referralRecycle.getAdapter().getItemCount();
        if (number == 0)
        {
            haveARefCard.setVisibility(View.VISIBLE);
            emptyReferralLayout.setVisibility(View.VISIBLE);
            referralRecycle.setVisibility(View.GONE);
            showMoreLayout.setVisibility(View.GONE);
        }
        else {
            haveARefCard.setVisibility(View.GONE);
            emptyReferralLayout.setVisibility(View.GONE);

        }


        referralAdapter.notifyDataSetChanged();
        return  view;
    }
}