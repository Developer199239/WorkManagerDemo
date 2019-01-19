package com.example.jahid.workmanager;

import android.arch.persistence.room.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataBaseManager {

    private DatabaseHelper helper;
    private SQLiteDatabase database;

    public DataBaseManager(Context context) {
        helper = new DatabaseHelper(context);

    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    // add
    public boolean addData(String timestamp, String tag) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_TIME_STAMP, timestamp);
        contentValues.put(DatabaseHelper.COL_TAG, tag);
        long inserted = database.insert(DatabaseHelper.TABLE_DATA_LIST, null, contentValues);
        database.close();
        if (inserted > 0)
            return true;
        else
            return false;
    }


    public ArrayList<DataModel> getDataList() {
        this.open();
        ArrayList<DataModel> dataModels = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_DATA_LIST;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TIME_STAMP));
                String tag = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TAG));

                DataModel row = new DataModel(name, tag);
                dataModels.add(row);
                cursor.moveToNext();
            }
        }
        this.close();
        return dataModels;
    }

}
