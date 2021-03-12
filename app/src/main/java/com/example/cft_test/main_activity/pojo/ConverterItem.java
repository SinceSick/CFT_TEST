package com.example.cft_test.main_activity.pojo;

import android.os.Parcelable;

public class ConverterItem {
    private String abbr;
    private String value;
    private String name;
    private float course;

    public ConverterItem(String abbr, String value, String name, float course) {
        this.abbr = abbr;
        this.value = value;
        this.name = name;
        this.course = course;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public float getCourse() {
        return course;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
