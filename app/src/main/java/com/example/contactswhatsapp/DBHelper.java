package com.example.contactswhatsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.contactswhatsapp.model.Data;
import com.example.contactswhatsapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "USER.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user_table(id int, name text, number text, profileImage int)");

        db.execSQL("create table data_table(id INTEGER PRIMARY KEY AUTOINCREMENT,data text,data_type int," +
                "sendDataTime text,senderId int,reciverId int)");

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
            contentValues.put("profileImage", user.getProfileImage());
            rowId = db.insert("user_table", null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowId != -1;
    }

    public boolean insertedData(Data data) {
        long rowId = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
        //    contentValues.put("id", data.getId());
            contentValues.put("data", data.getData());
            contentValues.put("data_type", data.getData_type());
            contentValues.put("sendDataTime", data.getSendDataTime());
            contentValues.put("senderId", data.getSenderId());
            contentValues.put("reciverId", data.getReciverId());
            rowId = db.insert("data_table", null, contentValues);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowId != -1;
    }




    public List<User> getAllUsers2() {
        List<User> userList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "Select id,name,number ,profileImage from user_table  group by id ";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userList.add(new User(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getInt(cursor.getColumnIndex("profileImage"))

                        ));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
    public List<User> getAllUsersByReceiverId() {
        List<User> userList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
//            String sql = "Select distinct(user_table.id)," +
//                    "user_table.name,user_table.number,user_table.profileImage" +
//                    " from user_table inner join data_table on user_table.id=data_table.reciverId ";
            String sql = "Select distinct(user_table.name)," +
                    "user_table.id,user_table.number,user_table.profileImage" +
                    " from user_table inner join data_table on user_table.id=data_table.reciverId group by user_table.id ";


            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userList.add(new User(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getInt(cursor.getColumnIndex("profileImage"))

                ));
                cursor.moveToNext();
            }
            Log.e("users",userList.toString());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public List<Data> getAllData(int reciverId) {
        List<Data> dataList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String sql="select * from data_table where reciverId="+reciverId;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dataList.add(new Data(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("data")),
                        cursor.getInt(cursor.getColumnIndex("data_type")),
                        cursor.getInt(cursor.getColumnIndex("senderId")),
                        cursor.getString(cursor.getColumnIndex("sendDataTime")),
                        cursor.getInt(cursor.getColumnIndex("reciverId"))

                ));
                cursor.moveToNext();
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
    public List<Data> getAllMessages() {
        List<Data> dataList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String sql="select * from data_table";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dataList.add(new Data(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("data")),
                        cursor.getInt(cursor.getColumnIndex("data_type")),
                        cursor.getInt(cursor.getColumnIndex("senderId")),
                        cursor.getString(cursor.getColumnIndex("sendDataTime")),
                        cursor.getInt(cursor.getColumnIndex("reciverId"))

                ));
                Log.e("DataTable", cursor.getString(cursor.getColumnIndex("data")));
                cursor.moveToNext();
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
