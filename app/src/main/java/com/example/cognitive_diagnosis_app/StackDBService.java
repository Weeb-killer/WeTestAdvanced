package com.example.cognitive_diagnosis_app;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackDBService {
    private SQLiteDatabase db;
    public StackDBService(){
        db=SQLiteDatabase.openDatabase("/data/data/com.example.cognitive_diagnosis_app/databases/student.db",null,SQLiteDatabase.OPEN_READWRITE);
    }
    @SuppressLint("Range")
    public List<question> getQuestion(){
        List<question> list=new ArrayList<question>();
        Cursor cursor=db.rawQuery("select * from stack",null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            int count=cursor.getCount();
            int[] ints=Rnumber();


            String[] strings=new String[ints.length];
            for (int i2=0;i2<ints.length;i2++){
                strings[i2]= String.valueOf(ints[i2]);
            }

            for(int i=0;i<ints.length;i++){
                cursor.moveToPosition(ints[i]);
                question ques=new question();
                ques.ID=cursor.getInt(cursor.getColumnIndex("id"));
                ques.question=cursor.getString(cursor.getColumnIndex("content"));
                ques.answerA=cursor.getString(cursor.getColumnIndex("option_A"));
                ques.answerB=cursor.getString(cursor.getColumnIndex("option_B"));
                ques.answerC=cursor.getString(cursor.getColumnIndex("option_C"));
                ques.answerD=cursor.getString(cursor.getColumnIndex("option_D"));
                ques.answer=cursor.getInt(cursor.getColumnIndex("correct_ot"));
                ques.explaination=cursor.getString(cursor.getColumnIndex("explanation"));
                ques.selectedAnswer=-1;
                list.add(ques);

            }
        }
        return list;
    }


    @SuppressLint("Range")
    public List<question> getwrongQuestion(){
        List<question> list=new ArrayList<question>();
        Cursor cursor=db.rawQuery("select * from anothercuotiku",null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            int count=cursor.getCount();

            for(int i=0;i<count;i++){
                cursor.moveToPosition(i);
                question ques=new question();
                ques.ID=cursor.getInt(cursor.getColumnIndex("id"));
                ques.question=cursor.getString(cursor.getColumnIndex("content"));
                ques.answerA=cursor.getString(cursor.getColumnIndex("option_A"));
                ques.answerB=cursor.getString(cursor.getColumnIndex("option_B"));
                ques.answerC=cursor.getString(cursor.getColumnIndex("option_C"));
                ques.answerD=cursor.getString(cursor.getColumnIndex("option_D"));
                ques.answer=cursor.getInt(cursor.getColumnIndex("correct_ot"));
                ques.explaination=cursor.getString(cursor.getColumnIndex("explanation"));
                ques.selectedAnswer=-1;
                list.add(ques);
            }

        }
        return list;

    }


    private int[] Rnumber() {
        Random random = new Random();
        int n = 2;   //这个数要大于随机数范围最大值
        int ra = 0;   //作为结果的随机数
        int[] ints = new int[15];
        boolean[] booleans = new boolean[n];  //布尔数组里每个坑默认为false
        for (int i = 0; i < 2; i++) {    //i是多少就有多少题
            do {
                ra = random.nextInt(2);
            } while (booleans[ra]);//当这个坑是true时，函数无法脱离循环继续向下进行，不得不重新随机
            booleans[ra] = true;
            ints[i] = ra;
        }
        return ints;
    }
}
