package com.aariyan.stockmover.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.aariyan.stockmover.Model.ProductsSyncModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {


    DatabaseHelper helper;
    private List<ProductsSyncModel> planList = new ArrayList<>();


    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    //Insert Header:
    public long insertProducts(String barCode, String pastelCode) {

        SQLiteDatabase database = helper.getWritableDatabase();
//        database.execSQL(DatabaseHelper.DROP_PRODUCT_TABLE);
//        database.execSQL(DatabaseHelper.CREATE_PRODUCT_TABLE);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PastelCode, pastelCode);
        contentValues.put(DatabaseHelper.Barcode, barCode);

        long id = database.insert(DatabaseHelper.PRODUCT_TABLE_NAME, null, contentValues);
        return id;
    }


    class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        private static final String DATABASE_NAME = "stock_mover.db";
        private static final int VERSION_NUMBER = 3;

        //Header Table:
        private static final String PRODUCT_TABLE_NAME = "products";
        private static final String UID = "_id";
        private static final String PastelCode = "PastelCode";
        private static final String Barcode = "Barcode";


        //Creating the table:
        private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + PRODUCT_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PastelCode + " VARCHAR(255),"
                + Barcode + " VARCHAR(255));";
        private static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME;


        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, VERSION_NUMBER);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create table:
            try {
                db.execSQL(CREATE_PRODUCT_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_PRODUCT_TABLE);
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}