package iq.camera.weatherappdemo.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import iq.camera.weatherappdemo.R;
import iq.camera.weatherappdemo.model.DayItem;
import iq.camera.weatherappdemo.utils.Util;

public class DayItemViewModel extends BaseObservable {

    private DayItem day;
    private Context context;

    public DayItemViewModel(DayItem day, Context context) {
        this.day = day;
        this.context = context;
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String getUnitType() {

        return getSharedPreferences().getString(
                context.getString(R.string.pref_units_key),
                context.getString(R.string.pref_units_metric));

    }


    public void setDay(DayItem day) {
        this.day = day;
        notifyChange();
    }


    public Calendar getDate() {
        return day.getDate();
    }

    public String getDescription() {
        return day.getWeather().weatherDescription;
    }

    public String getTempMax() {
        if (getUnitType().equals(context.getString(R.string.pref_units_metric))){

            return String.format("%.0f", Util.convertFahrenheitToCelcius(day.main.tempMax)).concat("\u00B0");
        } else {
            return String.format("%.0f", day.main.tempMax).concat("\u00B0");
        }

    }

    public String getTempMin() {
        if (getUnitType().equals(context.getString(R.string.pref_units_metric))){

            return String.format("%.0f", Util.convertFahrenheitToCelcius(day.main.tempMin)).concat("\u00B0");
        } else {
            return String.format("%.0f", day.main.tempMin).concat("\u00B0");
        }

    }

    public String getDayOfWeek() {
        return day.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    public String getIconUrl() {

        return "http://openweathermap.org/img/w/" + day.getWeather().iconCode + ".png";

    }

}
