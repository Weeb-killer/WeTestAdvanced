package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdGradeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdgrade);
    }

    public void tOnclick1(View view){
        Intent intent = new Intent(ThirdGradeActivity.this,PracticeActicity.class);
        startActivity(intent);
    }

    public void Onclick3_1(View view){
        Intent intent = new Intent(ThirdGradeActivity.this,Chapter3_1Activity.class);
        startActivity(intent);
    }

    public void Onclick3_2(View view){
        Intent intent = new Intent(ThirdGradeActivity.this,Chapter3_2Activity.class);
        startActivity(intent);
    }
}
