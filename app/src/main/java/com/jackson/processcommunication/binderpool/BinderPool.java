package com.jackson.processcommunication.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/*
 * BinderPool  2018-11-27
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 11 27
 */
public class BinderPool {

    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;

    private Context mContext;
    private IBinderPool mIBinderPool;
    private static volatile BinderPool instance;
    private CountDownLatch mCountDownLatch;


    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static BinderPool getInstance(Context context) {
        if (instance == null) {
            synchronized (BinderPool.class) {
                if (instance == null) {
                    instance = new BinderPool(context);
                }
            }
        }
        return instance;
    }

    /**
     * 连接服务
     */
    private synchronized void connectBinderPoolService() {
        mCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据code查询绑定哪个Binder
     * @param binderCode
     * @return
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;

        try {
            if (mIBinderPool != null) {
                binder = mIBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;

    }


    /**
     * 防止服务端意外停止，重新连接服务
     */
    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("hbj--", "binder died.");
            mIBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mIBinderPool = null;
            connectBinderPoolService();
        }
    };

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mIBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public static class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}