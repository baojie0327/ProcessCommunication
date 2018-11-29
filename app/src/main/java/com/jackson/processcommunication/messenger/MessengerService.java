package com.jackson.processcommunication.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.jackson.processcommunication.utils.Constant;

/*
 * MessengerService  2018-10-31
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 10 31
 */
public class MessengerService extends Service {

    //MessengerHandler用来处理客户端发送的消息
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:

                    // 接收到Client的信息
                    Log.e("hbj--", "receive msg from Client:" + msg.getData().getString("msg"));

                    // 回复消息到客户端
                    Messenger client = msg.replyTo;  // 服务端通过此参数回应客户端
                    Message replyMessage = Message.obtain(null, Constant.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "恩，消息收到，稍后回复你。");
                    replyMessage.setData(bundle);

                    try {
                        client.send(replyMessage);  // 回信
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }


    private final Messenger mMessenger = new Messenger(new MessengerHandler());


    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}