package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    View Sun,Builing,first,second,third,fourth,fifth;
    Animation topAnimation,bottomAnimation,middleAnimation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleAnimation= AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);
        fifth=findViewById(R.id.fifth);
        Sun=findViewById(R.id.Sun);
        Builing=findViewById(R.id.building);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        Sun.setAnimation(middleAnimation);
        Builing.setAnimation(bottomAnimation);
    }
    public void eOnclick1(View view){
        Intent intent = new Intent(EditActivity.this,UserActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }
}
