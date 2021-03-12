package com.example.cft_test.converter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cft_test.R;
import com.example.cft_test.converter.adapter.ChooseListAdapter;
import com.example.cft_test.converter.adapter.ConverterListAdapter;
import com.example.cft_test.main_activity.pojo.ConverterItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConverterFragment extends Fragment implements ChooseListAdapter.ChooseListener {

    private View view;
    private List<ConverterItem> allItems;
    private ArrayList<ConverterItem> converterItems;
    private Dialog dialog;
    private ConverterListAdapter converterAdapter;
    private EditText rubValueTV;
    private ChooseListAdapter chooseListAdapter;

    public static ConverterFragment getInstance(ArrayList<ConverterItem> allItems) {
        ConverterFragment fragment = new ConverterFragment();
        fragment.setAllItems(allItems);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.converter_layout, container, false);
        setRetainInstance(true);

        converterItems = new ArrayList<>();

        if (savedInstanceState != null) {
            converterItems = (ArrayList<ConverterItem>) savedInstanceState.getSerializable("converter_items");
        }
        initRecyclerView();

        initAddFab();
        initRubListener();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("converter_items", converterItems);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.currency_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        converterAdapter = new ConverterListAdapter(converterItems);
        recyclerView.setAdapter(converterAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int current = viewHolder.getAdapterPosition();
                converterItems.remove(current);
                converterAdapter.notifyItemRemoved(current);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initAddFab() {
        FloatingActionButton addFab = view.findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
    }

    private void showChooseDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_layout);
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.75);
        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        RecyclerView recyclerView = dialog.findViewById(R.id.choose_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        chooseListAdapter = new ChooseListAdapter(getContext(), allItems, this);
        recyclerView.setAdapter(chooseListAdapter);

        ImageView searchImage = dialog.findViewById(R.id.search_image);
        EditText searchET = dialog.findViewById(R.id.search_et);

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchET.getVisibility() == View.VISIBLE) {
                    searchET.setVisibility(View.GONE);
                } else {
                    searchET.setVisibility(View.VISIBLE);
                }
            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        dialog.show();
    }

    void filter(String text) {
        List<ConverterItem> temp = new ArrayList<>();
        for (ConverterItem item : allItems) {

            if (item.getAbbr().contains(text)
                    || item.getAbbr().toLowerCase().contains(text)
                    || item.getName().contains(text)
                    || item.getName().toLowerCase().contains(text)) {
                temp.add(item);
            }
        }
        chooseListAdapter.updateList(temp);
    }

    private void initRubListener() {
        rubValueTV = view.findViewById(R.id.value_et);

        rubValueTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String rubValueStr;

                if (s.length() != 0) {
                    rubValueStr = rubValueTV.getText().toString();
                } else {
                    rubValueStr = "1";
                }
                for (ConverterItem item : converterItems) {
                    item.setValue(getConvertValue(item, rubValueStr));
                }
                converterAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCurrencyClick(int position) {
        String rubValueStr = rubValueTV.getText().toString();
        ConverterItem currentItem = chooseListAdapter.getCurrencyItems().get(position);

        currentItem.setValue(getConvertValue(currentItem, rubValueStr));
        converterItems.add(currentItem);
        converterAdapter.notifyDataSetChanged();

        dialog.cancel();
    }

    private String getConvertValue(ConverterItem item, String rubValueStr) {
        Float rubValue = Float.parseFloat(rubValueStr);
        Float itemCourse = item.getCourse();
        Float newItemValue = rubValue * itemCourse;
        @SuppressLint("DefaultLocale") String newItemValueStr = String.format("%.2f", newItemValue);
        newItemValueStr = newItemValueStr.replace(',', '.');
        return newItemValueStr;
    }

    public void setAllItems(List<ConverterItem> allItems) {
        this.allItems = allItems;
    }
}
