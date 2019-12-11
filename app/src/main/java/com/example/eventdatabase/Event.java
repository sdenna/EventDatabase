package com.example.eventdatabase;

import android.os.Parcel;
import android.os.Parcelable;

public class Event
{
    private String eventName;
    private String eventDate;
    private int year;
    private int month;
    private int day;

    public Event(String eventName, String eventDate, int year, int month, int day) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.year = year;
        this.month = month;
        this.day = day;

    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public int getYear(){ return year;}

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String toString() {
        String str = "";
        if (month == 1)
            str += "Jan ";
        else if (month == 2)
            str += "Feb ";
        else if (month == 3)
            str += "Mar ";
        else if (month == 4)
            str += "Apr ";
        else if (month == 5)
            str += "May ";
        else if (month == 6)
            str += "Jun ";
        else if (month == 7)
            str += "Jul ";
        else if (month == 8)
            str += "Aug ";
        else if (month == 9)
            str += "Sep ";
        else if (month == 10)
            str += "Oct ";
        else if (month == 11)
            str += "Nov ";
        else
            str += "Dec ";


        if (day < 10)
            str += "  ";

        str += day;

        str += ", " + year + "   " + eventName;

        return str;


    }

}
