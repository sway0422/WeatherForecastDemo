package iq.camera.weatherappdemo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {

    @SerializedName("temp") public double currentTemp;
    @SerializedName("temp_max") public double tempMax;
    @SerializedName("temp_min") public double tempMin;
    @SerializedName("pressure") public double pressure;
    @SerializedName("humidity") public double humidity;

}
