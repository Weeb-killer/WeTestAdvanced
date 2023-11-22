package com.example.cognitive_diagnosis_app;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.ai.BmobAI;

public class BmobApp extends Application {
    public static BmobAI bmobAI;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        Bmob.initialize(this,"402c8cbad9009f6f12c739dbfe3bdecb");
        //初始化AI（初始化时，会自动创建一个websocket，保持心跳连接，确保实时回复）
        bmobAI = new BmobAI();
    }
}