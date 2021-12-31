package com.shoppreglobal.shoppre.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.IncomingPkg;
import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.ViewOrderPersonalShop;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int FIRST_LIST = 0;
    final int SECOND_LIST = 1;
    List<Order> list;
    List<IncomingPkg> list1;
    Context context;

    public OrdersAdapter(List<Order> list, List<IncomingPkg> list1, Context context) {
        this.list = list;
        this.list1 = list1;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_order_layout, parent, false);
//        return new viewHolder(view);
        if (viewType == FIRST_LIST){
            return new FirstListViewHolder(view);
        }
        if (viewType == SECOND_LIST){
            return new SecondListViewHolder(view);
        }
        return null;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof FirstListViewHolder){
            ((FirstListViewHolder) holder).populate(list.get(position));
        }

        if(holder instanceof SecondListViewHolder){

            ((SecondListViewHolder) holder).populate(list1.get(position - list.size()));
        }
//        Order order = list.get(position);
//        holder.orderWebsiteName.setText(order.getStore().getName());
//        holder.orderNo.setText("Order No: " + "#" + order.getOrderCode());
//
//        String date1 = order.getCreatedAt();
//        String[] split = date1.split("T");
//        String date = split[0];
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
//        LocalDate ld = LocalDate.parse(date, dtf);
//        String month_name = dtf2.format(ld);
//
//        holder.orderDate.setText(month_name);
//        holder.orderImage.setImageResource(R.drawable.ic_personal_shopper);
//
//        holder.viewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Bundle bundle = new Bundle();
//
//                bundle.putString("orderCode", order.getOrderCode());
//                bundle.putString("id", String.valueOf(order.getId()));
//
//                ViewOrderPersonalShop viewOrderPersonalShop = new ViewOrderPersonalShop();
//                viewOrderPersonalShop.setArguments(bundle);
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, viewOrderPersonalShop, null)
//                        .addToBackStack(null).commit();
//
//            }
//        });

    }
    @Override
    public int getItemViewType(int position){
        if(position < list.size()){
            return FIRST_LIST;
        }

        if(position - list.size() < list1.size()){
            return SECOND_LIST;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return list.size() + list1.size();
    }



//    public class viewHolder extends RecyclerView.ViewHolder {
//
//        ImageView orderImage;
//        TextView orderWebsiteName, orderNo, orderDate;
//        LinearLayout viewMore;
//
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            orderImage = itemView.findViewById(R.id.singleOrderImage);
//            orderWebsiteName = itemView.findViewById(R.id.orderWebsiteName);
//            orderNo = itemView.findViewById(R.id.orderNo);
//            orderDate = itemView.findViewById(R.id.orderDate);
//            viewMore = itemView.findViewById(R.id.viewMore);
//
//
//        }
//    }



    public class FirstListViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImage;
        TextView orderWebsiteName, orderNo, orderDate;
        LinearLayout viewMore;

        public FirstListViewHolder(View itemView){
            super(itemView);

            orderImage = itemView.findViewById(R.id.singleOrderImage);
            orderWebsiteName = itemView.findViewById(R.id.orderWebsiteName);
            orderNo = itemView.findViewById(R.id.orderNo);
            orderDate = itemView.findViewById(R.id.orderDate);
            viewMore = itemView.findViewById(R.id.viewMore);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void populate(Order order){

        orderWebsiteName.setText(order.getStore().getName());
        orderNo.setText("Order No: " + "#" + order.getOrderCode());

        String date1 = order.getCreatedAt();
        String[] split = date1.split("T");
        String date = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date, dtf);
        String month_name = dtf2.format(ld);

        orderDate.setText(month_name);
        orderImage.setImageResource(R.drawable.ic_personal_shopper);

        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("orderCode", order.getOrderCode());
                bundle.putString("id", String.valueOf(order.getId()));
                bundle.putString("type" , "ps");


                ViewOrderPersonalShop viewOrderPersonalShop = new ViewOrderPersonalShop();
                viewOrderPersonalShop.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, viewOrderPersonalShop, null)
                        .addToBackStack(null).commit();

            }
        });
        }
    }


    public class SecondListViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImage;
        TextView orderWebsiteName, orderNo, orderDate;
        LinearLayout viewMore;

        public SecondListViewHolder(View itemView){
            super(itemView);

            orderImage = itemView.findViewById(R.id.singleOrderImage);
            orderWebsiteName = itemView.findViewById(R.id.orderWebsiteName);
            orderNo = itemView.findViewById(R.id.orderNo);
            orderDate = itemView.findViewById(R.id.orderDate);
            viewMore = itemView.findViewById(R.id.viewMore);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void populate(IncomingPkg incomingPkg){

            orderWebsiteName.setText("Incoming Package Id #"+incomingPkg.getId());
            orderNo.setVisibility(View.GONE);

            String date1 = incomingPkg.getCreatedAt();
            String[] split = date1.split("T");
            String date = split[0];

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
            LocalDate ld = LocalDate.parse(date, dtf);
            String month_name = dtf2.format(ld);

            orderDate.setText(month_name);
            orderImage.setImageResource(R.drawable.ic_self_shopper);

            viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();

                    bundle.putString("orderCode", String.valueOf(incomingPkg.getOrderCode()));
                    bundle.putString("id", String.valueOf(incomingPkg.getId()));
                    bundle.putString("date", incomingPkg.getCreatedAt());
                    bundle.putString("type" , "ss");

                    ViewOrderPersonalShop viewOrderPersonalShop = new ViewOrderPersonalShop();
                    viewOrderPersonalShop.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, viewOrderPersonalShop, null)
                            .addToBackStack(null).commit();

                }
            });
        }
    }
}
