package com.jackson.processcommunication.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/*
 * BookProvider  2018-11-21
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.

 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 11 21
 */
public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.jackson.processcommunication.book.provider";
    String uriString;
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {
        Log.e("hbj--", "onCreate,current thread:" + Thread.currentThread());
        mContext=getContext();
        initProviderData();
        return false;
    }

    private void initProviderData(){
        mDb=new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'Ios');");
        mDb.execSQL("insert into book values(5,'Html5');");
        mDb.execSQL("insert into user values(1,'jack',1);");
        mDb.execSQL("insert into user values(2,'jasmine',0);");
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.e("hbj--", "query,current thread:" + Thread.currentThread());
        String table=getTableName(uri);
        if (table==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        return mDb.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }


    @Override
    public String getType(Uri uri) {
        Log.e("hbj--", "getType");
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e("hbj--", "insert");
        String table=getTableName(uri);
        if (table==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        mDb.insert(table,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e("hbj--", "delete");
        String table=getTableName(uri);
        if (table==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        int count=mDb.delete(table,selection,selectionArgs);
        if (count>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e("hbj--", "update");
        String table=getTableName(uri);
        if (table==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        int row=mDb.update(table,values,selection,selectionArgs);
        if (row>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return row;
    }

    /**
     * 获取外界要访问的数据表名称
     *
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;

    }

}