package com.abc.campustrack.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abc.campustrack.entity.CampusTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CampusTrackDao {

    private DatabaseHelper dbHelper;

    public CampusTrackDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertCampus(CampusTrack cam){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", cam.getName());
        cv.put("email", cam.getEmail());
        cv.put("password", cam.getPassword());
        cv.put("phone", cam.getPhone());
        cv.put("address", cam.getAddress());
        cv.put("dateOfBirth", cam.getDateOfBirth());
        cv.put("gender", cam.getGender());
        cv.put("course", cam.getCourse());
        cv.put("gpa", cam.getGpa());
        cv.put("hobbies", cam.getHobbies() == null ? "" : String.join(",", cam.getHobbies()));
        cv.put("skills", cam.getSkills());
        cv.put("termsAccepted", cam.isTermsAccepted() ? 1 : 0);

       long id =  db.insert(dbHelper.TABLE_NAME, null, cv);

       return id;
    }

    public int updateCampus(long id, CampusTrack cam){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", cam.getName());
        cv.put("email", cam.getEmail());
        cv.put("password", cam.getPassword());
        cv.put("phone", cam.getPhone());
        cv.put("address", cam.getAddress());
        cv.put("dateOfBirth", cam.getDateOfBirth());
        cv.put("gender", cam.getGender());
        cv.put("course", cam.getCourse());
        cv.put("gpa", cam.getGpa());
        cv.put("hobbies", cam.getHobbies() == null ? "" : String.join(",", cam.getHobbies()));
        cv.put("skills", cam.getSkills());
        cv.put("termsAccepted", cam.isTermsAccepted() ? 1 : 0);
        String[] arg = new String[]{String.valueOf(id)};

        int selectedRowsUpdated =  db.update(dbHelper.TABLE_NAME, cv, "id=?",arg);

        return selectedRowsUpdated;
    }

    public CampusTrack getCamusTrackById(Long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        CampusTrack cam =null;
        Cursor c = db.rawQuery(
                "select * from " + dbHelper.TABLE_NAME + " where id =?",
                new String[]{String.valueOf(id)});
        if(c != null){
           if(c.moveToFirst()){
               cam = cursorToCampusTrack(c);
           }
        }
        return cam;
    }

    public List<CampusTrack> getCampusTrack(){
        List<CampusTrack> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c =db.rawQuery("Select * from " + dbHelper.TABLE_NAME,null);

        if(c != null){
            if(c.moveToFirst()){
                do {
                    list.add(cursorToCampusTrack(c));
                }while (c.moveToNext());
            }
            c.close();
        }
        db.close();
        return list;
        }

    public void deleteCampus(long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] arg = new String[]{String.valueOf(id)};
        db.delete(dbHelper.TABLE_NAME, "id=?", arg);
    }


    //cursor to campusTrack
    private CampusTrack cursorToCampusTrack(Cursor c){
        CampusTrack cam = new CampusTrack();

        cam.setId(c.getLong(c.getColumnIndexOrThrow("id")));
        cam.setName(c.getString(c.getColumnIndexOrThrow("name")));
        cam.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
        cam.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
        cam.setPhone(c.getString(c.getColumnIndexOrThrow("phone")));
        cam.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
        cam.setDateOfBirth(c.getString(c.getColumnIndexOrThrow("dateOfBirth")));
        cam.setGender(c.getString(c.getColumnIndexOrThrow("gender")));
        cam.setCourse(c.getString(c.getColumnIndexOrThrow("course")));
        cam.setGpa(c.getLong(c.getColumnIndexOrThrow("gpa")));
       String hobbiesStr = c.getString(c.getColumnIndexOrThrow("hobbies"));
       if(hobbiesStr != null && !hobbiesStr.isEmpty()){
           cam.setHobbies(Arrays.asList(hobbiesStr.split(",")));
       }else {
           cam.setHobbies(new ArrayList<>());
       }
        cam.setSkills(c.getString(c.getColumnIndexOrThrow("skills")));

       int terms = c.getInt(c.getColumnIndexOrThrow("termsAccepted"));
       cam.setTermsAccepted(terms == 1);

       return cam;
    }
}
