package com.example.android.laskukone2.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(CalculationContract.CalculationEntry.COLUMN_CALCULATION, "3 + 4 = 7");
        list.add(cv);

        cv = new ContentValues();
        cv.put(CalculationContract.CalculationEntry.COLUMN_CALCULATION, "5 * 4 = 20");
        list.add(cv);

        cv = new ContentValues();
        cv.put(CalculationContract.CalculationEntry.COLUMN_CALCULATION, "10 / 4 = 2.5");
        list.add(cv);



        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (CalculationContract.CalculationEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(CalculationContract.CalculationEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}