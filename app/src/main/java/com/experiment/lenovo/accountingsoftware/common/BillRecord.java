package com.experiment.lenovo.accountingsoftware.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.experiment.lenovo.accountingsoftware.tool.MyDate;
import com.experiment.lenovo.accountingsoftware.tool.MyTime;

public class BillRecord implements Parcelable {

    private String billType;
    private String billClass;
    private String values;
    private String note;
    private MyDate dateOfBill;
    private MyTime timeOfAdding;
    private String id;
    private boolean setNote;
    private boolean setDate;
    private boolean setUid;
    private boolean outOfBudget;
    private String uid;


    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
        setUid = true;
        this.uid = uid;
    }

    public boolean isOutOfBudget() {
        return outOfBudget;
    }

    public void setOutOfBudget(boolean outOfBudget) {
        this.outOfBudget = outOfBudget;
    }

    public BillRecord(String billType, String billClass, String values) {
        outOfBudget = false;
        setUid = false;
        setNote = false;
        setDate = false;
        this.billType = billType;
        this.billClass = billClass;
        this.values = values;
    }

    public BillRecord(String billType, String billClass, String values, MyDate dateOfBill) {
        outOfBudget = false;
        setUid = false;
        setNote = false;
        setDate = true;
        this.billType = billType;
        this.billClass = billClass;
        this.values = values;
        this.dateOfBill = dateOfBill;
    }

    public BillRecord(String billType, String billClass, String values, String note) {
        outOfBudget = false;
        setUid = false;
        setNote = true;
        setDate = false;
        this.billType = billType;
        this.billClass = billClass;
        this.values = values;
        this.note = note;
    }

    public BillRecord(String billType, String billClass, String values, String note, MyDate dateOfBill) {
        outOfBudget = false;
        setUid = false;
        setNote = true;
        setDate = true;
        this.billType = billType;
        this.billClass = billClass;
        this.values = values;
        this.note = note;
        this.dateOfBill = dateOfBill;
    }

    public BillRecord(String billType, String billClass, String values, String note, MyDate dateOfBill, MyTime dateOfAdding, String id) {
        outOfBudget = false;
        setUid = false;
        this.billType = billType;
        this.billClass = billClass;
        this.values = values;
        this.note = note;
        this.dateOfBill = dateOfBill;
        this.timeOfAdding = dateOfAdding;
        this.id = id;
    }

    protected BillRecord(Parcel in) {
        billType = in.readString();
        billClass = in.readString();
        values = in.readString();
        note = in.readString();
        dateOfBill = in.readParcelable(MyDate.class.getClassLoader());
        timeOfAdding = in.readParcelable(MyTime.class.getClassLoader());
        id = in.readString();
        setNote = in.readByte() != 0;
        setDate = in.readByte() != 0;
        uid = in.readString();
        setUid = in.readByte() != 0;
        outOfBudget = in.readByte() !=0 ;
    }

    public static final Creator<BillRecord> CREATOR = new Creator<BillRecord>() {
        @Override
        public BillRecord createFromParcel(Parcel in) {
            return new BillRecord(in);
        }

        @Override
        public BillRecord[] newArray(int size) {
            return new BillRecord[size];
        }
    };

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillClass() {
        return billClass;
    }

    public void setBillClass(String billClass) {
        this.billClass = billClass;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getNote() {
        setNote = true;
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public MyDate getDateOfBill() {
        return dateOfBill;
    }

    public void setDateOfBill(MyDate dateOfBill) {
        setDate = true;
        this.dateOfBill = dateOfBill;
    }

    public MyTime getDateOfAdding() {
        return timeOfAdding;
    }

    public void setDateOfAdding(MyTime timeOfAdding) {
        this.timeOfAdding = timeOfAdding;
    }

    public String getId() {
        return id;
    }

    public boolean isSetNote(){
        return setNote;
    }

    public boolean isSetDate() {
        return setDate;
    }
    public boolean isSetUid(){return setUid;}
    public String toString(){
        return "BillType:" + billType
                +",BillClass:" + billClass
                +",BillValue:" + values
                +",Note:" + note
                +",DateOfBill:" + dateOfBill.toStringDate()
                +",TimeOfAdding:" + timeOfAdding.toStringTime()
                +",ID:" + id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(billType);
        dest.writeString(billClass);
        dest.writeString(values);
        dest.writeString(note);
        dest.writeParcelable(dateOfBill,0);
        dest.writeParcelable(timeOfAdding,0);
        dest.writeString(id);
        dest.writeByte((byte) (setNote ? 1 : 0));
        dest.writeByte((byte) (setDate ? 1 : 0));
        dest.writeString(uid);
        dest.writeByte((byte) (setUid ? 1 : 0));
        dest.writeByte((byte)(outOfBudget ? 1: 0)) ;
    }
}
