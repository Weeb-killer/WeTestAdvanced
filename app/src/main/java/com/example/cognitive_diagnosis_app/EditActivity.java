package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

public class EditActivity extends AppCompatActivity {
    private ImageView avatar;
    View Sun,Builing,first,second,third,fourth,fifth;

    Uri imageUri;
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

        avatar=findViewById(R.id.avatar);
        SharedPreferences sp = getSharedPreferences("Userdata",MODE_PRIVATE);
        String myn= sp.getString("myname","");
        String myclass= sp.getString("myclass","");
        String myt= sp.getString("myt","");
        String mya= sp.getString("mya","");
        EditText myn1 = (EditText) findViewById(R.id.Myname);
        EditText myclass1 = (EditText) findViewById(R.id.MyGrade);
        EditText myt1 = (EditText) findViewById(R.id.MyPlace);
        ImageView avatar= findViewById(R.id.avatar);
        Uri myav=Uri.parse(mya);
        if (mya!=null) {
            avatar.setImageURI(myav);
        }
        myn1.setText(myn);
        myclass1.setText(myclass);
        myt1.setText(myt);


    }
    public void eOnclick1(View view){
        Intent intent = new Intent(EditActivity.this,UserActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }
    public void ChageClick(View view){
        //打开相册
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        //选择相册界面
        startActivityForResult(intent,1);
    }

    public void SaveInput(View view){//用户保存设定
        EditText myn1 = (EditText) findViewById(R.id.Myname);
        EditText myclass1 = (EditText) findViewById(R.id.MyGrade);
        EditText myt1 = (EditText) findViewById(R.id.MyPlace);

        SharedPreferences sp= getSharedPreferences("Userdata",MODE_PRIVATE);

        SharedPreferences.Editor editor=sp.edit();
        editor.putString("myname",myn1.getText().toString());
        editor.putString("myclass",myclass1.getText().toString());
        editor.putString("myt",myt1.getText().toString());
        editor.putString("mya",imageUri.toString());
        editor.commit();
    }

    protected void onActivityResult(int reqC,int resC,Intent data) {

        super.onActivityResult(reqC, resC, data);

        if (reqC==1&&resC==RESULT_OK&&data!=null){
            imageUri=data.getData();
            avatar.setImageURI(imageUri);
        }
    }

}
