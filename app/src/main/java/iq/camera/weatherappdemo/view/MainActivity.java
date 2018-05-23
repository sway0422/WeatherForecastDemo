package iq.camera.weatherappdemo.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import iq.camera.weatherappdemo.R;
import iq.camera.weatherappdemo.databinding.MainActivityBinding;
import iq.camera.weatherappdemo.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity  implements Observer {


    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    public static int DEFAULT_RADIUS = 150;
    public static int MAX_DISTANCE = DEFAULT_RADIUS/2;
    public static String SINGLE_LOCATION_UPDATE_ACTION = "WEATHER_SINGLE_LOC_UPDATE";
    private WeatherViewModel weatherViewModel;
    private MainActivityBinding mainActivityBinding;

    private LocationManager mLocationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) { }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    private Intent updateIntent = new Intent(SINGLE_LOCATION_UPDATE_ACTION);
    private PendingIntent singleUpatePI;

    protected BroadcastReceiver singleUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(singleUpdateReceiver);

            String key = LocationManager.KEY_LOCATION_CHANGED;
            Location location = (Location) intent.getExtras().get(key);

            if (mLocationListener != null && location != null)
                mLocationListener.onLocationChanged(location);

            singleUpatePI = PendingIntent.getBroadcast(getApplication(), 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mLocationManager.removeUpdates(singleUpatePI);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupObserver(weatherViewModel);
        setupWeatherListView(mainActivityBinding.recyclerView, mainActivityBinding.tendayforecastSwipeRefreshLayout);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MAX_TIME,
//                MAX_DISTANCE, mLocationListener);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(false);
        }
        initializeData();
    }

    private void initDataBinding() {
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        weatherViewModel = new WeatherViewModel(this);
        mainActivityBinding.setMainViewModel(weatherViewModel);
    }

    private void setupWeatherListView(final RecyclerView weatherListView, SwipeRefreshLayout refreshLayout) {

        WeatherAdapter adapter = new WeatherAdapter();
        weatherListView.setAdapter(adapter);
        weatherListView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeData();
                weatherListView.refreshDrawableState();
            }
        });
        refreshLayout.setColorSchemeResources(R.color.grey);
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }


    private void initializeData(){

        weatherViewModel.setLocation(getLocation());
        weatherViewModel.fetchWeatherList();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        weatherViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.e("MainActivity", "update!!! ");
        if (observable instanceof WeatherViewModel) {
            //update adapter
            mainActivityBinding.tendayforecastSwipeRefreshLayout.setRefreshing(false);
            WeatherAdapter adapter = (WeatherAdapter) mainActivityBinding.recyclerView.getAdapter();
            WeatherViewModel weatherViewModel = (WeatherViewModel) observable;

            adapter.setWeatherList(weatherViewModel.getWeatherList());

        }


    }

    public Location getLocation() {
        Location bestResult = null;
        long minTime = System.currentTimeMillis();
        long maxTime = minTime - 86400000; // minimum time to check for refresh should be exactly one day ago
        float bestAccuracy = Float.MAX_VALUE;
        long bestTime = 0;

        try {
            List<String> matchingProviders = mLocationManager.getAllProviders();
            for (String provider : matchingProviders) {
                Location location = mLocationManager.getLastKnownLocation(provider);
                if (location != null) {
                    float accuracy = location.getAccuracy();
                    long time = location.getTime();

                    if ((time > minTime && accuracy < bestAccuracy)) {
                        bestResult = location;
                        bestAccuracy = accuracy;
                        bestTime = time;
                    } else if (time < minTime &&
                            bestAccuracy == Float.MAX_VALUE && time > bestTime) {
                        bestResult = location;
                        bestTime = time;
                    }
                }
            }

            if (mLocationListener != null &&
                    (bestTime < maxTime || bestAccuracy > MAX_DISTANCE)) {
                IntentFilter locIntentFilter = new IntentFilter(SINGLE_LOCATION_UPDATE_ACTION);
                getApplicationContext().registerReceiver(singleUpdateReceiver, locIntentFilter);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_LOW);
                singleUpatePI = PendingIntent.getBroadcast(getApplication(), 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mLocationManager.requestSingleUpdate(criteria, singleUpatePI);
            }
        }
        catch (SecurityException se) {
            Log.e(LOG_TAG, "User has not given sufficient permissions for geolocation");
        }

        return bestResult;
    }
}
