package iq.camera.weatherappdemo.data;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.logging.Logger;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFactory {

    private final static String HTTP_BASE_URL = "http://api.openweathermap.org/";
    private final static String BASE_URL = "api.openweathermap.org";
    private final static String OWM_APIKEY = "b604225469b920d36ff0bebe9e85a788";

    private final static String OWM_VERSION = "2.5";
    private final static String OWM_DATA = "data";
    private final static String OWM_FORECAST = "forecast";
    private final static String OWM_DAILY = "daily";
    private final static String OWM_PARAM_LATITUDE = "lat";
    private final static String OWM_PARAM_LONGITUDE = "lon";
    private final static String OWM_PARAM_MODE = "mode";
    private final static String OWM_PARAM_UNITS = "units";
    private final static String OWM_PARAM_DAYCOUNT = "cnt";
    private final static String OWM_PARAM_APIKEY = "APPID";
    private final static String dataProtocol = "http";
    private final static String dataMode = "json";
    private final static String dataUnits = "imperial";
    private final static String dataDays = "10";

    public static WeatherService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HTTP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();



        return retrofit.create(WeatherService.class);

    }

    public static String generateURL(Location location) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(dataProtocol)
                .authority(BASE_URL)
                .appendPath(OWM_DATA)
                .appendPath(OWM_VERSION)
                .appendPath(OWM_FORECAST)
//                .appendPath(OWM_DAILY)
                .appendQueryParameter(OWM_PARAM_LATITUDE, Double.toString(location.getLatitude()))
                .appendQueryParameter(OWM_PARAM_LONGITUDE, Double.toString(location.getLongitude()))
                .appendQueryParameter(OWM_PARAM_APIKEY, OWM_APIKEY)
                .appendQueryParameter(OWM_PARAM_DAYCOUNT, dataDays)
                .appendQueryParameter(OWM_PARAM_MODE, dataMode)
                .appendQueryParameter(OWM_PARAM_UNITS, dataUnits);

        Log.d("WeatherFactory", "" + builder.build().toString());

        return builder.build().toString();
    }


}
