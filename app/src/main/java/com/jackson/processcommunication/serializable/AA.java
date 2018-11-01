package com.jackson.processcommunication.serializable;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * AA  2018-10-29
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 10 29
 */
public class AA implements Parcelable {

    private int id;
    private String name;
    private boolean isMale;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
    }

    public AA() {
    }

    protected AA(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.isMale = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AA> CREATOR = new Parcelable.Creator<AA>() {
        @Override
        public AA createFromParcel(Parcel source) {
            return new AA(source);
        }

        @Override
        public AA[] newArray(int size) {
            return new AA[size];
        }
    };
}