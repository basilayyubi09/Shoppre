package com.peceinfotech.shoppre.Adapters.AccountAndWallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Models.RefferalDummyModel;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.viewHolder> {

    Context context;
    List<RefferalDummyModel> list ;

    public ReferralAdapter(Context context, List<RefferalDummyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.refer_single_layout, parent ,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.image.setImageResource(list.get(position).getImage());
        holder.mainText.setText(list.get(position).getMainText());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView date , mainText;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            date = itemView.findViewById(R.id.date);
            mainText = itemView.findViewById(R.id.mainText);
        }
    }
}
