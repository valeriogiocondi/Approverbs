package com.valeriogiocondi.user.proverbsapp.System;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.valeriogiocondi.user.proverbsapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DatabaseManager extends SQLiteOpenHelper {

    Context context;
    private  static final String DB_NAME = "proverbsapp.db";
    private  static final int DB_VERSION = 1;

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String queryDb(String query, String tableNAme) {

        SQLiteDatabase db;
        Cursor c;
        String value;
        db = getWritableDatabase();

        try {

            c = db.rawQuery(query, null);
            c.moveToFirst();

            while (!c.isAfterLast()) {

                if ((value = c.getString(c.getColumnIndex("name"))) != null)
                    return value;
            }

        } catch (SQLException e) {

            createTable(db, tableNAme);
        }

        return null;
    }

    public void createTable(SQLiteDatabase db, String nameTable) {

        /*
        *
        *   CREATE TABLE AND FILL IT OUT
        *
        */

        String queryCreateTable;
        InputStream inputStream;
        BufferedReader br;

        queryCreateTable = "CREATE TABLE IF NOT EXISTS "+nameTable+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT " +
                ");";

        db.execSQL(queryCreateTable);

        ContentValues values = new ContentValues();

        switch (nameTable) {

            case "proverbs_italian": {

                inputStream = context.getResources().openRawResource(R.raw.proverbs_italian);
                break;
            }
            case "proverbs_english": {

                inputStream = context.getResources().openRawResource(R.raw.proverbs_english);
                break;
            }
            case "proverbs_deutsche": {

                inputStream = context.getResources().openRawResource(R.raw.proverbs_deutsche);
                break;
            }
            case "proverbs_latin": {

                inputStream = context.getResources().openRawResource(R.raw.proverbs_latin);
                break;
            }
            case "proverbs_chinese": {

                inputStream = context.getResources().openRawResource(R.raw.proverbs_chinese);
                break;
            }
            case "proverbs_spanish": {

                inputStream = context.getResources().openRawResource(R.raw.proverbs_spanish);
                break;
            }

            default: {

                inputStream = null;
                break;
            }
        }

        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;

            while ((line = br.readLine()) != null) {

                /*
                *
                *   insert into DB
                *
                */

                values.put("name", line);
                db.insert(nameTable, null, values);
                values.clear();

            }
            br.close();
        }
        catch (IOException e) {

        }
    }
}
