package com.example.cognitive_diagnosis_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class UserActivity extends Activity {
    Animation middleAnimation;
    protected void onCreate(Bundle savedInstanceState) {
        middleAnimation= AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);
        findViewById(R.id.wronganswer).setAnimation(middleAnimation);
        findViewById(R.id.uploadquestion).setAnimation(middleAnimation);
        findViewById(R.id.knowledge).setAnimation(middleAnimation);
    }
    public void editOnclick(View view){
        Intent intent = new Intent(UserActivity.this, EditActivity.class);
        startActivity(intent); // 刚开始漏了
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void userOnclick1(View view){
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void userOnclick2(View view){
        Intent intent = new Intent(UserActivity.this, KnowledgemapActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void wOnclick(View view){
        Intent intent = new Intent(UserActivity.this, wrongtitlebook.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void uOnclick(View view){
        Intent intent = new Intent(UserActivity.this, UploadActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void rOnclick(View view){
        Intent intent = new Intent(UserActivity.this, ResultActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

}
