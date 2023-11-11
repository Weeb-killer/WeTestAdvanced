package com.example.cognitive_diagnosis_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstGradeActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstgrade);
    }

    public void cOnclick1(View view){
        Intent intent = new Intent(FirstGradeActivity.this, PracticeActicity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void Onclick1_1(View view){
        Intent intent = new Intent(FirstGradeActivity.this, Chapter1_1Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void Onclick1_2(View view){
        Intent intent = new Intent(FirstGradeActivity.this, Chapter1_2Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void Onclick1_3(View view){
        Intent intent = new Intent(FirstGradeActivity.this, Chapter1_3Activity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

}
