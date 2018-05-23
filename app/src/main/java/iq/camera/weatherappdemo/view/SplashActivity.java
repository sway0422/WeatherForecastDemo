package iq.camera.weatherappdemo.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import iq.camera.weatherappdemo.R;
import iq.camera.weatherappdemo.utils.PermissionManager;

public class SplashActivity extends AppCompatActivity {

    public static final String IS_PERMISSION_GRANTED = "PERMISSION_GRANTED";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PermissionManager.permissionsGranted(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    goToMainActivity();
                } else {
                    goToPermissionActivity();
                }
            }
        }, 1000);
    }


    private void goToPermissionActivity() {
        Intent intent = new Intent(this, PermissionActivity.class);
        startActivity(intent);
        finish();

    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
