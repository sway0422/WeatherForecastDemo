package iq.camera.weatherappdemo.data;



import android.location.Location;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface WeatherService {

    @GET
    Observable<WeatherResponse> fetchWeather(@Url String url);

}
