package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Adapters.OrderAdapter.ParentFinalOrderSummaryAdapter;
import com.peceinfotech.shoppre.Models.Section;
import com.peceinfotech.shoppre.Models.SectionItem;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;


public class FinalOrderSummaryFragment extends Fragment {


    List<Section> list = new ArrayList<>();
    RecyclerView recyclerView;
    ParentFinalOrderSummaryAdapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_order_summary, container, false);

        recyclerView = view.findViewById(R.id.recycleFinal);
        String section = "Myntra";
        int personal = 200;
        int additional = 100;
        List<SectionItem> item1 = new ArrayList<>();
        item1.add(new SectionItem("Reebok Men...(01)" , 1 , 1218));
        item1.add(new SectionItem("Kurta Shopp...(02)" , 2 , 1496));

        String section2 = "Amazon.in";
        int personal2 = 200;
        int additional2 = 50;
        List<SectionItem> item2 = new ArrayList<>();
        item2.add(new SectionItem("Nike Running S...(01)" , 1 , 2460));

        list.add(new Section(section,personal,additional , item1));
        list.add(new Section(section2,personal2 , additional2 , item2));

        adapter = new ParentFinalOrderSummaryAdapter(list , getActivity() );
        recyclerView.setAdapter(adapter);
        return view;
    }
}