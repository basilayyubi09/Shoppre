package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.PriceTableAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.PriceTableResponse;

import java.util.ArrayList;
import java.util.List;

public class PricingTableDialog{

    List<PriceTableResponse> list = new ArrayList<>();
    RecyclerView pricingTableRecycler;

    public void showDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pricing_table_pop_up);

        pricingTableRecycler = dialog.findViewById(R.id.pricingTableRecycler);

        list.add(new PriceTableResponse("0.5", "1,445", "19"));
        list.add(new PriceTableResponse("0.5", "1,445", "19"));
        list.add(new PriceTableResponse("0.5", "1,445", "19"));
        list.add(new PriceTableResponse("0.5", "1,445", "19"));
        list.add(new PriceTableResponse("0.5", "1,445", "19"));
        list.add(new PriceTableResponse("0.5", "1,445", "19"));
        list.add(new PriceTableResponse("0.5", "1,445", "19"));



        PriceTableAdapter priceTableAdapter = new PriceTableAdapter(list, context);
        pricingTableRecycler.setAdapter(priceTableAdapter);




        dialog.show();

    }
}
