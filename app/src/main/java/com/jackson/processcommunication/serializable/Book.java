package com.jackson.processcommunication.serializable;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Book  2018-10-29
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 10 29
 */
public class Book implements Parcelable {

    public int bookId;
    private String bookName;
    private boolean isNative;

    /**
     * 构造方法
     * @param id
     * @param name
     * @param isNative
     */
    public Book(int id,String name,boolean isNative){
        this.bookId=id;
        this.bookName=name;
        this.isNative=isNative;
    }

    /**
     * 从序列化的对象中创建原始对象
     *
     * @param in
     */
    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
        isNative = in.readByte() != 0;
    }


    public static final Creator<Book> CREATOR = new Creator<Book>() {
        // 从序列化的对象中创建原始对象
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        // 创建制定长度的原原始对象数组
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    /**
     * 返回当前对象的内容描述，如果含有文件描述符，返回1，否则，返回0，几乎所有的情况都返回0
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将当前对象写入序列化结构中，其中flags，标识有0或1
     * 为1时标识当前对象需要作为返回值返回，不能立即释放资源
     * 几乎所有的情况都为0
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeByte((byte) (isNative ? 1 : 0));
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isNative() {
        return isNative;
    }

    public void setNative(boolean aNative) {
        isNative = aNative;
    }
}

