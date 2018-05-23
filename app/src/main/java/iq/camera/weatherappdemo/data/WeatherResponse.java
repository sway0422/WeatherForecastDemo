package iq.camera.weatherappdemo.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import iq.camera.weatherappdemo.model.DayItem;

public class WeatherResponse {

    @SerializedName("cod") public String cod;

    @SerializedName("message") public double message;

    @SerializedName("cnt") public int count;

    @SerializedName("list") private List<DayItem> weatherList;

    public List<DayItem> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<DayItem> dayItems) {
        this.weatherList = dayItems;
    }

}
