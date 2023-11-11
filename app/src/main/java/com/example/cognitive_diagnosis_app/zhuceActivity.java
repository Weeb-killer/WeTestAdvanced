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

public class zhuceActivity extends AppCompatActivity {

    private EditText etName,etPassw,etConfirmPass;
    private Button btConfirm,btbt;
    private MyDatabaseHelper dbHelper;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);


        etName=findViewById(R.id.et1_register_username);
        etPassw=findViewById(R.id.et2_register_password);
        etConfirmPass=findViewById(R.id.et3_register_confirmPassw);
        btConfirm=findViewById(R.id.bt1_register);
        btbt=findViewById(R.id.bt1_register22);
        textView=findViewById(R.id.tv);
        dbHelper=new MyDatabaseHelper(zhuceActivity.this,"student.db",null,1);


        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().length()>6 && etPassw.getText().length()>6){
                    ArrayList<User> list=dbHelper.getAllData();
                    Boolean registered=false;
                    for(int i =0;i<list.size();i++){
                        User user=list.get(i);
                        if (etName.getText().toString().equals(user.getName())){
                            Toast.makeText(zhuceActivity.this, "该账号已被注册", Toast.LENGTH_SHORT).show();
                            registered=true;
                            break;
                        }
                    }
                    if (registered!=true){
                        if (etConfirmPass.getText().toString().equals(etPassw.getText().toString())){
                            SQLiteDatabase db=dbHelper.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("stuName",etName.getText().toString());
                            values.put("stuPassword",etPassw.getText().toString());
                            db.insert("student",null,values);
                            Toast.makeText(zhuceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(zhuceActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(zhuceActivity.this, "用户名或密码需六字以上", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.query("student",null,null,null,null,null,null);
                StringBuilder content=new StringBuilder();
                content.append("id"+"\t\t"+"stuName"+"\t\t"+"password\n");
                if (cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range") String stuId=cursor.getString(cursor.getColumnIndex("id"));
                        @SuppressLint("Range") String stuName=cursor.getString(cursor.getColumnIndex("stuName"));
                        @SuppressLint("Range") String password=cursor.getString(cursor.getColumnIndex("stuPassword"));
                        content.append(stuId+"\t\t"+stuName+"\t\t"+password+"\n");
                    }while(cursor.moveToNext());
                }
                cursor.close();
                textView.setText(content.toString());
            }
        });



    }
}