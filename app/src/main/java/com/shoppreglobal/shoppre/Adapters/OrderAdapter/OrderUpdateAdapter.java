package com.shoppreglobal.shoppre.Adapters.OrderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.GetCommentsResponse;
import com.shoppreglobal.shoppre.R;

import java.util.List;

public class OrderUpdateAdapter extends RecyclerView.Adapter<OrderUpdateAdapter.viewHolder> {

    List<GetCommentsResponse> list;
    Context context;

    public OrderUpdateAdapter(List<GetCommentsResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_updates_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {




        if (list.get(position).getUser()!=null){
            holder.profileName.setText(list.get(position).getUser().getName());
        }
        if (list.get(position).getUser().getGroupId()==1){


            holder.profileName.setTextColor(context.getResources().getColor(R.color.text_red));
            holder.profileImage.setImageResource(R.drawable.shoppre_ic);

        }else {
            holder.profileImage.setImageResource(R.drawable.shoppre_ic);
            holder.profileName.setTextColor(context.getResources().getColor(R.color.text_blue));
        }
        holder.orderStatus.setText(list.get(position).getComments());
        holder.dateAndTime.setText(list.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        TextView profileName, orderStatus, dateAndTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage =itemView.findViewById(R.id.profileImage);
            profileName = itemView.findViewById(R.id.profileName);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            dateAndTime = itemView.findViewById(R.id.dateAndTime);

        }
    }
}
