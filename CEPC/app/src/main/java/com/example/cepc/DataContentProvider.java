package com.example.cepc;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cepc.db.DataBaseHelper;

public class DataContentProvider extends ContentProvider {
    private final static String AUTHORITY = "com.example.cepc.DataContentProvider";
    private final static int USER_URI_CODE = 0;//用来区别users和record
    private final static int RECORD_URI_CODE = 1;

    private Context mContext;
    private SQLiteDatabase mDataBase;
    //用来存放合法Uri
    private final static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY,"/"+DataBaseHelper.USERS_TABLE_NAME,USER_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"/"+DataBaseHelper.RECORDS_TABLE_NAME,RECORD_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDataBase = new DataBaseHelper(mContext).getWritableDatabase();//打开数据库，能够读取和写入
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable
            String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;
        switch (uriType) {
            case USER_URI_CODE:
                cursor = mDataBase.query(DataBaseHelper.USERS_TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder,null);
                break;
            case RECORD_URI_CODE:
                cursor = mDataBase.query(DataBaseHelper.RECORDS_TABLE_NAME,
                        projection,selection,selectionArgs,null,null,sortOrder,null);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long row;
        switch (uriType) {
            case USER_URI_CODE:
                row = mDataBase.insert(DataBaseHelper.USERS_TABLE_NAME,null, values);
                break;
            case RECORD_URI_CODE:
                row = mDataBase.insert(DataBaseHelper.RECORDS_TABLE_NAME,null, values);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        if(row > -1) {
            mContext.getContentResolver().notifyChange(uri,null);
            return ContentUris.withAppendedId(uri, row);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int rowDelete;
        switch (uriType) {
            case USER_URI_CODE:
                rowDelete = mDataBase.delete(DataBaseHelper.USERS_TABLE_NAME,selection,selectionArgs);
                break;
            case RECORD_URI_CODE:
                rowDelete = mDataBase.delete(DataBaseHelper.RECORDS_TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        if(rowDelete > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return rowDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int rowUpdate;
        switch (uriType) {
            case USER_URI_CODE:
                rowUpdate = mDataBase.update(DataBaseHelper.USERS_TABLE_NAME,
                        values,selection,selectionArgs);
                break;
            case RECORD_URI_CODE:
                rowUpdate = mDataBase.update(DataBaseHelper.RECORDS_TABLE_NAME,
                        values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        if(rowUpdate > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return rowUpdate;
    }
}
