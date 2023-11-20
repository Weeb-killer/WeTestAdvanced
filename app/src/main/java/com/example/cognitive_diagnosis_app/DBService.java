package com.example.cognitive_diagnosis_app;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DBService {
    File saveFile;
    private SQLiteDatabase db;
    public DBService(){
        db=SQLiteDatabase.openDatabase("/data/data/com.example.cognitive_diagnosis_app/databases/student.db",null,SQLiteDatabase.OPEN_READWRITE);
    }


    //为了发csva
    class MyThread extends Thread{

        // 步骤2：复写run（），内容 = 定义线程行为
        public MyThread(){}
        @Override
        public void run(){
            sendcsva();
            // 定义的线程行为
        }
    }

    // 步骤3：创建线程对象，即 实例化线程类
    MyThread mt=new MyThread();


    @SuppressLint("Range")
    public List<question> getQuestion(){
        List<question> list=new ArrayList<question>();
        Cursor cursor=db.rawQuery("select * from timutimu",null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            int count=cursor.getCount();
            int[] ints=Rnumber();


            String[] strings=new String[ints.length];
            for (int i2=0;i2<ints.length;i2++){
                strings[i2]= String.valueOf(ints[i2]);
            }

            Cursor cursor2=db.rawQuery("select Search_Algorithm,sorting_algorithm,Link_List,queue,stack,array,tree,graph from timutimu where id in (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new String[]{strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9],strings[10],strings[11],strings[12],strings[13],strings[14]});
            ExportToCSV(cursor2,"a.csv");
            mt.start();

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
        Cursor cursor=db.rawQuery("select * from cuotiku",null);

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
        int n = 32;   //这个数要大于随机数范围最大值
        int ra = 0;   //作为结果的随机数
        int[] ints = new int[15];
        boolean[] booleans = new boolean[n];  //布尔数组里每个坑默认为false
        for (int i = 0; i < 15; i++) {    //i是多少就有多少题
            do {
                ra = random.nextInt(30);
            } while (booleans[ra]);//当这个坑是true时，函数无法脱离循环继续向下进行，不得不重新随机
            booleans[ra] = true;
            ints[i] = ra;
        }
        return ints;
    }




    public void ExportToCSV(Cursor c, String fileName) {

        int rowCount = 0;
        int colCount = 0;
        FileWriter fw;
        BufferedWriter bfw;
        //保存文件目录
        saveFile = new File("/data/data/com.example.cognitive_diagnosis_app", fileName);
        try {
            rowCount = c.getCount();
            colCount = c.getColumnCount();
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                c.moveToFirst();
                // 写入表头
                for (int i = 0; i < colCount; i++) {
                    if (i != colCount - 1)
                        bfw.write(c.getColumnName(i) + ',');
                    else
                        bfw.write(c.getColumnName(i));
                }
                // 写好表头后换行
                bfw.newLine();
                // 写入数据
                for (int i = 0; i < rowCount; i++) {
                    c.moveToPosition(i);
                    for (int j = 0; j < colCount; j++) {
                        if (j != colCount - 1)
                            bfw.write(c.getString(j) + ',');
                        else
                            bfw.write(c.getString(j));
                    }
                    // 写好每条记录后换行
                    bfw.newLine();
                }
            }
            // 将缓存数据写入文件
            bfw.flush();
            // 释放缓存
            bfw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            c.close();
        }
    }




    //发送csva

    int port;
    private void sendcsva(){
        try {
            System.out.println("try send csva now");
            Path path = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                path = Paths.get(saveFile.toURI());
            }

            byte[] bytes = new byte[15];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Files.readAllBytes(path);
            }
            System.out.println(bytes);
            String content = new String(bytes);
            System.out.println(content);

            Socket socket = new Socket("10.252.157.202", 8080);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            System.out.println("socket a success");
            socket.close();
        }catch (Exception e){
            System.out.println("a fail");
            System.out.println(e);
        }
    }
}
