package com.candroid.textme.ui.activities.permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.candroid.textme.ui.activities.BackPressedActivity;

public class LocationActivity extends BackPressedActivity {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 33;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            onBackPressed();
        }else{
            requestPermissions();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    public void requestPermissions(){
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }else{
            onBackPressed();
        }
    }
}