package com.example.cft_test.currency_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cft_test.R;
import com.example.cft_test.currency_list.adapter.CurrencyListAdapter;
import com.example.cft_test.main_activity.pojo.CurrencyItem;

import java.util.List;

public class CurrencyListFragment extends Fragment {

    private View view;
    private List<CurrencyItem> currencyItems;

    public static CurrencyListFragment getInstance(List<CurrencyItem> currencyItems) {
        CurrencyListFragment fragment = new CurrencyListFragment();
        fragment.setCurrencyItems(currencyItems);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.currency_list_layout, container, false);
        setRetainInstance(true);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.currency_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        CurrencyListAdapter currencyListAdapter = new CurrencyListAdapter(getContext(), this.currencyItems);
        recyclerView.setAdapter(currencyListAdapter);

    }

    public void setCurrencyItems(List<CurrencyItem> currencyItems) {
        this.currencyItems = currencyItems;
    }
}
