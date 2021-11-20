package com.peceinfotech.shoppre.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.viewHolder> {
    List<Order> list;
    Context context;
    ArrayAdapter arrayAdapterDropdown;


    String[] optionSelection = {"Select an option", "250", "500", "750", "1000", "1000+", "Cancel all if the cost is increased"};

    final ArrayList<String> optionSelectList = new ArrayList<>(Arrays.asList(optionSelection));

    public OrderSummaryAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_summary_single_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.websiteName.setText(list.get(position).getStore().getName());

        if (position == getItemCount() - 1) {

            holder.view1.setVisibility(View.GONE);

        }
//        arrayAdapterDropdown = new ArrayAdapter(context, R.layout.option_selection_layout, optionSelection);
//        holder.dropdown.setAdapter(arrayAdapterDropdown);

        final ArrayAdapter<String> dropdownArrayAdapter = new ArrayAdapter<String>(context, R.layout.option_selection_layout, optionSelectList) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                TextView selectOptionTextView = (TextView) view;

                if (position == 0) {
                    selectOptionTextView.setVisibility(View.INVISIBLE);
                    selectOptionTextView.setTextColor(Color.GRAY);
                } else {
                    selectOptionTextView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        dropdownArrayAdapter.setDropDownViewResource(R.layout.option_selection_layout);
        holder.dropdown.setAdapter(dropdownArrayAdapter);

        holder.dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = (String) parent.getItemAtPosition(position);

                if (position > 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        holder.selectOptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dropdown.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView websiteName;
        Spinner dropdown;
        LinearLayout selectOptionLayout;
        EditText charges, instruction;
        View view1;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            websiteName = itemView.findViewById(R.id.websiteName);
            view1 = itemView.findViewById(R.id.view1);
            charges = itemView.findViewById(R.id.charges);
            instruction = itemView.findViewById(R.id.instruction);
            dropdown = itemView.findViewById(R.id.dropdown);
            selectOptionLayout = itemView.findViewById(R.id.selectOptionLayout);

            charges.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
}
