package com.thundersoft.music;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "music.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建音乐信息表
        sqLiteDatabase.execSQL("create table music_info(id integer primary key autoincrement, name text, artist text, album text, duration integer, path text, is_local integer)");
        //创建用户信息表
        sqLiteDatabase.execSQL("create table user_info(id integer primary key autoincrement, username text, password text,email text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //新增用户信息
    public void register(String username,String password,String email){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into user_info(username,password,email) values(?,?,?)",new String[]{username,password,email});
        db.close();
    }
    //查询用户名是否存在
    public boolean isUserExist(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info where username=?",new String[]{username});
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }
    //查询用户名和密码是否匹配
    public boolean isUserMatch(String username,String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info where username=? and password=?",new String[]{username,password});
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }

}
