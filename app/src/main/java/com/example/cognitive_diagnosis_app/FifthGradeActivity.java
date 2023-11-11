package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FifthGradeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifthgrade);
    }

    public void ffOnclick1(View view){
        Intent intent = new Intent(FifthGradeActivity.this,PracticeActicity.class);
        startActivity(intent);
    }

    public void Onclick5_1(View view){
        Intent intent = new Intent(FifthGradeActivity.this,Chapter5_1Activity.class);
        startActivity(intent);
    }

    public void Onclick5_2(View view){
        Intent intent = new Intent(FifthGradeActivity.this,Chapter5_2Activity.class);
        startActivity(intent);
    }
}
