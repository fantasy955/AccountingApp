package com.experiment.lenovo.accountingsoftware.tool;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.experiment.lenovo.accountingsoftware.common.Constants.Equal;
import static com.experiment.lenovo.accountingsoftware.common.Constants.Greater;
import static com.experiment.lenovo.accountingsoftware.common.Constants.LOCAL_TIME_ZONE;
import static com.experiment.lenovo.accountingsoftware.common.Constants.Smaller;

public class MyDate implements Parcelable {
    private String year;
    private String month;
    private String day;
    private int dayOfTheWeek;

    public MyDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(LOCAL_TIME_ZONE));
        long timeMillis = System.currentTimeMillis();;
        Date date = new Date(timeMillis);
        String str_date = simpleDateFormat.format(date);
        String[] strings = str_date.split("-");
        year = strings[0];
        month = strings[1];
        day = strings[2];

    }

    public MyDate(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MyDate(String year, String month, String day, int dayOfTheWeek) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfTheWeek = dayOfTheWeek;
    }
    public MyDate(String date){
        String[] temp = date.split("-");
        year = temp[0];
        month = temp[1];
        day = temp[2];
    }

    protected MyDate(Parcel in) {
        year = in.readString();
        month = in.readString();
        day = in.readString();
        dayOfTheWeek = in.readInt();
    }

    public static final Creator<MyDate> CREATOR = new Creator<MyDate>() {
        @Override
        public MyDate createFromParcel(Parcel in) {
            return new MyDate(in);
        }

        @Override
        public MyDate[] newArray(int size) {
            return new MyDate[size];
        }
    };

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month.length() == 2 ? month : "0" + month;
    }

    public void setDay(String day) {
        this.day = day.length() == 2 ? day : "0"+day;
    }


    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }
    public String toStringDate(){
        return year+"-"+month+"-"+day;
    }
    public static String getEndtOfMonth(String year,String month){
        switch (Integer.parseInt(month)){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return new String("31");
            case 2:
                if(isLeapYear(Integer.parseInt(year))){
                    return new String("29");
                }
                return new String("28");
            case 4:
            case 6:
            case 9:
            case 11:
                return new String("30");
        }
        return null;
    }
    public static String getStartOfMonth(String month){
        return new String("1");
    }

    private static boolean isLeapYear(int year){
        if(year % 100 == 0){
            if(year % 400 ==0)
                return true;
            else
                return false;
        }else if(year % 4 == 0){
            return true;
        }else{
            return false;
        }
    }
    public int compare(MyDate myDate){
        if(Integer.parseInt(this.getYear()) > Integer.parseInt(myDate.getYear())){
            return Greater;
        }else if(Integer.parseInt(this.getYear()) < Integer.parseInt(myDate.getYear())){
            return Smaller;
        }else{
            if(Integer.parseInt(this.getMonth()) > Integer.parseInt(myDate.getMonth())){
                return Greater;
            }else if(Integer.parseInt(this.getMonth()) < Integer.parseInt(myDate.getMonth())){
                return Smaller;
            }else{
                if(Integer.parseInt(this.getDay()) > Integer.parseInt(myDate.getDay())){
                    return Greater;
                }else if(Integer.parseInt(this.getDay()) < Integer.parseInt(myDate.getDay())){
                    return Smaller;
                }else {
                    return Equal;
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(day);
        dest.writeInt(dayOfTheWeek);
    }
    public String toStringChinese(){
        return year + "年" + month + "月" + day + "日";
    }
    public String toString(){
        return year + "-" +
                month + "-" +
                day;
    }
}
