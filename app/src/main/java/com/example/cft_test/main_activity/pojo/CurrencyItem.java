package com.example.cft_test.main_activity.pojo;

import android.graphics.Color;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cft_test.R;

@Entity(tableName = "currency_items")
public class CurrencyItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String abbr;
    private String name;
    private String value;
    private String delta;
    private String lastUpdate;
    private int deltaImage;
    private float course;

    public CurrencyItem(String abbr, String name, String value, String delta, String lastUpdate, float course) {
        this.abbr = abbr;
        this.name = name;
        this.value = value;
        this.delta = delta;
        this.lastUpdate = lastUpdate;
        this.course = course;

        if(Double.parseDouble(delta) >= 0){
            this.deltaImage = R.drawable.ic_rise;
        } else {
            this.deltaImage = R.drawable.ic_down;
        }
    }

    public String getAbbr() {
        return abbr;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDelta() {
        return delta;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public int getDeltaImage() {
        return deltaImage;
    }

    public int getId() {
        return id;
    }

    public float getCourse() {
        return course;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeltaImage(int deltaImage) {
        this.deltaImage = deltaImage;
    }

}
