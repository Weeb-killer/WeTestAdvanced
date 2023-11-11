package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SecondGradeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondgrade);
    }

    public void sOnclick1(View view){
        Intent intent = new Intent(SecondGradeActivity.this,PracticeActicity.class);
        startActivity(intent);
    }

    public void Onclick2_1(View view){
        Intent intent = new Intent(SecondGradeActivity.this,Chapter2_1Activity.class);
        startActivity(intent);
    }

    public void Onclick2_2(View view){
        Intent intent = new Intent(SecondGradeActivity.this,Chapter2_2Activity.class);
        startActivity(intent);
    }
}
