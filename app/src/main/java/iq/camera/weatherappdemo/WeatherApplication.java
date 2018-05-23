package iq.camera.weatherappdemo;

import android.app.Application;
import android.content.Context;
import android.location.Location;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import iq.camera.weatherappdemo.data.WeatherFactory;
import iq.camera.weatherappdemo.data.WeatherService;

public class WeatherApplication extends Application {
    private WeatherService weatherService;
    private Scheduler scheduler;


    private static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

    public static WeatherApplication create(Context context) {
        return WeatherApplication.get(context);
    }


    public WeatherService getWeatherService() {
        if (weatherService == null) {
            weatherService = WeatherFactory.create();
        }

        return weatherService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
