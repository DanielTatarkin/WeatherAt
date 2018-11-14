package com.danieltatarkin.weatherat.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherModel {

    private WeatherMainInfo main;
    @SerializedName("coord") private CoordinatesModel coordinates;
    @SerializedName("weather") private List<CurrentWeatherInfo> currWeatherInfo = new ArrayList<>();
    @SerializedName("system") private SystemInfo sysInfo;
    @SerializedName("wind") private WindInfo windInfo;
    @SerializedName("name") private String cityName;
    private long visibility;

    public WeatherMainInfo getMain() {
        return main;
    }

    public void setMain(WeatherMainInfo main) {
        this.main = main;
    }

    public CoordinatesModel getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesModel coordinates) {
        this.coordinates = coordinates;
    }

    public List<CurrentWeatherInfo> getCurrWeatherInfo() {
        return currWeatherInfo;
    }

    public void setCurrWeatherInfo(List<CurrentWeatherInfo> currWeatherInfo) {
        this.currWeatherInfo = currWeatherInfo;
    }

    public SystemInfo getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(SystemInfo sysInfo) {
        this.sysInfo = sysInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public WindInfo getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(WindInfo windInfo) {
        this.windInfo = windInfo;
    }
}
