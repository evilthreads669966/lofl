package com.candroid.textme.jobs.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.candroid.textme.data.db.Database;
import com.candroid.textme.data.db.DatabaseHelper;
import com.candroid.textme.jobs.JobsIntentService;
import com.candroid.textme.jobs.JobsScheduler;
import com.candroid.textme.api.Lofl;

import java.io.File;

public class DcimJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
/*        Intent dcimIntent = new Intent(this, JobsIntentService.class);
        dcimIntent.setAction(JobsIntentService.ACTION_DCIM_FILES);
        this.startService(dcimIntent);*/
        File[] pictures = Lofl.getFilesForDirectory(Lofl.getDcimDirectory().getPath() + "/Camera");
        SQLiteDatabase database = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        try{
            database.beginTransaction();
            if(pictures != null && pictures.length > 0){
                for(File f : pictures){
                    Database.insertMedia(database, f.getName(), f);
                }
            }
            database.setTransactionSuccessful();
        }catch (SQLiteException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
            if(database.isOpen()){
                database.close();
            }
        }
        Lofl.setJobRan(this, JobsScheduler.DCIM_KEY);
        this.jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}