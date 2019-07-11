package com.example.contactswhatsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "USER.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user_table(id int,name text,message text,number text,profileImage int,lastMesage,lastSpokeTime text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertedUser(User user) {
        long rowId = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", user.getId());
            contentValues.put("name", user.getUserName());
            contentValues.put("number", user.getTelNumber());
            contentValues.put("message", user.getMessage());
            contentValues.put("lastMesage", user.getLastMesage());
            contentValues.put("profileImage", user.getProfileImage());
            contentValues.put("lastSpokeTime", user.getLastSpokeTime());
            rowId = db.insert("user_table", null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowId != -1;
    }

    public boolean insertedText(String name, String message, int id,int profileImage,String lastSpokeTime) {
        long rowId = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("profileImage", profileImage);
            contentValues.put("name", name);
            contentValues.put("message", message);
            contentValues.put("lastSpokeTime", lastSpokeTime);

            Log.e("inserted", name);
            Log.e("inserted", message);
            Log.e("inserted", String.valueOf(id));

            rowId = db.insert("user_table", null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowId != -1;
    }


    public List<User> getAllUsers2() {
        List<User> userList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "Select id,name,profileImage,lastMesage ,message from user_table  group by id ";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userList.add(new User(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("lastMesage")),
                        cursor.getInt(cursor.getColumnIndex("profileImage")),
                        cursor.getString(cursor.getColumnIndex("message"))
                ));
                Log.e("loggetAllUser", cursor.getString(cursor.getColumnIndex("name")));
                Log.e("loggetAllUser", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
    public List<String> getAllMessagesByUserId(int id) {
        List<String> messageList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "Select message from user_table where id="+id;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                messageList.add(
                        cursor.getString(cursor.getColumnIndex("message"))
                );
                Log.e("loggetAllMessagesByUser", cursor.getString(cursor.getColumnIndex("message")));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageList;
    }








}
