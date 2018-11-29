// IOnNewBookArrivedListener.aidl
package com.jackson.processcommunication.aidl;
import com.jackson.processcommunication.aidl.Book; // 对于自定义的Parcelable对象和AIDL对象，必须要显式import进来


// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


   void onNewBookArrived(in Book newBook);


}
