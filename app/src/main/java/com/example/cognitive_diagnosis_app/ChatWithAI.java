package com.example.cognitive_diagnosis_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.ai.ChatMessageListener;

public class ChatWithAI extends Activity {
    RecyclerView recyclerView;
    EditText messageEditText;
    Button sendButton;

    List<Message> messageList;
    MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwithai);

        messageList=new ArrayList<>();
        recyclerView= findViewById(R.id.msg_recycle_view);
        messageEditText= findViewById(R.id.message_edit);
        sendButton= findViewById(R.id.send_bt);

        messageAdapter =new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question= messageEditText.getText().toString().trim();

                if(question.equals("")) return;

                addToChat(question,Message.SEND_BY_ME);

                BmobApp.bmobAI.Chat(question, "mysession", new ChatMessageListener() {
                    @Override
                    public void onMessage(String s) {

                    }

                    @Override
                    public void onFinish(String s) {
                        addToChat(s,Message.SEND_BY_BOT);
                    }

                    @Override
                    public void onError(String s) {

                    }

                    @Override
                    public void onClose() {

                    }
                });
            }
        });
    }

    private void addToChat(String message,String sendBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sendBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

}
