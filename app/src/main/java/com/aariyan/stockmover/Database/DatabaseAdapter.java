package com.aariyan.stockmover.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.aariyan.stockmover.Activity.HomeActivity;
import com.aariyan.stockmover.Interface.DeletePostingData;
import com.aariyan.stockmover.Model.LocationSyncModel;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.Model.QueueModel;
import com.aariyan.stockmover.Model.StockModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseAdapter {


    DatabaseHelper helper;
    private List<ProductsSyncModel> productList = new ArrayList<>();
    private List<LocationSyncModel> locationList = new ArrayList<>();
    private List<StockModel> stockList = new ArrayList<>();

    public static boolean checkProduct = false;
    public static boolean checkLocations = false;


    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    //Insert Product:
    public long insertProducts(String barCode, String pastelCode, String description) {
        SQLiteDatabase database = helper.getWritableDatabase();
        if (!checkProduct) {
            checkProduct = true;
            dropProductTable();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PastelCode, pastelCode);
        contentValues.put(DatabaseHelper.Barcode, barCode);
        contentValues.put(DatabaseHelper.ProductDescription, description);

        long id = database.insert(DatabaseHelper.PRODUCT_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertQueue(QueueModel model) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.BARCODE, model.getBarcode());
        contentValues.put(DatabaseHelper.LOCATION, model.getLocation());
        contentValues.put(DatabaseHelper.MOVE_IN, model.getMoveIn());
        contentValues.put(DatabaseHelper.MOVE_FROM, model.getMoveFrom());

        long id = database.insert(DatabaseHelper.QUEUE_TABLE_NAME, null, contentValues);
        return id;
    }

    //Insert Product:
    public long insertLocations(String intBinLocationId, String strBinLocationName, String intaislenumber) {
        SQLiteDatabase database = helper.getWritableDatabase();
        if (!checkLocations) {
            checkLocations = true;
            dropLocationTable();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.intBinLocationId, intBinLocationId);
        contentValues.put(DatabaseHelper.strBinLocationName, strBinLocationName);
        contentValues.put(DatabaseHelper.intaislenumber, intaislenumber);

        long id = database.insert(DatabaseHelper.LOCATION_TABLE_NAME, null, contentValues);
        return id;
    }

    public long updateQueueByBarcode(String stockType, String barcode) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String selection = DatabaseHelper.BARCODE + " LIKE ? ";
        String[] args = {"" + barcode};

        ContentValues contentValues = new ContentValues();
        if (stockType.equals("MOVE_FROM")) {
            contentValues.put(DatabaseHelper.MOVE_FROM, 1);
        } else {
            contentValues.put(DatabaseHelper.MOVE_IN, 1);
        }
        long ids = database.update(DatabaseHelper.QUEUE_TABLE_NAME, contentValues, selection, args);

        return ids;
    }


    public List<QueueModel> getQueueByBarcode(String barcode) {
        List<QueueModel> list = new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();
        //select * from tableName where name = ? and customerName = ?:
        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
        String selection = DatabaseHelper.BARCODE + "=?";


        String[] args = {barcode};
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.MOVE_IN, DatabaseHelper.MOVE_FROM, DatabaseHelper.LOCATION,DatabaseHelper.BARCODE};

        Cursor cursor = database.query(DatabaseHelper.QUEUE_TABLE_NAME, columns, selection, args, null, null, null);
        while (cursor.moveToNext()) {
            QueueModel model = new QueueModel(
                    ""+cursor.getString(1),
                    ""+cursor.getString(2),
                    ""+cursor.getString(3),
                    ""+cursor.getString(4)
            );
            list.add(model);
        }
        return list;
    }


    //get location:
    public List<LocationSyncModel> getLocation(String input) {

        locationList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        //select * from tableName where name = ? and customerName = ?:
        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
        String selection = DatabaseHelper.strBinLocationName + "=?".toUpperCase(Locale.ROOT);


        String[] args = {input};
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.intBinLocationId, DatabaseHelper.strBinLocationName, DatabaseHelper.intaislenumber};

        Cursor cursor = database.query(DatabaseHelper.LOCATION_TABLE_NAME, columns, selection, args, null, null, null);
        while (cursor.moveToNext()) {
            LocationSyncModel model = new LocationSyncModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            locationList.add(model);
        }
        return locationList;
    }


    //validate location:
    public List<ProductsSyncModel> getProduct() {

        productList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.PastelCode, DatabaseHelper.Barcode, DatabaseHelper.ProductDescription};

        Cursor cursor = database.query(DatabaseHelper.PRODUCT_TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ProductsSyncModel model = new ProductsSyncModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            productList.add(model);
        }
        return productList;
    }

    //validate location:
    public List<ProductsSyncModel> getProductByBarcode(String barcode) {

        productList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();

        String selection = DatabaseHelper.Barcode + "=?";


        String[] args = {barcode};

        String[] columns = {DatabaseHelper.UID, DatabaseHelper.PastelCode, DatabaseHelper.Barcode, DatabaseHelper.ProductDescription};

        Cursor cursor = database.query(DatabaseHelper.PRODUCT_TABLE_NAME, columns, selection, args, null, null, null);
        while (cursor.moveToNext()) {
            ProductsSyncModel model = new ProductsSyncModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            productList.add(model);
        }
        return productList;
    }


    //Insert STOCK_IN_OUT:
    public long insertStocks(String shelf, String Qty, String barcode, String Expiry, String productCode, String strTransactionType) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.shelf, shelf);
        contentValues.put(DatabaseHelper.Qty, Qty);
        contentValues.put(DatabaseHelper.barcode, barcode);
        contentValues.put(DatabaseHelper.Expiry, Expiry);
        contentValues.put(DatabaseHelper.productCode, productCode);
        contentValues.put(DatabaseHelper.strTransactionType, strTransactionType);

        long id = database.insert(DatabaseHelper.PRODUCT_STOCK_IN_OUT_NAME, null, contentValues);
        return id;
    }


    //validate location:
    public List<StockModel> getStock(String deviceId) {
        stockList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.shelf, DatabaseHelper.Qty,
                DatabaseHelper.barcode, DatabaseHelper.Expiry, DatabaseHelper.productCode, DatabaseHelper.strTransactionType};

        Cursor cursor = database.query(DatabaseHelper.PRODUCT_STOCK_IN_OUT_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            StockModel model = new StockModel(
                    "" + cursor.getString(0),
                    "" + cursor.getString(1),
                    "" + deviceId,
                    "" + cursor.getString(2),
                    "" + cursor.getString(3),
                    "" + cursor.getString(4),
                    "" + cursor.getString(5),
                    "" + cursor.getString(6)
            );
            stockList.add(model);
        }
        return stockList;
    }


    public void dropProductTable() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(DatabaseHelper.DROP_PRODUCT_TABLE);
        database.execSQL(DatabaseHelper.CREATE_PRODUCT_TABLE);
    }

    public void dropStockTable() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(DatabaseHelper.DROP_STOCK_IN_OUT_TABLE);
        database.execSQL(DatabaseHelper.CREATE_STOCK_IN_OUT_TABLE);
    }

    public void dropLocationTable() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(DatabaseHelper.DROP_LOCATION_TABLE);
        database.execSQL(DatabaseHelper.CREATE_LOCATION_TABLE);
    }

    public void dropQueueTable() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(DatabaseHelper.DROP_QUEUE_TABLE);
        database.execSQL(DatabaseHelper.CREATE_QUEUE_TABLE);
    }


    class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;


        private static final String DATABASE_NAME = "stock_mover.db";
        private static final int VERSION_NUMBER = 10;

        //Product Table:
        private static final String PRODUCT_TABLE_NAME = "products";
        private static final String UID = "_id";
        private static final String PastelCode = "PastelCode";
        private static final String ProductDescription = "ProductDescription";
        private static final String Barcode = "Barcode";


        //Creating Product the table:
        private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + PRODUCT_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PastelCode + " VARCHAR(255),"
                + ProductDescription + " VARCHAR(255),"
                + Barcode + " VARCHAR(255));";
        private static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME;


        //Location Table:
        private static final String LOCATION_TABLE_NAME = "locations";
        private static final String intBinLocationId = "intBinLocationId";
        private static final String strBinLocationName = "strBinLocationName";
        private static final String intaislenumber = "intaislenumber";

        //Creating Location the table:
        private static final String CREATE_LOCATION_TABLE = "CREATE TABLE " + LOCATION_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + intBinLocationId + " VARCHAR(255),"
                + strBinLocationName + " VARCHAR(255),"
                + intaislenumber + " VARCHAR(255));";
        private static final String DROP_LOCATION_TABLE = "DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME;


        //Stock_out_in Table:
        //Stock out:
        private static final String PRODUCT_STOCK_IN_OUT_NAME = "stock_in_out";
        private static final String shelf = "shelf";
        private static final String Qty = "Qty";
        private static final String barcode = "barcode";
        private static final String Expiry = "Expiry";
        private static final String productCode = "productCode";
        private static final String strTransactionType = "strTransactionType";

        //Creating Stock_out_in the table:
        private static final String CREATE_STOCK_IN_OUT_TABLE = "CREATE TABLE " + PRODUCT_STOCK_IN_OUT_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + shelf + " VARCHAR(255),"
                + Qty + " VARCHAR(255),"
                + barcode + " VARCHAR(255),"
                + Expiry + " VARCHAR(255),"
                + productCode + " VARCHAR(255),"
                + strTransactionType + " VARCHAR(255));";
        private static final String DROP_STOCK_IN_OUT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_STOCK_IN_OUT_NAME;

        private static final String QUEUE_TABLE_NAME = "queue";
        private static final String MOVE_IN = "move_in";
        private static final String MOVE_FROM = "move_from";
        private static final String LOCATION = "location";
        private static final String BARCODE = "barcode";
        private static final String CREATE_QUEUE_TABLE = "CREATE TABLE " + QUEUE_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOVE_IN + " VARCHAR(255),"
                + MOVE_FROM + " VARCHAR(255),"
                + LOCATION + " VARCHAR(255),"
                + BARCODE + " VARCHAR(255));";
        private static final String DROP_QUEUE_TABLE = "DROP TABLE IF EXISTS " + QUEUE_TABLE_NAME;


        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, VERSION_NUMBER);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create table:
            try {
                db.execSQL(CREATE_PRODUCT_TABLE);
                db.execSQL(CREATE_LOCATION_TABLE);
                db.execSQL(CREATE_STOCK_IN_OUT_TABLE);
                db.execSQL(CREATE_QUEUE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_PRODUCT_TABLE);
                db.execSQL(DROP_LOCATION_TABLE);
                db.execSQL(DROP_STOCK_IN_OUT_TABLE);
                db.execSQL(DROP_QUEUE_TABLE);
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}