package com.jackson.processcommunication.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/*
 * BinderPoolService  2018-11-27
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 11 27
 */
public class BinderPoolService extends Service {


    private Binder mBinderPool=new BinderPool.BinderPoolImpl();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("hbj--","onBind");
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}