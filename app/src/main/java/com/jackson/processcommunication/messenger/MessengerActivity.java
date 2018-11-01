package com.jackson.processcommunication.messenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jackson.processcommunication.R;

public class MessengerActivity extends AppCompatActivity {

    /**
     * 使用Messenger进行进程间通信的例子
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
    }
}
