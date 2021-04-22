package com.andriod.lesson4;

import com.andriod.lesson4.model.OpenWeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherManager {
    private final String UNITS = "metric";

    private Retrofit retrofit;
    private OpenWeather openWeather;

    public WeatherManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeather = retrofit.create(OpenWeather.class);
    }

    public void getWeather(String city, Listener listener) {
        openWeather.getWeather(city, BuildConfig.WEATHER_API_KEY, UNITS)
                .enqueue(new Callback<OpenWeatherData>() {
                    @Override
                    public void onResponse(Call<OpenWeatherData> call, Response<OpenWeatherData> response) {
                        OpenWeatherData data = response.body();
                        if (data != null && listener != null) listener.onReady(data);

                    }

                    @Override
                    public void onFailure(Call<OpenWeatherData> call, Throwable t) {

                    }
                });
    }

    public interface Listener {
        void onReady(OpenWeatherData data);
    }
}
