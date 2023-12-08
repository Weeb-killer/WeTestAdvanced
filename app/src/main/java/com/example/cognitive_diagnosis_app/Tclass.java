package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Tclass extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;

    private Spinner spinner;

    private Button createClass,deleteClass,shuaxin;

    private SQLiteDatabase db;

    private TextView showclass;




    //为了老师创建班级时修改下拉表的内容
    private String[] options = {"未创建班级", "未创建班级", "未创建班级","未创建班级","未创建班级"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tclass);
        spinner=findViewById(R.id.check_class);
        createClass=findViewById(R.id.create_class);
        deleteClass=findViewById(R.id.delete_class);
        shuaxin=findViewById(R.id.shuaxin);
        showclass=findViewById(R.id.showclass);

        myDatabaseHelper=new MyDatabaseHelper(Tclass.this,"student.db",null,1);
        db=myDatabaseHelper.getWritableDatabase();




        //改下拉表
        ModifyAdapter adapter=new ModifyAdapter(Tclass.this,options);
        spinner.setAdapter(adapter);

        //点创建班级后，弹窗要求设定班级名称，确认后创建班级并返回id以供学生加入班级。
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputServer = new EditText(Tclass.this);
                AlertDialog.Builder builder=new AlertDialog.Builder(Tclass.this);
                builder.setTitle("请输入班级名称");
                builder.setIcon(R.drawable.tlogo);
                builder.setView(inputServer);
                builder.setPositiveButton("创建", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Boolean add=true;


                        //改下拉列表
                        Cursor cursor=db.query("banjiku",null,null,null,null,null,"teacher_id DESC");
                        int class_num=0;
                        if (cursor.moveToFirst()){
                            do{
                                @SuppressLint("Range") String teacher_id = cursor.getString(cursor.getColumnIndex("teacher_id"));
                                @SuppressLint("Range") String class_name = cursor.getString(cursor.getColumnIndex("class_name"));

                                if (teacher_id.equals(String.valueOf(teacher_register.cur_teacher_id))){
                                    class_num+=1;
                                    if (class_num>=5){
                                        add=false;
                                        Toast.makeText(Tclass.this,"已达到最大创建班级数量",Toast.LENGTH_SHORT).show();
                                        break;};
                                }
                            }while(cursor.moveToNext());
                        }
                        if (add){
                            ContentValues value=new ContentValues();
                            value.put("class_name",inputServer.getText().toString());
                            value.put("teacher_id",teacher_register.cur_teacher_id);
                            db.insert("banjiku",null,value);
                            Toast.makeText(Tclass.this, "创建班级成功", Toast.LENGTH_SHORT).show();
                        }
                        cursor.close();

                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });

        deleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputServer = new EditText(Tclass.this);
                AlertDialog.Builder builder=new AlertDialog.Builder(Tclass.this);
                builder.setTitle("请输入删除班级名称");
                builder.setIcon(R.drawable.tlogo);
                builder.setView(inputServer);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.delete("banjiku","class_name=? and teacher_id=?",new String[]{inputServer.getText().toString(), String.valueOf(teacher_register.cur_teacher_id)});
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });

        shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<5;i++){options[i]="未创建班级";};
                int position=0;
                Cursor cursor=db.query("banjiku",null,null,null,null,null,"teacher_id DESC");
                if (cursor.moveToFirst()){
                    do {
                        @SuppressLint("Range") String teacher_id = cursor.getString(cursor.getColumnIndex("teacher_id"));
                        @SuppressLint("Range") String class_name = cursor.getString(cursor.getColumnIndex("class_name"));
                        if (teacher_id.equals(String.valueOf(teacher_register.cur_teacher_id))){
                            options[position]=class_name;
                            position+=1;
                        }
                    }while (cursor.moveToNext());
                }
                adapter.notifyDataSetChanged();
                cursor.close();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor=db.query("banjiku",null,null,null,null,null,"teacher_id DESC");
                StringBuilder content=new StringBuilder();
                while(cursor.moveToNext()){
                    @SuppressLint("Range") String teacher_id = cursor.getString(cursor.getColumnIndex("teacher_id"));
                    @SuppressLint("Range") String class_name = cursor.getString(cursor.getColumnIndex("class_name"));
                    if (teacher_id.equals(String.valueOf(teacher_register.cur_teacher_id))&&class_name.equals(parent.getSelectedItem().toString())){
                        @SuppressLint("Range") String banji_id = cursor.getString(cursor.getColumnIndex("id"));
                        @SuppressLint("Range") String stuid1 = cursor.getString(cursor.getColumnIndex("stuid1"));
                        @SuppressLint("Range") String stuid2 = cursor.getString(cursor.getColumnIndex("stuid2"));
                        @SuppressLint("Range") String stuid3 = cursor.getString(cursor.getColumnIndex("stuid3"));
                        @SuppressLint("Range") String stuid4 = cursor.getString(cursor.getColumnIndex("stuid4"));
                        @SuppressLint("Range") String stuid5 = cursor.getString(cursor.getColumnIndex("stuid5"));
                        @SuppressLint("Range") String stuid6 = cursor.getString(cursor.getColumnIndex("stuid6"));
                        @SuppressLint("Range") String stuid7 = cursor.getString(cursor.getColumnIndex("stuid7"));
                        @SuppressLint("Range") String stuid8 = cursor.getString(cursor.getColumnIndex("stuid8"));
                        @SuppressLint("Range") String stuid9 = cursor.getString(cursor.getColumnIndex("stuid9"));
                        @SuppressLint("Range") String stuid10 = cursor.getString(cursor.getColumnIndex("stuid10"));

                        content.append("class id:"+banji_id+"\t\t"+"teacher id:"+teacher_id+"\t\t"+"class name:"+class_name+"\n"+"student:"+"\n\n");
                        content.append(stuid1+"\t\t\t\t"+stuid2+"\t\t\t\t"+stuid3+"\t\t\t\t"+stuid4+"\n");
                        content.append(stuid5+"\t\t\t\t"+stuid6+"\t\t\t\t"+stuid7+"\t\t\t\t"+stuid8+"\n");
                        content.append(stuid9+"\t\t\t\t"+stuid10+"\n" );
                    }
                }
                cursor.close();
                showclass.setText(content.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });





    }
}