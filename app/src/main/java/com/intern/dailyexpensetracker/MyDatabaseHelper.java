package com.intern.dailyexpensetracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "exp.db";
    public final static String TABLE="category";
    private static final int DATABASE_VERSION = 16;

    // Database creation sql statement
    private static final String DATABASE_CREATE_CONVERSATIONS = "create table category( _id integer primary key AUTOINCREMENT,category text not null);";
    private static final String DATABASE_CREATE_ITEM = "create table item( _id integer primary key AUTOINCREMENT,item text not null,category text not null,amount text not null,date text not null);";
    private static final String DATABASE_CREATE_SETTINGS = "create table settings( _id integer primary key AUTOINCREMENT,limits text not null);";
    //private static final String DATABASE_CREATE_CONTACTS = "create table contacts( _id integer primary key AUTOINCREMENT,number text not null, name text not null, photo text not null, crypto_key text not null);";
   // private static final String DATABASE_CREATE_PROFILE = "create table profile( _id integer primary key AUTOINCREMENT,name text not null, number text not null);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CONVERSATIONS);
        database.execSQL(DATABASE_CREATE_ITEM);
        database.execSQL(DATABASE_CREATE_SETTINGS);
       // database.execSQL(DATABASE_CREATE_CONTACTS);


    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS category");
        database.execSQL("DROP TABLE IF EXISTS item");
        database.execSQL("DROP TABLE IF EXISTS settings");

        onCreate(database);

    }



}
