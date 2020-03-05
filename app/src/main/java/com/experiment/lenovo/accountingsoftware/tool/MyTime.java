package com.experiment.lenovo.accountingsoftware.tool;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.experiment.lenovo.accountingsoftware.common.Constants.LOCAL_TIME_ZONE;

public class MyTime implements Parcelable {
    private  long timeMillis;
    private  String Date_standard_format = "yyyy年MM月dd日'";
    private String str_currentTime;
    private String year;
    private String month;

    protected MyTime(Parcel in) {
        timeMillis = in.readLong();
        Date_standard_format = in.readString();
        str_currentTime = in.readString();
        year = in.readString();
        month = in.readString();
        day = in.readString();
        hour = in.readString();
        minute = in.readString();
        second = in.readString();
        dayOfTheWeek = in.readInt();
    }

    public static final Creator<MyTime> CREATOR = new Creator<MyTime>() {
        @Override
        public MyTime createFromParcel(Parcel in) {
            return new MyTime(in);
        }

        @Override
        public MyTime[] newArray(int size) {
            return new MyTime[size];
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

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    private String day;
    private String hour;
    private String minute;
    private String second;
    private Date date;
    private int dayOfTheWeek;
    public MyTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy'年'MM'月'dd'日' HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(LOCAL_TIME_ZONE));
        timeMillis = System.currentTimeMillis();
        date = new Date(timeMillis);
        str_currentTime = simpleDateFormat.format(date);
        String strings[] = str_currentTime.split("年");
        year = strings[0];
        strings = strings[1].split("月");
        month = strings[0];
        strings = strings[1].split("日");
        day = strings[0];
        strings = strings[1].split(":");
        hour = strings[0].split(" ")[1];
        minute = strings[1];
        second = strings[2];
        Log.e("TAG",year+","+month+','+day+','+hour+','+minute+','+second);
    }
    public MyTime(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy'年'MM'月'dd'日' HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(LOCAL_TIME_ZONE));
        this.timeMillis = timeMillis;
        date = new Date(timeMillis);

        str_currentTime = simpleDateFormat.format(date);
        String strings[] = str_currentTime.split("年");
        year = strings[0];
        strings = strings[1].split("月");
        month = strings[0];
        strings = strings[1].split("日");
        day = strings[0];
        strings = strings[1].split(":");
        hour = strings[0].split(" ")[1];
        minute = strings[1];
        second = strings[2];
        Log.e("TAG",year+","+month+','+day+','+hour+','+minute+','+second);
    }

    public MyTime(String utcTime){
        String[] temp = utcTime.split("-");
        year = temp[0];
        month = temp[1];
        temp = temp[2].split(" ");
        day = temp[0];
        temp = temp[1].split(":");
        hour = temp[0];
        minute= temp[1];
        second = temp[2];
        UTC2GMT();

    }
    public String getTime(){
        return str_currentTime;
    }
    public Date getDate(){ return date;}
    public String toStringDate(){
        return year+"-"+month+"-"+day;
    }
    public String toStringTime(){
        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    }


    private boolean isLeapYear(int year){
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
    private void UTC2GMT(){
        int _hour = Integer.parseInt(hour) + 8;
        int _day = Integer.parseInt(day);
        int _month = Integer.parseInt(month);
        int _year = Integer.parseInt(year);
        if(_hour >=24 ){
            _day += 1;
            _hour -= 24;
            switch (_month){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(_day >31 ){
                        _day = 1;
                        _month += 1;
                        if(_month > 12){
                            _month = 1;
                            _year +=1 ;
                        }
                    }
                    break;
                case 2:
                    if(isLeapYear(_year)){
                        if(_day > 29){
                            _day = 1;
                            _month += 1;
                        }
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(_day >30){
                        _day = 1;
                        _month += 1;
                    }
                    break;
            }
        }
        year = String.valueOf(_year);
        month = String.valueOf(_month);
        day = String .valueOf(_day);
        hour = String.valueOf(_hour);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(timeMillis);
        dest.writeString(Date_standard_format);
        dest.writeString(str_currentTime);
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(day);
        dest.writeString(hour);
        dest.writeString(minute);
        dest.writeString(second);
        dest.writeSerializable(date);
        dest.writeInt(dayOfTheWeek);

    }
}
