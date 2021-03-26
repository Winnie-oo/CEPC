package com.example.cepc.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final static String TAG = "DataBaseHelper";
    private final static String DATABASE_NAME = "database.db";
    public final static String USERS_TABLE_NAME = "users";
    public final static String RECORDS_TABLE_NAME = "records";
    private final static int DATABASE_VERSION = 1;
    private Context mContext;

    private final static String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USERS_TABLE_NAME
            + "(user_id INTEGER PRIMARY KEY,"
            + "username TEXT NOT NULL,"
            + "password TEXT NOT NULL,"
            + "daymarks INTEGER CHECK(daymarks >= 0 and daymarks <= 100))";

    private final static String CREATE_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS "
            + RECORDS_TABLE_NAME
            + "(record_id INTEGER PRIMARY KEY,"
            + "user_name TEXT NOT NULL,"
            + "temperature TEXT NOT NULL,"
            + "patient TEXT NOT NULL,"
            + "date TEXT NOT NULL,"
            + "address TEXT NOT NULL)";

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.mContext = context;
    }
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.mContext = context;
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_USERS_TABLE);//创建users表
        db.execSQL(CREATE_RECORDS_TABLE);//创建records表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade o = " + oldVersion + " , n = " + newVersion);
    }
}
