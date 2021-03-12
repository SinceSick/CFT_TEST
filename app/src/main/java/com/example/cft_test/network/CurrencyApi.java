package com.example.cft_test.network;

import com.example.cft_test.network.pojo.CurrencyResponse;


import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyApi {
    @GET("/daily_json.js")
    public Call<CurrencyResponse> getCurrency();
}
