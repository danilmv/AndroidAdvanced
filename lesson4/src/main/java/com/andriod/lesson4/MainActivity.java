package com.andriod.lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andriod.lesson4.model.History;
import com.andriod.lesson4.model.OpenWeatherData;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements WeatherManager.Listener {
    static final WeatherManager weatherManager = new WeatherManager();

    private TextView textTemperatureValue;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textCityName = findViewById(R.id.editCityValue);
        textTemperatureValue = findViewById(R.id.textTemperatureValue);
        imageView = findViewById(R.id.imageView);

        Button button = findViewById(R.id.button);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new HistoryFragment())
                .commit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherManager.getWeather(textCityName.getText().toString(), MainActivity.this);
            }
        });
    }

    @Override
    public void onReady(OpenWeatherData data) {
        textTemperatureValue.setText(String.format(Locale.getDefault(), "%.1f", data.getTemperature()));

        Picasso.get()
                .load(String.format("http://openweathermap.org/img/w/%s.png", data.getIcon()))
                .fit()
                .into(imageView);

        HistoryAdapter.getInstance().add(new History(data));
    }
}