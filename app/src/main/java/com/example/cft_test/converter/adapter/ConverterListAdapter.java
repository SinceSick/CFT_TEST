package com.example.cft_test.converter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cft_test.R;
import com.example.cft_test.main_activity.pojo.ConverterItem;

import java.util.List;

public class ConverterListAdapter extends RecyclerView.Adapter<ConverterListAdapter.ConverterViewHolder> {
    private final List<ConverterItem> currencyItems;

    public ConverterListAdapter(List<ConverterItem> currencyItems) {
        this.currencyItems = currencyItems;
    }

    @NonNull
    @Override
    public ConverterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.converter_item_layout,parent,false);
        return new ConverterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConverterViewHolder holder, int position) {
        holder.abbrTV.setText(currencyItems.get(position).getAbbr());
        holder.nameTV.setText(currencyItems.get(position).getName());
        holder.valueTV.setText(currencyItems.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return currencyItems.size();
    }

    public static class ConverterViewHolder extends RecyclerView.ViewHolder {

        private final TextView abbrTV;
        private final TextView nameTV;
        private final TextView valueTV;

        public ConverterViewHolder(@NonNull View itemView) {
            super(itemView);
            abbrTV = itemView.findViewById(R.id.char_code_tv);
            nameTV = itemView.findViewById(R.id.name_tv);
            valueTV = itemView.findViewById(R.id.value_tv);

        }
    }
}
