package com.jackson.processcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jackson.processcommunication.aidl.AidlActivity;
import com.jackson.processcommunication.binderpool.BinderPoolActivity;
import com.jackson.processcommunication.contentprovider.ContentProviderActivity;
import com.jackson.processcommunication.messenger.MessengerActivity;
import com.jackson.processcommunication.process.ProcessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_process)
    Button btnProcess;


    @OnClick({R.id.btn_process,R.id.btn_messenger,R.id.btn_aidl,R.id.btn_contentprovider,R.id.btn_binderpool})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_process: // 开启多线程
                startActivity(new Intent(MainActivity.this, ProcessActivity.class));
                break;
            case R.id.btn_messenger:  // Messenger通信
                startActivity(new Intent(MainActivity.this,MessengerActivity.class));
                break;
            case R.id.btn_aidl:     // 使用AIDL通信
                startActivity(new Intent(MainActivity.this,AidlActivity.class));
                break;
            case R.id.btn_contentprovider:
                startActivity(new Intent(MainActivity.this,ContentProviderActivity.class));
                break;
            case R.id.btn_binderpool:
                startActivity(new Intent(MainActivity.this,BinderPoolActivity.class));
                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }



}
