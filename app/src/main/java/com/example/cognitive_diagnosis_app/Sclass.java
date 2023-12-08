package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Sclass extends AppCompatActivity {
    private Button joinclass,checkclass;
    MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sclass);
        joinclass=findViewById(R.id.joinClass);
        checkclass=findViewById(R.id.stuCheckClass);
        myDatabaseHelper=new MyDatabaseHelper(Sclass.this,"student.db",null,1);
        db=myDatabaseHelper.getWritableDatabase();
        textView=findViewById(R.id.sshowclass);



        joinclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText inputServer = new EditText(Sclass.this);
                AlertDialog.Builder builder=new AlertDialog.Builder(Sclass.this);
                builder.setTitle("请输入班级id");
                builder.setIcon(R.drawable.tlogo);
                builder.setView(inputServer);

                builder.setPositiveButton("加入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean CanAdd=false;
                        Cursor stucursor=db.query("student",null,null,null,null,null,"id DESC");
                        if (stucursor.moveToFirst()){
                            do {
                                @SuppressLint("Range") String stu_id = stucursor.getString(stucursor.getColumnIndex("id"));

                                if (stu_id.equals(String.valueOf(RegisterActivity.stuid))){
                                    for (int i=1;i<6;i++){
                                        @SuppressLint("Range") String classid = stucursor.getString(stucursor.getColumnIndex("class"+i));

                                        if (classid==null){
                                            ContentValues value=new ContentValues();
                                            value.put("class"+i,inputServer.getText().toString());
                                            db.update("student",value,"id=?",new String[]{RegisterActivity.stuid});
                                            Toast.makeText(Sclass.this,"学生记录班级成功",Toast.LENGTH_SHORT).show();
                                            CanAdd=true;
                                            break;
                                        }
                                        if (i==5){Toast.makeText(Sclass.this,"无法加入，学生加入班级数量已达到最大",Toast.LENGTH_SHORT).show();}
                                    }
                                    break;
                                }
                            }while(stucursor.moveToNext());
                            stucursor.close();

                        }

                        if (CanAdd){
                            Cursor cursor=db.query("banjiku",null,null,null,null,null,"id DESC");

                            if (cursor.moveToFirst()){
                                do {
                                    @SuppressLint("Range") String class_id = cursor.getString(cursor.getColumnIndex("id"));

                                    if (inputServer.getText().toString().equals(class_id)){
                                        for (int i=1;i<11;i++){
                                            @SuppressLint("Range") String sstuid = cursor.getString(cursor.getColumnIndex("stuid"+i));
                                            if (sstuid==null){
                                                ContentValues value=new ContentValues();
                                                value.put("stuid"+i,RegisterActivity.stuid);
                                                db.update("banjiku",value,"id=?",new String[]{inputServer.getText().toString()});
                                                Toast.makeText(Sclass.this,"加入班级成功",Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            if (i==10){Toast.makeText(Sclass.this,"该班级已满",Toast.LENGTH_SHORT).show();}
                                        }


                                    }
                                }while(cursor.moveToNext());
                            }
                            cursor.close();
                        }
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });

        checkclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder content=new StringBuilder();
                String[] s=new String[5];

                Cursor cursor=db.query("student",null,null,null,null,null,"id DESC");
                if (cursor.moveToFirst()){
                    do {
                        @SuppressLint("Range") String stuid = cursor.getString(cursor.getColumnIndex("id"));
                        if (stuid.equals(RegisterActivity.stuid)){
                            Toast.makeText(Sclass.this,"equal",Toast.LENGTH_SHORT).show();
                            for (int i=1;i<6;i++){
                                @SuppressLint("Range") String classid = cursor.getString(cursor.getColumnIndex("class"+i));
                                    s[i-1]=classid;
                            }
                        }
                    }while(cursor.moveToNext());
                }

                cursor.close();
                Cursor cursor2=db.query("banjiku",null,null,null,null,null,"id DESC");
                if (cursor2.moveToFirst()){
                    do {
                        @SuppressLint("Range") String banji_id = cursor2.getString(cursor2.getColumnIndex("id"));
                        for (int i=0;i<5;i++){
                            if (s[i]!=null){
                                if (s[i].equals(banji_id)){
                                    Toast.makeText(Sclass.this,"here",Toast.LENGTH_SHORT).show();
                                    @SuppressLint("Range") String teacher_id = cursor2.getString(cursor2.getColumnIndex("teacher_id"));
                                    @SuppressLint("Range") String class_name = cursor2.getString(cursor2.getColumnIndex("class_name"));
                                    @SuppressLint("Range") String stuid1 = cursor2.getString(cursor2.getColumnIndex("stuid1"));
                                    @SuppressLint("Range") String stuid2 = cursor2.getString(cursor2.getColumnIndex("stuid2"));
                                    @SuppressLint("Range") String stuid3 = cursor2.getString(cursor2.getColumnIndex("stuid3"));
                                    @SuppressLint("Range") String stuid4 = cursor2.getString(cursor2.getColumnIndex("stuid4"));
                                    @SuppressLint("Range") String stuid5 = cursor2.getString(cursor2.getColumnIndex("stuid5"));
                                    @SuppressLint("Range") String stuid6 = cursor2.getString(cursor2.getColumnIndex("stuid6"));
                                    @SuppressLint("Range") String stuid7 = cursor2.getString(cursor2.getColumnIndex("stuid7"));
                                    @SuppressLint("Range") String stuid8 = cursor2.getString(cursor2.getColumnIndex("stuid8"));
                                    @SuppressLint("Range") String stuid9 = cursor2.getString(cursor2.getColumnIndex("stuid9"));
                                    @SuppressLint("Range") String stuid10 = cursor2.getString(cursor2.getColumnIndex("stuid10"));

                                    content.append("class id:"+banji_id+"\t\t"+"teacher id:"+teacher_id+"\t\t"+"class name:"+class_name+"\n\n"+"student:"+"\n\n");
                                    content.append(stuid1+"\t\t\t\t"+stuid2+"\t\t\t\t"+stuid3+"\t\t\t\t"+stuid4+"\n");
                                    content.append(stuid5+"\t\t\t\t"+stuid6+"\t\t\t\t"+stuid7+"\t\t\t\t"+stuid8+"\n");
                                    content.append(stuid9+"\t\t\t\t"+stuid10+"\n\n\n\n" );
                                    content.append("__________________________________");
                                }
                            }
                        }

                    }while (cursor2.moveToNext());
                }
                cursor2.close();
                textView.setText(content.toString());
            }
        });
    }
}