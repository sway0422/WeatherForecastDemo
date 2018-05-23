package iq.camera.weatherappdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager {


    public static final int FINE_LOCATION = 0;


    public static boolean permissionsGranted(final Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean requestLocationPermissions(final Activity activity) {
        if (permissionsGranted(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        }

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION);
        return false;
    }
}
