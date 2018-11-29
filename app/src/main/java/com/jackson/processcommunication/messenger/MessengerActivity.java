package com.jackson.processcommunication.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jackson.processcommunication.R;
import com.jackson.processcommunication.utils.Constant;

public class MessengerActivity extends AppCompatActivity {

    /**
     * 使用Messenger进行进程间通信的例子
     */

    private Messenger mService; // 服务端的Messenger
    private Messenger mGetReplyMessenger=new Messenger(new MessengerHandler());  // 创建客户端的Messenger，传递一个Handler处理消息

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_SERVICE:
                    Log.e("hbj--", "receive msg from Service:" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 根据服务端返回的binder对象创建Messenger对象
            mService=new Messenger(service);
            //向服务端 发送消息
            Message msg=Message.obtain(null,Constant.MSG_FROM_CLIENT);
            Bundle bundle=new Bundle();
            bundle.putString("msg","hello ,this is client");
            msg.setData(bundle);  // 设置数据

            msg.replyTo=mGetReplyMessenger; // 如果需要服务端回信，指定回信的信使
            try {
                mService.send(msg);  // 调用服务端信使，发送数据
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent("com.jackson.messenger");
        intent.setPackage("com.jackson.processcommunication");
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
