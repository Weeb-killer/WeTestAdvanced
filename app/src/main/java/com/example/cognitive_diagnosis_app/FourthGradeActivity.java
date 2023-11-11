package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FourthGradeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourthgrade);
    }

    public void fOnclick1(View view){
        Intent intent = new Intent(FourthGradeActivity.this,PracticeActicity.class);
        startActivity(intent);
    }

    public void Onclick4_1(View view){
        Intent intent = new Intent(FourthGradeActivity.this,Chapter4_1Activity.class);
        startActivity(intent);
    }

    public void Onclick4_2(View view){
        Intent intent = new Intent(FourthGradeActivity.this,Chapter4_2Activity.class);
        startActivity(intent);
    }

    public void Onclick4_3(View view){
        Intent intent = new Intent(FourthGradeActivity.this,Chapter4_3Activity.class);
        startActivity(intent);
    }

    public void Onclick4_4(View view){
        Intent intent = new Intent(FourthGradeActivity.this,Chapter4_4Activity.class);
        startActivity(intent);
    }
}
