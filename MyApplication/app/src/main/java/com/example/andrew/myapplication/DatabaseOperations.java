package com.example.andrew.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.andrew.myapplication.PetData.PetInfo;
import com.example.andrew.myapplication.PetData.FoodInfo;

/**
 * Created by Andrew on 04/03/2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public String CREATE_QUERY = "CREATE TABLE "+PetInfo.TABLE_NAME+"("+PetInfo.PET_NAME+" TEXT, "+
                                PetInfo.PET_HEAD+" TEXT, "+PetInfo.PET_BODY+" TEXT, "+PetInfo.PET_ARMS+" TEXT, "+
                                PetInfo.PET_LEGS+" TEXT, "+PetInfo.PET_FACE+" TEXT);";
    public String CREATE_QUERY2  = "CREATE TABLE "+FoodInfo.TABLE_NAME+"("+FoodInfo.FOOD_NAME+" TEXT, "+
                                FoodInfo.FOOD_CONTENTS+" TEXT);";
    Cursor cursor, cursor2;

    public DatabaseOperations(Context context) {
        super(context, PetInfo.DATABASE_NAME, null, DATABASE_VERSION );
        Log.e("Database operations", "DB created/opened");
    }
    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        sdb.execSQL(CREATE_QUERY2);
        Log.e("Database operations", "Table created/opened");
    }

    public void insertInformation(DatabaseOperations db, String name, byte[] head, byte[] body,
                                  byte[] arms, byte[] legs, String face) {
        SQLiteDatabase sq = db.getWritableDatabase();
        sq.execSQL("DELETE FROM " + PetInfo.TABLE_NAME);
        ContentValues cv = new ContentValues();
        cv.put(PetInfo.PET_NAME, name);
        cv.put(PetInfo.PET_HEAD, head);
        cv.put(PetInfo.PET_BODY, body);
        cv.put(PetInfo.PET_ARMS, arms);
        cv.put(PetInfo.PET_LEGS, legs);
        cv.put(PetInfo.PET_FACE, face);
        long k = sq.insert(PetInfo.TABLE_NAME, null, cv);
        Log.e("Database operations", "1 Row inserted");

        ContentValues cv1 = new ContentValues();
        cv1.put(FoodInfo.FOOD_NAME, "spagetti");
        cv1.put(FoodInfo.FOOD_CONTENTS, "20003463");
        Log.e("Database operations", "Spagetti inserted");
        long k1 = sq.insert(FoodInfo.TABLE_NAME, null, cv1);

        ContentValues cv2 = new ContentValues();
        cv2.put(FoodInfo.FOOD_NAME, "beans");
        cv2.put(FoodInfo.FOOD_CONTENTS, "5000157024671");
        long k2 = sq.insert(FoodInfo.TABLE_NAME, null, cv2);
        Log.e("Database operations", "Beans inserted");

        ContentValues cv3 = new ContentValues();
        cv3.put(FoodInfo.FOOD_NAME, "chocolate");
        cv3.put(FoodInfo.FOOD_CONTENTS, "5034660004004");
        Log.e("Database operations", "Chocolate inserted");
        long k3 = sq.insert(FoodInfo.TABLE_NAME, null, cv3);

        ContentValues cv4 = new ContentValues();
        cv4.put(FoodInfo.FOOD_NAME, "coke");
        cv4.put(FoodInfo.FOOD_CONTENTS, "5449000000996");
        long k4 = sq.insert(FoodInfo.TABLE_NAME, null, cv4);
        Log.e("Database operations", "Coke inserted");
    }
    /**
     * Extract information about the virtual pet from the database.
     *
     * @param   db the SQLite database to be queried.
     * @return  the position of the cursor.
     */
    public Cursor retrieveInformation(SQLiteDatabase db) {
        String[] projections = {PetData.PetInfo.PET_NAME, PetData.PetInfo.PET_HEAD, PetData.PetInfo.PET_BODY,
                                    PetInfo.PET_ARMS, PetData.PetInfo.PET_LEGS, PetData.PetInfo.PET_FACE};
        cursor = db.query(PetData.PetInfo.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    public Cursor checkForFood(SQLiteDatabase db, String[] whereArgs) {
        String[] projections = {FoodInfo.FOOD_NAME, FoodInfo.FOOD_CONTENTS};
        cursor2 = db.query(FoodInfo.TABLE_NAME, projections, FoodInfo.FOOD_CONTENTS + "=?", whereArgs, null, null, null);
        return cursor2;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
