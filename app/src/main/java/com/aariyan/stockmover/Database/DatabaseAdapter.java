package com.aariyan.stockmover.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.aariyan.stockmover.Interface.DeletePostingData;
import com.aariyan.stockmover.Model.LocationSyncModel;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.Model.StockModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter implements DeletePostingData {


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


    //get location:
    public List<LocationSyncModel> getLocation(String input) {

        locationList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        //select * from tableName where name = ? and customerName = ?:
        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
        String selection = DatabaseHelper.strBinLocationName + "=?";


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
//    public long insertStocks(String productCode, String expireDate, String quantity, String type) {
//        SQLiteDatabase database = helper.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.productCode, productCode);
//        contentValues.put(DatabaseHelper.date, expireDate);
//        contentValues.put(DatabaseHelper.quantity, quantity);
//        contentValues.put(DatabaseHelper.stockType, type);
//
//        long id = database.insert(DatabaseHelper.PRODUCT_STOCK_IN_OUT_NAME, null, contentValues);
//        return id;
//    }

    //Insert Stock In Stock Out:
    public long insertStockOut(String shelfFrom, String productFromBarcode, String qty, String productFromCode) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.shelffrom, shelfFrom);
        contentValues.put(DatabaseHelper.productfrombarcode, productFromBarcode);
        contentValues.put(DatabaseHelper.Qty, qty);
        contentValues.put(DatabaseHelper.productfromcode, productFromCode);
        contentValues.put(DatabaseHelper.out, 0);

        long id = database.insert(DatabaseHelper.PRODUCT_STOCK_IN_OUT_NAME, null, contentValues);
        return id;
    }

    public long insertStockIn(String shelfTo, String productToBarcode, String confirmQty, String expireDate,
                              String productToCode, int type) {

        SQLiteDatabase database = helper.getWritableDatabase();

        String selection = DatabaseHelper.productfrombarcode + "=?";
        String[] args = {productToBarcode};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.shelfto, shelfTo);
        contentValues.put(DatabaseHelper.producttobarcode, productToBarcode);
        contentValues.put(DatabaseHelper.confirmqty, confirmQty);
        contentValues.put(DatabaseHelper.Expiry, expireDate);
        contentValues.put(DatabaseHelper.producttocode, productToCode);
        contentValues.put(DatabaseHelper.out, 1);

        long id = database.update(DatabaseHelper.PRODUCT_STOCK_IN_OUT_NAME, contentValues, selection, args);
        return id;
    }

    //validate location:
    public List<StockModel> getStock() {
        stockList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.shelffrom, DatabaseHelper.productfrombarcode,
                DatabaseHelper.Qty, DatabaseHelper.productfromcode, DatabaseHelper.out,
                DatabaseHelper.shelfto, DatabaseHelper.producttobarcode, DatabaseHelper.confirmqty, DatabaseHelper.Expiry,
                DatabaseHelper.producttocode, DatabaseHelper.in};

        Cursor cursor = database.query(DatabaseHelper.PRODUCT_STOCK_IN_OUT_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            StockModel model = new StockModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
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

    @Override
    public void trackDelete(String flag) {
        if (flag.equals("yes")) {
            dropStockTable();
        } else {
            Toast.makeText(helper.context, "Unable to post data!", Toast.LENGTH_SHORT).show();
        }
    }


    class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        private static final String DATABASE_NAME = "stock_mover.db";
        private static final int VERSION_NUMBER = 5;

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
        private static final String shelffrom = "shelffrom";
        private static final String productfrombarcode = "productfrombarcode";
        private static final String Qty = "Qty";
        private static final String productfromcode = "productfromcode";
        private static final String out = "stockOut";

        //stock In
        private static final String shelfto = "shelfto";
        private static final String producttobarcode = "producttobarcode";
        private static final String confirmqty = "confirmqty";
        private static final String Expiry = "Expiry";
        private static final String producttocode = "producttocode";
        private static final String in = "stockIn";

        //Creating Stock_out_in the table:
        private static final String CREATE_STOCK_IN_OUT_TABLE = "CREATE TABLE " + PRODUCT_STOCK_IN_OUT_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + shelffrom + " VARCHAR(255),"
                + productfrombarcode + " VARCHAR(255),"
                + Qty + " VARCHAR(255),"
                + productfromcode + " VARCHAR(255),"
                + shelfto + " VARCHAR(255),"
                + producttobarcode + " VARCHAR(255),"
                + confirmqty + " VARCHAR(255),"
                + Expiry + " VARCHAR(255),"
                + out + " INTEGER,"
                + in + " INTEGER,"
                + producttocode + " VARCHAR(255));";
        private static final String DROP_STOCK_IN_OUT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_STOCK_IN_OUT_NAME;


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
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}