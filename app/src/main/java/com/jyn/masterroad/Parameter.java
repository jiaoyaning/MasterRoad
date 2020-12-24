package com.jyn.masterroad;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameter implements Parcelable {
    private int param;

    protected Parameter(Parcel in) {
        param = in.readInt();
    }

    public static final Creator<Parameter> CREATOR = new Creator<Parameter>() {
        @Override
        public Parameter createFromParcel(Parcel in) {
            return new Parameter(in);
        }

        @Override
        public Parameter[] newArray(int size) {
            return new Parameter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(param);
    }
}
