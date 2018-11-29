// Book.aidl
package com.jackson.processcommunication.aidl;

// Declare any non-default types here with import statements

parcelable Book;  // AIDL文件中用到了自定义的Parcelable对象，必须新建一个和它同名的AIDL文件，并在其中声明它为Parcelable类型

