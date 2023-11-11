package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Onclick2(View view){
        Intent intent = new Intent(MainActivity.this, PracticeActicity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mainOnClick2(View view){
        Intent intent = new Intent(MainActivity.this, KnowledgemapActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mainOnClick3(View view){
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mainOnClick4(View view){
        Intent intent = new Intent(MainActivity.this, Main1_1Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mainOnClick5(View view){
        Intent intent = new Intent(MainActivity.this, Main1_2Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mainOnClick6(View view){
        Intent intent = new Intent(MainActivity.this, Main1_3Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mainOnClick7(View view){
        Intent intent = new Intent(MainActivity.this, Main1_4Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }
}