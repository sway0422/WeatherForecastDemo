package iq.camera.weatherappdemo.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import iq.camera.weatherappdemo.R;
import iq.camera.weatherappdemo.utils.PermissionManager;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getSupportActionBar().hide();
        findViewById(R.id.confirm_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!PermissionManager.permissionsGranted(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionManager.requestLocationPermissions(this);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PermissionManager.FINE_LOCATION) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
