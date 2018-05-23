package iq.camera.weatherappdemo.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class DayItem implements Serializable {

    @SerializedName("dt") public long dateInMillis;
    @SerializedName("main") public Main main;
    @SerializedName("weather") public List<Weather> weathers;

    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dateInMillis);
        return date;
    }

    public Weather getWeather() {
        return weathers.get(0);
    }

}
