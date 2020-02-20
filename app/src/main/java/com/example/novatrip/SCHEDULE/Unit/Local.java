package com.example.novatrip.SCHEDULE.Unit;

import android.os.Parcel;
import android.os.Parcelable;

public class Local implements Parcelable {



    private int idx;

    protected Local(Parcel in) {
        idx = in.readInt();
        name_local = in.readString();
    }

    public Local( ) {
    }

    public static final Creator<Local> CREATOR = new Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel in) {
            return new Local(in);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };

    public int getIdx_local() {
        return idx;
    }

    public void setIdx_local(int idx_local) {
        this.idx = idx_local;
    }

    public String getName() {
        return name_local;
    }

    public void setName(String name_local) {
        this.name_local = name_local;
    }

    private String name_local;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx);
        dest.writeString(name_local);
    }
}

