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

    public static final String CREATE_Student="create table student("
            +"id integer primary key autoincrement,"
            + "stuName text, "
            + "stuPassword text)" ;


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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Student);

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
        db.execSQL("create table cuotiku (id integer primary key,content VARCHAR(255)," +
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

