package com.example.cognitive_diagnosis_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Camera;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private SQLiteDatabase db;



    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
        db=getReadableDatabase();
    }

    public MyDatabaseHelper(Context context) {
        super(context,"student.db",null,1);
        mContext=context;
        db=getReadableDatabase();

    }

    //为了空间换时间，学生暂时只能同时加入五个班，从此只能增加，难以无限
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table student("
                +"id integer primary key autoincrement,"
                + "stuName text, "
                + "stuPassword text,class1 integer, class2 integer,class3 integer,class4 integer,class5 integer)");
        //张哥，这是存老师用户的账户和密码表
        //为了空间换时间以及避免不必要的麻烦，老师只能同时拥有五个班，可以轻易增加，难以变为无限
        db.execSQL("create table teacher(id integer primary key autoincrement,tecName text,tecPassword text,class VARCHAR(90),class1 integer,class2 integer,class3 integer,class4 integer,class5 integer)");

        //存放班级的表,一个班级暂定只有十个学生
        db.execSQL("create table banjiku(id integer primary key autoincrement,class_name VARCHAR(255)," +
                "teacher_id integer,stuid1 integer,stuid2 integer," +
                "stuid3 integer,stuid4 integer,stuid5 integer,stuid6 integer,stuid7 integer,stuid8 integer,stuid9 integer,stuid10 integer)");
        try {
            InputStream in = mContext.getAssets().open("vip.sql");
            String sqlUpdate = null;
            try {
                sqlUpdate = readTextFromSDcard(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] s = sqlUpdate.split(";");
            for (int i = 0; i < s.length; i++) {
                if (!TextUtils.isEmpty(s[i])) {
                    db.execSQL(s[i]);
                }
            }
            in.close();
        } catch (SQLException e) {
        } catch (IOException e) {
        }

        //错题库
        db.execSQL("create table cuotiku (id integer primary key autoincrement,content VARCHAR(255)," +
                "option_A VARCHAR(255),option_B VARCHAR(255),option_C VARCHAR(255)," +
                "option_D VARCHAR(255),correct_ot integer,explanation VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);

    }
    public void add(String name,String password ){
        db.execSQL("INSERT INTO user(name,password)VALUES(?,?)",new Object[]{name,password});
    }

    public ArrayList<User> getAllData(){
        ArrayList<User> list=new ArrayList<>();
        Cursor cursor=db.query("student",null,null,null,null,null,"stuName DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("stuName"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("stuPassword"));
            list.add(new User(name,password));
        }
        return list;
    }


    public ArrayList<User> tgetAllData(){
        ArrayList<User> list=new ArrayList<>();
        Cursor cursor=db.query("teacher",null,null,null,null,null,"tecName DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("tecName"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("tecPassword"));
            list.add(new User(name,password));
        }
        return list;
    }


    private String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}

