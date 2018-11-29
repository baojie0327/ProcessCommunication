package com.jackson.processcommunication.binderpool;

import android.os.RemoteException;

/*
 * ComputeImpl  2018-11-27
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 11 27
 */
public class ComputeImpl extends ICompute.Stub{
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}