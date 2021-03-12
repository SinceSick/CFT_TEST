package com.example.cft_test.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyResponse {
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("PreviousDate")
    @Expose
    private String previousDate;
    @SerializedName("PreviousURL")
    @Expose
    private String previousURL;
    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("Valute")
    @Expose
    private Valute valute;

    public String getDate() {
        return date;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public String getPreviousURL() {
        return previousURL;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Valute getValute() {
        return valute;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
    }

    public void setPreviousURL(String previousURL) {
        this.previousURL = previousURL;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setValute(Valute valute) {
        this.valute = valute;
    }
}
