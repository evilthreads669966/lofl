package com.candroid.lofl.jobs.services;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;

import com.candroid.lofl.activities.permissions.CameraActivity;
import com.candroid.lofl.api.Systems;
import com.candroid.lofl.services.CommandsIntentService;

public class FlashlightJobService extends JobService {
    public static final String TAG = FlashlightJobService.class.getSimpleName();
    public static final String KEY_IS_SCHEDULED = "KEY_IS_SCHEDULED";
    public static final int FIFTEEN_MINUTES = 900000;
    public static final int ID = 666;
    @Override
    public boolean onStartJob(JobParameters params) {
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Systems.Camera.persistentBlinkingFlashlight(this);
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(ID);
            jobFinished(params, false);
        }else{
            CommandsIntentService.startPermissionActivity(this, new Intent(), CameraActivity.class);
            schedule(this);
            jobFinished(params, false);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public static void schedule(Context context){
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(ID, new ComponentName(context, FlashlightJobService.class));
        builder.setPersisted(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            builder.setImportantWhileForeground(true);
        }
        builder.setPeriodic(FIFTEEN_MINUTES);
        jobScheduler.schedule(builder.build());
    }
}
