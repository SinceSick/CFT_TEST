package com.example.cft_test.currency_list.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cft_test.R;
import com.example.cft_test.main_activity.pojo.CurrencyItem;

import java.util.List;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {

    private final Context context;
    private final List<CurrencyItem> currencyItems;


    public CurrencyListAdapter(Context context,List<CurrencyItem> currencyItems) {
        this.context = context;
        this.currencyItems = currencyItems;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item_layout,parent,false);
        return new CurrencyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.abbrTV.setText(currencyItems.get(position).getAbbr());
        holder.valueTV.setText(currencyItems.get(position).getValue() + context.getResources().getString(R.string.rub));
        holder.nameTV.setText(currencyItems.get(position).getName());
        holder.deltaTV.setText(currencyItems.get(position).getDelta());
        holder.lastUpdateTV.setText(currencyItems.get(position).getLastUpdate());
        holder.deltaIV.setImageResource(currencyItems.get(position).getDeltaImage());
    }

    @Override
    public int getItemCount() {
        return currencyItems.size();
    }

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private final TextView abbrTV;
        private final TextView valueTV;
        private final TextView nameTV;
        private final TextView deltaTV;
        private final TextView lastUpdateTV;
        private final ImageView deltaIV;


        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            abbrTV = itemView.findViewById(R.id.char_code_tv);
            valueTV = itemView.findViewById(R.id.currency_value_tv);
            nameTV = itemView.findViewById(R.id.currency_name_tv);
            deltaTV = itemView.findViewById(R.id.currency_delta_tv);
            lastUpdateTV = itemView.findViewById(R.id.last_date_tv);
            deltaIV = itemView.findViewById(R.id.currency_delta_iv);
        }
    }
}
