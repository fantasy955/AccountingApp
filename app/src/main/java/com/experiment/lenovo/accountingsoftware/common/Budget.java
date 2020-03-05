package com.experiment.lenovo.accountingsoftware.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.experiment.lenovo.accountingsoftware.tool.MyDate;

public class Budget implements Parcelable {
    private String valuie;
    private MyDate myDate;
    private String uid;

    public String getValuie() {
        return valuie;
    }

    public void setValuie(String valuie) {
        this.valuie = valuie;
    }

    public MyDate getMyDate() {
        return myDate;
    }

    public void setMyDate(MyDate myDate) {
        this.myDate = myDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Budget(String valuie, MyDate myDate, String uid) {
        this.valuie = valuie;
        this.myDate = myDate;
        this.uid = uid;
    }

    protected Budget(Parcel in) {
        valuie = in.readString();
        myDate = in.readParcelable(MyDate.class.getClassLoader());
        uid = in.readString();
    }

    public static final Creator<Budget> CREATOR = new Creator<Budget>() {
        @Override
        public Budget createFromParcel(Parcel in) {
            return new Budget(in);
        }

        @Override
        public Budget[] newArray(int size) {
            return new Budget[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(valuie);
        dest.writeParcelable(myDate, flags);
        dest.writeString(uid);
    }
}
