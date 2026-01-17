package com.abc.studentapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abc.studentapp.entity.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDao {

    private DatabaseHelper dbHelper;

    public StudentDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertStudent(Student student){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", student.getName());
            cv.put("email", student.getEmail());
            cv.put("password", student.getPassword());
            cv.put("age", student.getAge());
            cv.put("gender", student.getGender());
            cv.put("dateOfBirth", student.getDateOfBirth());
            cv.put("phone", student.getPhone());
            cv.put("address", student.getAddress());
            cv.put("course", student.getCourse());
            cv.put("department", student.getDepartment());
            cv.put("skills", student.getSkills());
            cv.put("hobbies", student.getHobbies() == null ? "" : String.join("|", student.getHobbies()));
            cv.put("termsAccepted", student.isTermsAccepted() ? 1 : 0 );

            return db.insert(dbHelper.TABLE_STUDENT , null, cv);
        } finally {
            db.close();
        }
    }


    public int updateStudent(long id, Student student){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", student.getName());
            cv.put("email", student.getEmail());
            cv.put("password", student.getPassword());
            cv.put("age", student.getAge());
            cv.put("gender", student.getGender());
            cv.put("dateOfBirth", student.getDateOfBirth());
            cv.put("phone", student.getPhone());
            cv.put("address", student.getAddress());
            cv.put("course", student.getCourse());
            cv.put("department", student.getDepartment());
            cv.put("skills", student.getSkills());
            cv.put("hobbies", student.getHobbies() == null ? "" : String.join("|", student.getHobbies()));
            cv.put("termsAccepted", student.isTermsAccepted() ? 1 : 0 );

            return db.update(dbHelper.TABLE_STUDENT, cv, "id=?", new String[]{String.valueOf(id)});
        } finally {
            db.close();
        }
    }

    public List<Student> getAllStudent(){
        List<Student> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + dbHelper.TABLE_STUDENT, null);

        if(c.moveToFirst()){
            do {
                list.add(cursorToStudent(c));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public Student getStudentById(Long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] arg = new String[]{String.valueOf(id)};

        Cursor c = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_STUDENT + " WHERE id=?", arg);

        Student s = null;
        if(c.moveToFirst()){
           s =  cursorToStudent(c);
        }

        c.close();
        db.close();
        return s;
    }

    public void deleteStudent(Long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_STUDENT, "id=?", new String[]{String.valueOf(id)});
    }

    private Student cursorToStudent(Cursor c) {

        Student s = new Student();

        s.setId(c.getLong(c.getColumnIndexOrThrow("id")));
        s.setName(c.getString(c.getColumnIndexOrThrow("name")));
        s.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
        s.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
        s.setAge(c.getInt(c.getColumnIndexOrThrow("age")));
        s.setGender(c.getString(c.getColumnIndexOrThrow("gender")));
        s.setDateOfBirth(c.getString(c.getColumnIndexOrThrow("dateOfBirth")));
        s.setPhone(c.getString(c.getColumnIndexOrThrow("phone")));
        s.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
        s.setCourse(c.getString(c.getColumnIndexOrThrow("course")));
        s.setDepartment(c.getString(c.getColumnIndexOrThrow("department")));
        s.setSkills(c.getString(c.getColumnIndexOrThrow("skills")));

        // 🔹 hobbies: String → List<String>
        String hobbyStr = c.getString(c.getColumnIndexOrThrow("hobbies"));
        if (hobbyStr != null && !hobbyStr.isEmpty()) {
            s.setHobbies(Arrays.asList(hobbyStr.split("\\|")));
        } else {
            s.setHobbies(new ArrayList<>());
        }

        // 🔹 termsAccepted: int → boolean
        s.setTermsAccepted(
                c.getInt(c.getColumnIndexOrThrow("termsAccepted")) == 1
        );

        return s;
    }

}
