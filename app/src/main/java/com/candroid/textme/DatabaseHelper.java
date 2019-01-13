package com.candroid.textme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String NAME = "Main.db";

    public DatabaseHelper(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataContract.CREATE_MESSAGE_TABLE_STATEMENT);
        db.execSQL(DataContract.LocationData.CREATE_LOCATION_TABLE_STATEMENT);
        db.execSQL(DataContract.CallLogContract.CREATE_CALL_LOG_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataContract.DROP_MESSAGE_TABLE_STATEMENT);
        db.execSQL(DataContract.LocationData.DROP_LOCATION_TABLE_STATEMENT);
        db.execSQL(DataContract.CallLogContract.DROP_CALL_LOG_TABLE_STATEMENT);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}