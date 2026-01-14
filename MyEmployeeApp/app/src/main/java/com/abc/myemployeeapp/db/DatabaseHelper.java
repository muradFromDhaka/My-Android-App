package com.abc.myemployeeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // 🔹 Database info
    private static final String DATABASE_NAME = "employee_db";
    private static final int DATABASE_VERSION = 1;

    // 🔹 Table name
    public static final String TABLE_EMPLOYEES = "employees";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 🔹 Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EMPLOYEE_TABLE =
                "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "email TEXT, " +
                        "phone TEXT, " +
                        "age INTEGER, " +
                        "salary REAL, " +
                        "active TEXT, " +
                        "joiningDate INTEGER, " +
                        "department TEXT, " +
                        "skills TEXT " +
                        ")";

        db.execSQL(CREATE_EMPLOYEE_TABLE);
    }

    // 🔹 Handle database upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Simple strategy: drop & recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        onCreate(db);
    }
}
