package com.example.jahid.workmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static  final int DATABASE_VERSION=1;
    static  final String DATABASE_NAME="workManager";

    // table property
    static  final String TABLE_DATA_LIST="data";
    static final String COL_ID="id";
    static final String COL_TIME_STAMP="timestamp";
    static final String COL_TAG="tag";

    //crate table
    static final String CREATE_VIDEO_PLAY_LIST=" CREATE TABLE " + TABLE_DATA_LIST + " ( " + COL_ID +" INTEGER PRIMARY KEY," + COL_TAG +" TEXT,"+ COL_TIME_STAMP +" TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VIDEO_PLAY_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
