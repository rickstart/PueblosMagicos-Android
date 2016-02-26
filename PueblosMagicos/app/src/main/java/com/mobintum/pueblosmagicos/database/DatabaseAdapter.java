package com.mobintum.pueblosmagicos.database;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

import com.mobintum.pueblosmagicos.application.AppController;

/**
 * Created by Rick on 26/02/16.
 */
public class DatabaseAdapter {

    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;

    public static boolean openDB(){
        if(mDbHelper != null)
            mDbHelper.close();
        mDbHelper = new DatabaseHelper(AppController.getInstance().getApplicationContext());

        try {
            mDb = mDbHelper.getWritableDatabase();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static SQLiteDatabase getDB(){

        if(mDb == null)
            openDB();

        return mDb;

    }

}



