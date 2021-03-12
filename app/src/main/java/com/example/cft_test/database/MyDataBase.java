package com.example.cft_test.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cft_test.main_activity.pojo.CurrencyItem;

@Database(entities = {CurrencyItem.class}, version = 2, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract CurrencyItemDAO currencyItemDAO();
}
