package com.mobintum.pueblosmagicos.database;

import android.database.sqlite.SQLiteDatabase;

import com.mobintum.pueblosmagicos.application.AppController;

/**
 * Created by Rick on 26/02/16.
 */
public class DatabaseAdapter {

    private static DatabaseHelper dBHelper;
    private static SQLiteDatabase db;

    public static boolean openDB(){
        if(dBHelper != null)
            dBHelper.close();
        dBHelper = new DatabaseHelper(AppController.getInstance().getApplicationContext());

        try {
            db = dBHelper.getWritableDatabase();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static  SQLiteDatabase getDB(){
        if(db!=null)
            openDB();

        return db;
    }


}
