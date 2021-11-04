package com.peceinfotech.shoppre.Adapters.OrderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Models.Section;
import com.peceinfotech.shoppre.Models.SectionItem;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class ParentFinalOrderSummaryAdapter extends RecyclerView.Adapter<ParentFinalOrderSummaryAdapter.viewHolder> {

    List<Section> list;
    Context context;

    public ParentFinalOrderSummaryAdapter(List<Section> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.single_final_order_summary , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Section section = list.get(position);
        holder.websiteName.setText(section.getSectionName());
        holder.additional.setText("₹ "+String.valueOf(section.getAdditional()));
        holder.personal.setText("₹ "+String.valueOf(section.getPersonal()));
        List<SectionItem> list1 = section.getList();
        ChildFinalOrderSummaryAdapter adapter = new ChildFinalOrderSummaryAdapter(list1 , context);
        if( position == getItemCount() - 1 ){

            holder.view1.setVisibility(View.GONE);
        }
        holder.childRecycle.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView websiteName , personal , additional;
        RecyclerView childRecycle;
        View view1;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            childRecycle = itemView.findViewById(R.id.child);
            view1 = itemView.findViewById(R.id.view1);
            websiteName = itemView.findViewById(R.id.websiteName);
            personal = itemView.findViewById(R.id.personal);
            additional = itemView.findViewById(R.id.additional);
        }
    }
}
