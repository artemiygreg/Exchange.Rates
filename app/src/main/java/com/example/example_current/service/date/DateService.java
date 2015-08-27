package com.example.example_current.service.date;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by artem_mobile_dev on 19.07.2015.
 */
@SuppressLint("SimpleDateFormat")
public class DateService {
    public static final String DATE_FORMAT_RU = "dd.MM.yyyy";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT_RU = "HH:mm";
    public static final String DATE_FORMAT_FOR_RU_LOCALE = "dd MMMM y г.";
    public static final String DATE_AND_TIME_FORMAT_RU = "dd.MM.yyyy HH:mm";
    public static final Long COUNT_MS_IN_DAY = 86400000L;
    private static DateFormat df;


    public static Date parseStringDate(String dateString) {
        Date date = null;
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseStringDate(String dateString, String format) {
        Date date = null;
        DateFormat df = new SimpleDateFormat(format);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateToString(Date date) {
        if (df == null) df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    public static String formatDateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date getCurrentDateAndTime() {
        return Calendar.getInstance().getTime();

    }

    public static long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getYesterdayDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = new Date(calendar.getTimeInMillis());
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static String convertTimestampToString(long timestamp){
        DateFormat dateFormat = new SimpleDateFormat(DATE_AND_TIME_FORMAT_RU);
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    public static String convertTimestampToString(long timestamp, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(timestamp));
    }

    public static Date convertTimestampToDate(long timestamp){
        return new Date(timestamp);
    }
}