package com.jyn.masterroad;

import android.os.Parcel;
import android.os.Parcelable;

public class AidlTestBean implements Parcelable {
    private int x;
    private int y;
    private String name;

    protected AidlTestBean(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        name = in.readString();
    }

    public static final Creator<AidlTestBean> CREATOR = new Creator<AidlTestBean>() {
        @Override
        public AidlTestBean createFromParcel(Parcel in) {
            return new AidlTestBean(in);
        }

        @Override
        public AidlTestBean[] newArray(int size) {
            return new AidlTestBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeString(name);
    }

    /**
     * 该方法不是Parcelable自动生成的，需要自己手动添加，
     * 如果不添加，则在使用AIDL时只支持 in 的定向tag
     * 如果添加了，则支持 in、out、inout
     *
     * @param dest 参数是一个Parcel,用它来存储与传输数据
     */
    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        x = dest.readInt();
        y = dest.readInt();
    }
}
