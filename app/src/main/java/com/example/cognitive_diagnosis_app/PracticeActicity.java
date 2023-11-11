package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PracticeActicity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
    }

    public void pOnclick7(View view){
        Intent intent = new Intent(PracticeActicity.this, MainActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick8(View view){
        Intent intent = new Intent(PracticeActicity.this, zonghelianxi.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick1(View view){
        Intent intent = new Intent(PracticeActicity.this, FirstGradeActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick2(View view){
        Intent intent = new Intent(PracticeActicity.this, SecondGradeActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick3(View view){
        Intent intent = new Intent(PracticeActicity.this, ThirdGradeActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick4(View view){
        Intent intent = new Intent(PracticeActicity.this, FourthGradeActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick5(View view){
        Intent intent = new Intent(PracticeActicity.this, FifthGradeActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void pOnclick6(View view){
        Intent intent = new Intent(PracticeActicity.this, SixthGradeActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

}
