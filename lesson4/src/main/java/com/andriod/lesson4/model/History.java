package com.andriod.lesson4.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;


@Entity()
public class History {
    private static final String CELSIUS = "\u2103";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    private String city;
    private double temperature;
    private String icon;

    public History() {
    }

    public History(OpenWeatherData data) {
        city = data.getName();
        temperature = data.getTemperature();
        icon = data.getIcon();
    }

    public History(String city, double temperature, String icon) {
        this.city = city;
        this.temperature = temperature;
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getFormattedTemperature() {
        return String.format(Locale.getDefault(), "%.1f%s", getTemperature(), CELSIUS);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int compareTo(History o) {
        return city.compareTo(o.getCity());
    }
}
