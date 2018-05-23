package iq.camera.weatherappdemo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {


    @SerializedName("id") public String id;
    @SerializedName("main") public String title;
    @SerializedName("description") public String weatherDescription;
    @SerializedName("icon") public String iconCode;



}
