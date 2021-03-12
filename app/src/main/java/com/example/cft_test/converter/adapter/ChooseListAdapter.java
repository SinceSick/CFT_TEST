package com.example.cft_test.converter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cft_test.R;
import com.example.cft_test.main_activity.pojo.ConverterItem;

import java.util.List;

public class ChooseListAdapter extends RecyclerView.Adapter<ChooseListAdapter.ChooseViewHolder> {
    private final Context context;
    private List<ConverterItem> currencyItems;
    private final ChooseListener chooseListener;

    public ChooseListAdapter(Context context,List<ConverterItem> currencyItems, ChooseListener chooseListener) {
        this.context = context;
        this.currencyItems = currencyItems;
        this.chooseListener = chooseListener;
    }

    @NonNull
    @Override
    public ChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.converter_item_layout, parent, false);
        return new ChooseViewHolder(v,chooseListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChooseViewHolder holder, int position) {
        holder.abbrTV.setText(currencyItems.get(position).getAbbr());
        holder.nameTV.setText(currencyItems.get(position).getName());
        holder.valueTV.setText(currencyItems.get(position).getValue() + context.getResources().getString(R.string.rub));
    }

    @Override
    public int getItemCount() {
        return currencyItems.size();
    }

    public void updateList(List<ConverterItem> list){
        this.currencyItems = list;
        notifyDataSetChanged();
    }

    public List<ConverterItem> getCurrencyItems() {
        return currencyItems;
    }

    public static class ChooseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ChooseListener chooseListener;
        private final TextView abbrTV;
        private final TextView nameTV;
        private final TextView valueTV;

        public ChooseViewHolder(@NonNull View itemView, ChooseListener chooseListener) {
            super(itemView);
            this.chooseListener = chooseListener;
            abbrTV = itemView.findViewById(R.id.char_code_tv);
            nameTV = itemView.findViewById(R.id.name_tv);
            valueTV = itemView.findViewById(R.id.value_tv);
            CardView cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            chooseListener.onCurrencyClick(getAdapterPosition());
        }
    }

    public interface ChooseListener {
        void onCurrencyClick(int position);
    }
}
