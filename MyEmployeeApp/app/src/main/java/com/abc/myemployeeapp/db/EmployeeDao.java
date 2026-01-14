package com.abc.myemployeeapp.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.abc.myemployeeapp.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private DatabaseHelper dbHelper;

    public EmployeeDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 🔹 Insert Employee
    public long insertEmployee(Employee e) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", e.getName());
        cv.put("email", e.getEmail());
        cv.put("phone", e.getPhone());
        cv.put("age", e.getAge());
        cv.put("salary", e.getSalary());
        cv.put("active", e.getActive());
        cv.put("joiningDate", e.getJoiningDate());
        cv.put("department", e.getDepartment());
        cv.put("skills", e.getSkills());
        return db.insert("employees", null, cv);
    }

    // 🔹 Get All Employees
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM employees ORDER BY id DESC", null);

        if (c.moveToFirst()) {
            do {
                list.add(cursorToEmployee(c));
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }

    // 🔹 Get Employee by ID
    public Employee getEmployeeById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM employees WHERE id=?",
                new String[]{String.valueOf(id)}
        );

        if (c.moveToFirst()) {
            Employee e = cursorToEmployee(c);
            c.close();
            return e;
        }

        c.close();
        return null;
    }

    // 🔹 Update Employee
    public int updateEmployee(Employee e) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", e.getName());
        cv.put("email", e.getEmail());
        cv.put("phone", e.getPhone());
        cv.put("age", e.getAge());
        cv.put("salary", e.getSalary());
        cv.put("active", e.getActive());
        cv.put("joiningDate", e.getJoiningDate());
        cv.put("department", e.getDepartment());
        cv.put("skills", e.getSkills());
        return db.update(
                "employees",
                cv,
                "id=?",
                new String[]{String.valueOf(e.getId())}
        );
    }

    // 🔹 Delete Employee
    public int deleteEmployee(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                "employees",
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }

    // 🔹 Cursor → Employee converter
    private Employee cursorToEmployee(Cursor c) {
        Employee e = new Employee();
        e.setId(c.getLong(c.getColumnIndexOrThrow("id")));
        e.setName(c.getString(c.getColumnIndexOrThrow("name")));
        e.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
        e.setPhone(c.getString(c.getColumnIndexOrThrow("phone")));
        e.setAge(c.getInt(c.getColumnIndexOrThrow("age")));
        e.setSalary(c.getDouble(c.getColumnIndexOrThrow("salary")));
        e.setActive(c.getString(c.getColumnIndexOrThrow("active")));
        e.setJoiningDate(c.getLong(c.getColumnIndexOrThrow("joiningDate")));
        e.setDepartment(c.getString(c.getColumnIndexOrThrow("department")));
        e.setSkills(c.getString(c.getColumnIndexOrThrow("skills")));
        return e;
    }
}
