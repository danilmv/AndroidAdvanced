package com.andriod.lesson4;

import com.andriod.lesson4.model.OpenWeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<OpenWeatherData> getWeather(@Query("q") String city, @Query("appid") String appId, @Query("units")String units);
}
