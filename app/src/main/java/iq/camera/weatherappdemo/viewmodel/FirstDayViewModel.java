package iq.camera.weatherappdemo.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Locale;
import java.util.Observable;

import iq.camera.weatherappdemo.R;
import iq.camera.weatherappdemo.WeatherApplication;
import iq.camera.weatherappdemo.model.DayItem;
import iq.camera.weatherappdemo.utils.Util;

public class FirstDayViewModel extends Observable {

    private DayItem day;
    private Context context;

    public FirstDayViewModel(DayItem day, Context context) {
        this.day = day;
        this.context = context;
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void onToggleBtnClick(View view){
        Log.e("FirstDayVM", "omToggleBtnClick called");
        if (view instanceof ToggleButton) {
            Log.e("FirstDayVM","Toogle button");
            ToggleButton toggleButton = (ToggleButton)view;
            updateUnitType(toggleButton.isChecked());
            setChanged();
            notifyObservers(view);
        }

    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String getUnitType() {

        return getSharedPreferences().getString(
                context.getString(R.string.pref_units_key),
                context.getString(R.string.pref_units_metric));

    }

    private void updateUnitType(boolean checkable){
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        if (checkable) {
            editor.putString(context.getString(R.string.pref_units_key), context.getString(R.string.pref_units_metric));
        } else {
            editor.putString(context.getString(R.string.pref_units_key), context.getString(R.string.pref_units_imperial));
        }
        editor.apply();

    }

    public void setDay(DayItem day) {
        this.day = day;
        setChanged();
        notifyObservers();
    }

    public Calendar getDate() {
        return day.getDate();
    }

    public String getDescription() {
        return day.getWeather().weatherDescription;
    }

    public String getIconCode() {
        return day.getWeather().iconCode;
    }

    public String getCurrentTemp() {
        if (getUnitType().equals(context.getString(R.string.pref_units_metric))){

            return String.format("%.0f", Util.convertFahrenheitToCelcius(day.main.currentTemp)).concat("\u00B0");
        } else {
            return String.format("%.0f", day.main.currentTemp).concat("\u00B0");
        }
    }

    public String getDayOfWeek() {
        return day.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    public String getIconUrl() {

        return "http://openweathermap.org/img/w/" + day.getWeather().iconCode + ".png";

    }


}
