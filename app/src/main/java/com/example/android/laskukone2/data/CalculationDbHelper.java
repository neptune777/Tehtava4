package com.example.android.laskukone2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class CalculationDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "calculationlist.db";
    private static final int DATABASE_VERSION = 1;


    public CalculationDbHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_CALCULATIONLIST_TABLE = "CREATE TABLE " + CalculationContract.CalculationEntry.TABLE_NAME + " (" +
                CalculationContract.CalculationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CalculationContract.CalculationEntry.COLUMN_CALCULATION + " TEXT NOT NULL " +

                "); ";



        sqLiteDatabase.execSQL(SQL_CREATE_CALCULATIONLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CalculationContract.CalculationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
