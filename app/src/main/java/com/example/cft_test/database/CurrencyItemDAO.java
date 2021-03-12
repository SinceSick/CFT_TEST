package com.example.cft_test.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cft_test.main_activity.pojo.CurrencyItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CurrencyItemDAO {
    @Query("SELECT * FROM currency_items")
    List<CurrencyItem> getAll();

    @Query("SELECT * FROM currency_items WHERE 'id' = :id")
    CurrencyItem getById(long id);

    @Insert
    void insert(CurrencyItem currencyItem);

    @Insert
    void insertAll(List<CurrencyItem> currencyItems );

    @Update
    void updateAll(List<CurrencyItem> currencyItems);

    @Delete
    void delete(CurrencyItem currencyItem);
}
