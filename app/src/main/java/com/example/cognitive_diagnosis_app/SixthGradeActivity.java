package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SixthGradeActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixthgrade);
    }

    public void sixOnclick1(View view){
        Intent intent = new Intent(SixthGradeActivity.this,PracticeActicity.class);
        startActivity(intent);
    }

    public void Onclick6_1(View view){
        Intent intent = new Intent(SixthGradeActivity.this,Chapter6_1Activity.class);
        startActivity(intent);
    }

    public void Onclick6_2(View view){
        Intent intent = new Intent(SixthGradeActivity.this,Chapter6_2Activity.class);
        startActivity(intent);
    }

    public void Onclick6_3(View view){
        Intent intent = new Intent(SixthGradeActivity.this,Chapter6_3Activity.class);
        startActivity(intent);
    }

    public void Onclick6_4(View view){
        Intent intent = new Intent(SixthGradeActivity.this,Chapter6_4Activity.class);
        startActivity(intent);
    }
}
