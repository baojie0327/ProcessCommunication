package com.jackson.processcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jackson.processcommunication.process.ProcessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_process)
    Button btnProcess;


    @OnClick({R.id.btn_process,R.id.btn_serializable})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_process: // 开启多线程
                startActivity(new Intent(MainActivity.this, ProcessActivity.class));
                break;
            case R.id.btn_messenger:  // Messenger通信

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
