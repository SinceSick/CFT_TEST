package com.example.cft_test.main_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.example.cft_test.R;
import com.example.cft_test.converter.ConverterFragment;
import com.example.cft_test.currency_list.CurrencyListFragment;
import com.example.cft_test.database.MyDataBase;
import com.example.cft_test.main_activity.adapter.TabsAdapter;
import com.example.cft_test.main_activity.pojo.ConverterItem;
import com.example.cft_test.main_activity.pojo.CurrencyItem;
import com.example.cft_test.network.NetworkService;
import com.example.cft_test.network.pojo.Currency;
import com.example.cft_test.network.pojo.CurrencyResponse;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences preferences;
    private List<CurrencyItem> currencyItems;
    private ArrayList<ConverterItem> converterItems;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyItems = new ArrayList<>();
        converterItems = new ArrayList<>();

        preferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        if (preferences.contains("last_update")) {
            if (isTime(preferences.getLong("last_update", 0))) {
                getCurrencyResponse();
                log("start with response time");
            } else {
                getFromDB();
                log("start with DB");
            }
        } else {
            log("start with response");
            getCurrencyResponse();
        }

        initToolBar();


    }

    private boolean isTime(Long last) {
        log("last = " + last/1000 + "  now = " + Calendar.getInstance().getTimeInMillis()/1000);
        return (Calendar.getInstance().getTimeInMillis() - last) / 1000 >= 1000;
    }

    private void getCurrencyResponse() {
        NetworkService.getInstance()
                .getCurrencyApi()
                .getCurrency()
                .enqueue(new Callback<CurrencyResponse>() {
                    final Handler handler = new Handler(Looper.getMainLooper());
                    @Override
                    public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                        if (response.isSuccessful()) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    CurrencyResponse currencyResponse = response.body();
                                    initCurrencyItemArray(currencyResponse);
                                    saveInDB();
                                    setLastUpdate();
                                    initTabs();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    @SuppressLint("DefaultLocale")
    private void initCurrencyItemArray(CurrencyResponse currencyResponse) {
        currencyItems = new ArrayList<>();
        converterItems = new ArrayList<>();

        Field[] fields = currencyResponse.getValute().getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                f.setAccessible(true);
                Currency c = (Currency) f.get(currencyResponse.getValute());
                String abbr = c.getCharCode();
                String name = c.getName();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                Date date = oldFormat.parse(currencyResponse.getDate());
                String lastUpdate = df.format(date);

                Float dFloat = Float.parseFloat(c.getValue()) - Float.parseFloat(c.getPrevious());
                String delta = String.format("%.4f", dFloat);
                delta = delta.replace(',', '.');

                float valueFloat = Float.parseFloat(c.getValue()) / Integer.parseInt(c.getNominal());
                String value = String.format("%.2f", valueFloat);
                value = value.replace(',', '.');
                value = " " + value;

                float course = 1 / valueFloat;

                currencyItems.add(new CurrencyItem(abbr, name, value, delta, lastUpdate, course));
                converterItems.add(new ConverterItem(abbr,value,name,course));

            } catch (IllegalAccessException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLastUpdate() {
        long mils = Calendar.getInstance().getTimeInMillis();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("last_update", mils);
        editor.apply();
    }

    private void saveInDB() {
        MyDataBase db = Room.databaseBuilder(this,
                MyDataBase.class, "my_database")
                .allowMainThreadQueries()
                .build();
        if(preferences.contains("last_update")){
            db.currencyItemDAO().updateAll(currencyItems);

        } else {
            db.currencyItemDAO().insertAll(currencyItems);
        }
        db.close();
    }

    private void getFromDB() {
        currencyItems = new ArrayList<>();
        converterItems = new ArrayList<>();

        MyDataBase db = Room.databaseBuilder(this,
                MyDataBase.class, "my_database")
                .allowMainThreadQueries()
                .build();
        currencyItems = db.currencyItemDAO().getAll();
        db.close();

        for(CurrencyItem item: currencyItems){
            converterItems.add(new ConverterItem(item.getAbbr(),item.getValue(),item.getName(),item.getCourse()));
        }

        initTabs();
    }

    private void initTabs(){

        updateAdapter();

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(), fragments);
        adapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void updateAdapter(){
        fragments = new ArrayList<>();
        fragments.add(CurrencyListFragment.getInstance(currencyItems));
        fragments.add(ConverterFragment.getInstance(converterItems));
    }

    private void initToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.update){
                    getCurrencyResponse();
                }
                return false;
            }
        });
    }

    private void log(String message) {
        Log.d("MyLog", message);
    }
}