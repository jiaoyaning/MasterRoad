package com.jyn.masterroad.ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 需要再client端copy一份，路径要与serve端一直
 */
public class AidlBean implements Parcelable {
    private int testInt = -1;
    private String testString = "默认name";

    public AidlBean() {
    }

    public AidlBean(int testInt, String testString) {
        this.testInt = testInt;
        this.testString = testString;
    }

    protected AidlBean(Parcel in) {
        testInt = in.readInt();
        testString = in.readString();
    }

    public static final Creator<AidlBean> CREATOR = new Creator<AidlBean>() {
        @Override
        public AidlBean createFromParcel(Parcel in) {
            return new AidlBean(in);
        }

        @Override
        public AidlBean[] newArray(int size) {
            return new AidlBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(testInt);
        dest.writeString(testString);
    }

    /**
     * 该方法不是Parcelable自动生成的，需要自己手动添加，
     * 如果不添加，则在使用AIDL时只支持 in 的定向tag
     * 如果添加了，则支持 in、out、inout
     * <p>
     * 注意：顺序不能错
     *
     * @param dest 参数是一个Parcel,用它来存储与传输数据
     */
    public void readFromParcel(Parcel dest) {
        testInt = dest.readInt();
        testString = dest.readString();
    }
}
