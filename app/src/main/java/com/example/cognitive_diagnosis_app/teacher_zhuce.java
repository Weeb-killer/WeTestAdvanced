package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class teacher_zhuce extends AppCompatActivity {


    private EditText tetName,tetPassw,etConfirmPass;
    private Button tbtConfirm,tbtbt;
    private MyDatabaseHelper dbHelper;
    private TextView ttextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_zhuce);

        tetName=findViewById(R.id.tec_et1_register_username);
        tetPassw=findViewById(R.id.tec_et2_register_password);
        etConfirmPass=findViewById(R.id.tec_et3_register_confirmPassw);
        tbtConfirm=findViewById(R.id.tec_bt1_register);
        tbtbt=findViewById(R.id.tec_bt1_register22);
        ttextView=findViewById(R.id.tec_tv);
        dbHelper=new MyDatabaseHelper(teacher_zhuce.this,"student.db",null,1);

        tbtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tetName.getText().length()>6 && tetPassw.getText().length()>6){
                    ArrayList<User> list=dbHelper.tgetAllData();
                    Boolean registered=false;
                    for(int i =0;i<list.size();i++){
                        User user=list.get(i);
                        if (tetName.getText().toString().equals(user.getName())){
                            Toast.makeText(teacher_zhuce.this, "该账号已被注册", Toast.LENGTH_SHORT).show();
                            registered=true;
                            break;
                        }
                    }
                    if (registered!=true){
                        if (etConfirmPass.getText().toString().equals(tetPassw.getText().toString())){
                            SQLiteDatabase db=dbHelper.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("tecName",tetName.getText().toString());
                            values.put("tecPassword",tetPassw.getText().toString());
                            db.insert("teacher",null,values);
                            Toast.makeText(teacher_zhuce.this, "教师已注册成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(teacher_zhuce.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(teacher_zhuce.this, "用户名或密码需六字以上", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tbtbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.query("teacher",null,null,null,null,null,null);
                StringBuilder content=new StringBuilder();
                content.append("id"+"\t\t"+"tecName"+"\t\t"+"password\n");
                if (cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range") String stuId=cursor.getString(cursor.getColumnIndex("id"));
                        @SuppressLint("Range") String stuName=cursor.getString(cursor.getColumnIndex("tecName"));
                        @SuppressLint("Range") String password=cursor.getString(cursor.getColumnIndex("tecPassword"));
                        content.append(stuId+"\t\t"+stuName+"\t\t"+password+"\n");
                    }while(cursor.moveToNext());
                }
                cursor.close();
                ttextView.setText(content.toString());
            }
        });



    }
}