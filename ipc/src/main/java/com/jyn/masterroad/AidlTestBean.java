package com.jyn.masterroad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 需要再client端copy一份，路径要与serve端一直
 */
public class AidlTestBean implements Parcelable {
    private int x = -1;
    private int y = -1;
    private String name = "默认name";

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AidlTestBean() {

    }

    public AidlTestBean(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    protected AidlTestBean(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        name = in.readString();
    }

    @Override
    public String toString() {
        return "AidlTestBean{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
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
     * 注意：顺序不能错
     * @param dest 参数是一个Parcel,用它来存储与传输数据
     */
    public void readFromParcel(Parcel dest) {
        x = dest.readInt();
        y = dest.readInt();
        name = dest.readString();
    }
}
