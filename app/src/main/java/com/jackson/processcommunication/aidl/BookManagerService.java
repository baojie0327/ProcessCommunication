package com.jackson.processcommunication.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * BookManagerService  2018-11-14
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 11 14
 */
public class BookManagerService extends Service {

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);


    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    // private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList=new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();


    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //权限验证
            int check = checkCallingOrSelfPermission("com.jackson.processcommunication.permission.ACCESS_BOOK_SERVICE");
            Log.e("hbj--", "check=" + check);
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }
            // 验证包名
            String packageName=null;
            String[] packages=getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.e("hbj--", "onTransact: " + packageName);
            if (!packageName.startsWith("com.jackson")) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
            final int N=mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Log.e("hbj--","register size=="+N);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
           boolean success= mListenerList.unregister(listener);
           if (success){
               Log.e("hbj--","unregister success");
           }else {
               Log.e("hbj--","not found,can not unregister");
           }
           final int N=mListenerList.beginBroadcast();
           mListenerList.finishBroadcast();
           Log.e("hbj--","unregister listener size=="+N);
        }

    };


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "ios"));
        new Thread(new ServiceWork()).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // 服务权限验证
        int check = checkCallingOrSelfPermission("com.jackson.processcommunication.permission.ACCESS_BOOK_SERVICE");
        Log.e("hbj--", "onbind check=" + check);
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;

    }


    /**
     * 有新书时，接口回调
     *
     * @param book
     * @throws RemoteException
     */
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }


    /**
     * 开启线程，每隔5s添加一本新书
     */
    private class ServiceWork implements Runnable {

        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 添加新书
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new Book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }
}