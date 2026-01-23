package com.abc.campustrack.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "campus_track";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "campus_members";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_NAME_CAMPUS = "CREATE TABLE " + TABLE_NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT," +
                "email TEXT," +
                "password TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "dateOfBirth TEXT," +
                "gender TEXT," +
                "course TEXT," +
                "gpa REAl," +
                "hobbies TEXT," +
                "skills TEXT," +
                "termsAccepted INTEGER" +
               ")";

        db.execSQL(TABLE_NAME_CAMPUS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
    }
}
