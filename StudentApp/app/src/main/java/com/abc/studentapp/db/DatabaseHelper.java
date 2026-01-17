package com.abc.studentapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "student_app";
    private static final int DB_VERSION = 1;
    public static final String TABLE_STUDENT = "Students";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_NAME_STUDENT =
                "CREATE TABLE " + TABLE_STUDENT + "( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "email TEXT," +
                        "password TEXT," +
                        "age INTEGER," +
                        "gender TEXT," +
                        "dateOfBirth TEXT," +
                        "phone TEXT," +
                        "address TEXT," +
                        "course TEXT," +
                        "department TEXT," +
                        "skills TEXT," +
                        "hobbies TEXT," +
                        "termsAccepted INTEGER " +
                        ")";
        db.execSQL(TABLE_NAME_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }
}
