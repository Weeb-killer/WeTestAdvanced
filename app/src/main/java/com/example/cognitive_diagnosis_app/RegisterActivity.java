package com.example.cognitive_diagnosis_app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RegisterActivity extends Activity {


    private EditText et_name,et_pass;
    private String username,user_password;
    private MyDatabaseHelper mdb;
    private User view;
    private Permission permission;
    Animation middleAnimation;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==permission.REQUEST_CODE){
            for (int i=0;i<grantResults.length;i++){
                if (grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                    Log.e("permission",permissions[i]+"not");
                    Log.e("permission",grantResults[i]+"not");
                }else {
                    Log.e("permission",permissions[i]);
                    Log.e("permission",grantResults[i]+"");
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        middleAnimation= AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        permission=new Permission();
        permission.checkPermission(this);
        et_name=findViewById(R.id.et1_name);
        et_pass=findViewById(R.id.et2_password);
        et_name.setAnimation(middleAnimation);
        et_pass.setAnimation(middleAnimation);
        findViewById(R.id.r).setAnimation(middleAnimation);
        findViewById(R.id.l).setAnimation(middleAnimation);

        MyDatabaseHelper mydatabasehelper=new MyDatabaseHelper(this);

        mdb=new MyDatabaseHelper(RegisterActivity.this);
        mdb.getWritableDatabase();
    }
    public void OnClick_d(View view) {

        username=et_name.getText().toString();
        user_password=et_pass.getText().toString();
        Boolean success=false;
        if (username.isEmpty() || user_password.isEmpty()){
            Toast.makeText(RegisterActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
            success=false;
        }else {
            ArrayList<User> data=mdb.getAllData();
            for(int i=0;i<data.size();i++){
                User user=data.get(i);
                if (username.equals(user.getName()) && user_password.equals(user.getPassword())){
                    success=true;
                    break;
                }else {
                    success=false;
                }
            }
        }
        if (success){
            Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }else {
            Toast.makeText(RegisterActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnClick_z(View view) {
        Intent intent = new Intent(RegisterActivity.this, zhuceActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);

    }

}
