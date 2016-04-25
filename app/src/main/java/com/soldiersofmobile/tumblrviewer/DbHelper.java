package com.soldiersofmobile.tumblrviewer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

/**
 * Created by madejs on 25.04.16.
 */
public class DbHelper extends SQLiteOpenHelper {

    @Inject
    public DbHelper(Context context) {
        super(context, "baza.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
