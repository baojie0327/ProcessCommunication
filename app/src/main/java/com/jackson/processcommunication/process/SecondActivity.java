package com.jackson.processcommunication.process;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jackson.processcommunication.R;
import com.jackson.processcommunication.serializable.User;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.tv_show_serial)
    TextView tvShowSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
           User user = (User) bundle.get("user");
            assert user != null;
            tvShow.setText(String.format(getResources().getString(R.string.tv_show), user.getUserId(), user.getUserName(), user.isMale()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recoverFromFile();
    }

    /**
     * 反序列化，从文件中恢复
     */
    private void recoverFromFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user=null;
                File userfile=new File(ProcessActivity.path+ "serialize");
                if (userfile.exists()){
                    ObjectInputStream objectInputStream=null;
                    try {
                        objectInputStream=new ObjectInputStream(new FileInputStream(userfile));
                        user= (User) objectInputStream.readObject();
                        tvShowSerial.setText(String.format(getResources().getString(R.string.tv_show), user.getUserId(), user.getUserName(), user.isMale()));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        close(objectInputStream);
                    }
                }
            }
        }).start();
    }


    public void close(Closeable closeable){

        try {
            if (closeable!=null){
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
