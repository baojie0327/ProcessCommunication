package com.jackson.processcommunication.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jackson.processcommunication.R;

public class BinderPoolActivity extends AppCompatActivity {

    private ISecurityCenter mISecurityCenter;
    private ICompute mICompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }


    private void doWork(){
        BinderPool binderPool=BinderPool.getInstance(BinderPoolActivity.this);

        IBinder securityBinder=binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);

        mISecurityCenter=SecurityCenterImpl.asInterface(securityBinder);
        Log.e("hbj--","visit ISecurityCenter");
        String msg="hello android";

        try {
            String password=mISecurityCenter.encrypt(msg);
            Log.e("hbj--","encrypt:"+password);
            Log.e("hbj--","decrypt:"+mISecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.d("hbj--", "visit ICompute");
        IBinder computeBinder=binderPool.queryBinder(BinderPool.BINDER_COMPUTE);

        mICompute=ComputeImpl.asInterface(computeBinder);

        try {
            Log.e("hbj--","3+5=="+mICompute.add(3,5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


}
