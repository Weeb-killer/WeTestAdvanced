package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class zonghelianxi extends Activity {

    private MyDatabaseHelper dbHelper;

    private int count;//题目总数
    private int current;//当前做题位置
    private boolean wrongMode;//错题

    File savefile;

    //为了发csvb
    class MyThread extends Thread{

        // 步骤2：复写run（），内容 = 定义线程行为
        public MyThread(){}
        @Override
        public void run(){
            sendcsvb();
            // 定义的线程行为
        }
    }
    MyThread bb=new MyThread();

    /*
    加入题目按这个来
    数据库的命令在vip.sql
    DBService是自创的模块 作为调用sql语句的api
    对每一个要加题目的part 都新建一个DBService 但数据库只有一个
    直接在原有的数据库里面添加新的表
            by Weeb-killer
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonghelianxi);

        DBService dbService=new DBService();//打开数据库，变量名字为dbService
        final List<question> list=dbService.getQuestion();//list 存题目

        count=15;//每次做题的题目数量
        current=0;
        wrongMode=false;
        final TextView tv_question = findViewById(R.id.question);
        final RadioButton[] radioButtons=new RadioButton[4];
        radioButtons[0]=findViewById(R.id.answerA);
        radioButtons[1] = findViewById(R.id.answerB);
        radioButtons[2] = findViewById(R.id.answerC);
        radioButtons[3] = findViewById(R.id.answerD);

        Button btn_previous = findViewById(R.id.btn_previous);
        Button btn_next = findViewById(R.id.btn_next);

        final TextView zhishidian = findViewById(R.id.zhishidian);
        TextView answer=findViewById(R.id.answer);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);

        question q=list.get(0);//第一个问题
        tv_question.setText(q.question);
        zhishidian.setText(q.explaination);//设置默认隐藏的知识点
        if (q.answer==2){
            answer.setText("答案：B");
        }else if (q.answer==1){
            answer.setText("答案：A");
        }else if (q.answer==3){
            answer.setText("答案：C");
        }else{answer.setText("答案：D");}

        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);

        //点击下一题时
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检查是否到最后一题，没到就位置+1
                if(current<count-1){
                    current++;
                    question q=list.get(current);
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    zhishidian.setText(q.explaination);
                    if (q.answer==2){
                        answer.setText("答案：B");
                    }else if (q.answer==1){
                        answer.setText("答案：A");
                    }else if (q.answer==3){
                        answer.setText("答案：C");
                    }else{answer.setText("答案：D");}


                    radioGroup.clearCheck();
                    if (q.selectedAnswer!=-1){
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }
                }
                else if(current==count-1 && wrongMode==true){  //点击下一题时，已经做完了了最后一道错题，且错题模式已完成
                    new AlertDialog.Builder(zonghelianxi.this).setTitle("提示").setMessage("答题已完成，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    zonghelianxi.this.finish();
                                }
                            }).setNegativeButton("取消",null).show();
                }
                else{  //做完了题目但还没进去错题模式
                    final List<Integer> wrongList=checkAnswer(list);



                    //生成csv
                    FileWriter fw;
                    BufferedWriter bfw;
                    savefile=new File("/data/data/com.example.cognitive_diagnosis_app","b.csv");
                    try{
                        fw = new FileWriter(savefile);
                        bfw = new BufferedWriter(fw);
                        for(int i=0;i<list.size();i++){
                            if (i!=list.size()-1){
                                bfw.write(list.get(i).ID+",");
                            }else {bfw.write(String.valueOf(list.get(i).ID));}
                        }
                        bfw.newLine();
                        for (int i=0;i<list.size();i++){
                            if (i!=0 && i%5==0){
                                bfw.newLine();
                            }
                            if (i!=list.size()-1){
                                if (wrongList.contains(i)){
                                    bfw.write("0"+',');
                                }else {bfw.write("1"+',');}
                            }else {
                                if (wrongList.contains(i)){
                                    bfw.write("0");
                                }else {bfw.write("1");}
                            }
                        }
                        bfw.flush();
                        bfw.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    bb.start();



                    //加入错题本
                    for(int i=0;i<list.size();i++){
                        if (wrongList.contains(i)){
                            SQLiteDatabase db=SQLiteDatabase.openDatabase("/data/data/com.example.cognitive_diagnosis_app/databases/student.db",null,SQLiteDatabase.OPEN_READWRITE);
                            ContentValues values=new ContentValues();
                            values.put("id",list.get(i).ID);
                            values.put("content",list.get(i).question);
                            values.put("option_A",list.get(i).answerA);
                            values.put("option_B",list.get(i).answerB);
                            values.put("option_C",list.get(i).answerC);
                            values.put("option_D",list.get(i).answerD);
                            values.put("correct_ot",list.get(i).answer);
                            values.put("explanation",list.get(i).explaination);

                            db.insert("cuotiku",null,values);
                        }

                    }


                    if(wrongList.size()==0){new AlertDialog.Builder(zonghelianxi.this) //全对时
                            .setTitle("提示")
                            .setMessage("恭喜你全部回答正确！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    zonghelianxi.this.finish();
                                }
                            }).show();
                    }
                    else //没有全对时
                        new AlertDialog.Builder(zonghelianxi.this)
                                .setTitle("提示")
                                .setMessage("您答对了"+(list.size()-wrongList.size())+
                                        "道题目；答错了"+wrongList.size()+"道题目。是否查看错题？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {

                                        //判断进入错题模式
                                        wrongMode=true;
                                        List<question> newList=new ArrayList<question>();
                                        //将错误题目复制到newList中
                                        for(int i=0;i< wrongList.size();i++){
                                            newList.add(list.get(wrongList.get(i)));
                                        }
//
                                        current=0;
                                        count=newList.size();
                                        //更新显示时的内容
                                        question q = newList.get(current);
                                        tv_question.setText(q.question);
                                        radioButtons[0].setText(q.answerA);
                                        radioButtons[1].setText(q.answerB);
                                        radioButtons[2].setText(q.answerC);
                                        radioButtons[3].setText(q.answerD);
                                        zhishidian.setText(q.explaination);
                                        //显示解析

                                        zhishidian.setVisibility(View.VISIBLE);
                                        answer.setVisibility(View.VISIBLE);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
//点击取消时，关闭当前activity
                                        zonghelianxi.this.finish();
                                    }
                                }).show();
                }
            }
        });
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current > 0)//若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
                {
                    current--;
                    question q = list.get(current);
                    tv_question.setText(q.question);
                    radioButtons[0].setText(q.answerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    zhishidian.setText(q.explaination);
                    if (q.answer==2){
                        answer.setText("答案：B");
                    }else if (q.answer==1){
                        answer.setText("答案：A");
                    }else if (q.answer==3){
                        answer.setText("答案：C");
                    }else{answer.setText("答案：D");}

                    //若之前已经选择过，则应记录选择
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);
                    }

                }
            }
        });
        //选择选项时更新选择，选择哪个selectedAnswer改成哪个数字

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (radioButtons[i].isChecked() == true) {
                        list.get(current).selectedAnswer = i;
                        break;
                    }
                }

            }
        });
    }


    private List<Integer> checkAnswer(List<question> list){
        List<Integer> wrongList=new ArrayList<Integer>();
        for (int i=0;i<list.size();i++){
            if (list.get(i).answer!=list.get(i).selectedAnswer){
                wrongList.add(i);
            }
        }
        return wrongList;
    }

    //发csv_b的方法
    int port;
    private void sendcsvb(){
        try {
            System.out.println("try send csvb now");
            Path path = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                path = Paths.get(savefile.toURI());
            }

            byte[] bytes = new byte[15];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Files.readAllBytes(path);
            }
            System.out.println(bytes);
            String content = new String(bytes);
            System.out.println(content);
            Socket socket=new Socket("10.252.157.202",8080);
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write(bytes);


            socket.close();
            System.out.println("send csvb success");
        }catch (Exception e){
            System.out.println("socket send csvb fail");
            System.out.println(e);
        }
    }



}

