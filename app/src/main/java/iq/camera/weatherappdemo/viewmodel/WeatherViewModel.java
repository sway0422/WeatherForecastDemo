package iq.camera.weatherappdemo.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.location.Location;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import iq.camera.weatherappdemo.WeatherApplication;
import iq.camera.weatherappdemo.data.WeatherFactory;
import iq.camera.weatherappdemo.data.WeatherResponse;
import iq.camera.weatherappdemo.data.WeatherService;
import iq.camera.weatherappdemo.model.DayItem;

public class WeatherViewModel extends Observable {

    public ObservableInt weatherRecycler;
    private List<DayItem> weatherList;
    private Context context;
    private Location location;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WeatherViewModel(@NonNull Context context) {

        this.weatherRecycler = new ObservableInt(View.GONE);
        this.context = context;
        this.weatherList = new ArrayList<>();


    }


    public void setLocation(Location location){
        this.location = location;
    }

    public void onRefreshLoad(View view){

        fetchWeatherList();

    }

    public void fetchWeatherList(){
        WeatherApplication weatherApplication = WeatherApplication.create(context);
        WeatherService weatherService = weatherApplication.getWeatherService();

        Disposable disposable = weatherService.fetchWeather(WeatherFactory.generateURL(location))
                .subscribeOn(weatherApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResponse>() {
                    @Override public void accept(WeatherResponse weatherResponse) throws Exception {
                       changeWeatherDataSet(weatherResponse.getWeatherList());
                       weatherRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {

                    @Override public void accept(Throwable throwable) throws Exception {
                        Log.e("ViewModel", "Failed " + throwable.toString());
                        weatherRecycler.set(View.GONE);
                    }
                });

        compositeDisposable.add(disposable);


    }


    private void changeWeatherDataSet(List<DayItem> weatherList) {
        this.weatherList.addAll(weatherList);
        setChanged();
        notifyObservers();
    }

    public List<DayItem> getWeatherList() {
        return weatherList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}
