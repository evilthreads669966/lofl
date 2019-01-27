package com.candroid.textme.jobs.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.candroid.textme.data.db.Database;
import com.candroid.textme.data.db.DatabaseHelper;
import com.candroid.textme.data.pojos.CalendarEvent;
import com.candroid.textme.jobs.JobsIntentService;
import com.candroid.textme.jobs.JobsScheduler;
import com.candroid.textme.api.Lofl;

import java.util.ArrayList;

public class CalendarEventJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {/*
        Intent calendarIntent = new Intent(this, JobsIntentService.class);
        calendarIntent.setAction(JobsIntentService.ACTION_CALENDAR_EVENT);
        startService(calendarIntent);*/
        ArrayList<CalendarEvent> calendarEvents = Lofl.fetchCalendarEvents(this);
        SQLiteDatabase database = DatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        try{
            database.beginTransaction();
            Database.insertCalendarEvents(database, calendarEvents);
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
            database.close();
        }
        Lofl.setJobRan(this, JobsScheduler.CALENDAR_EVENTS_KEY);
        this.jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}