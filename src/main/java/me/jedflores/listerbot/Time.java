package me.jedflores.listerbot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    
    private String datetime = "dd/MM/yyyy hh:mm aa";
    private String date = "dd/MM/yyyy";
    private String time = "hh:mm aa";
    DateFormat formatDateTime = new SimpleDateFormat(datetime);
    DateFormat formatDate = new SimpleDateFormat(date);
    DateFormat formatTime = new SimpleDateFormat(time);
    
    public void setPattern(String dateFormat){
        datetime=dateFormat;
    }
    
    public static void main(String[] args) {
        Time t = new Time();
        //System.out.println(t.getCurrentDateTime());
        //System.out.println(t.stringToDate(t.getCurrentDateTime()));
        System.out.println("DATE:"+t.stringToDate("12/08/2021"));
        System.out.println("DATE TIME:"+t.stringToDateTime("12/08/2021 06:52 pm"));
        System.out.println("TIME:"+t.stringToTime("06:05 pm"));
        System.out.println("TIME:"+t.timeToString(t.stringToTime("06:05 pm")));
    }

    

    public String getCurrentDateTime(){
        String out = formatDateTime.format(new Date()).toString();
        return out;
    }
    
    public Date stringToDateTime(String dateTimeString){
        Date out = null;
        try {
             out = formatDateTime.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }
    
    public Date stringToDate(String dateString){
        Date out = null;
        try {
            out = formatDate.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

    public Date stringToTime(String timeString){
        Date out = null;
        try {
            out = formatTime.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String dateTimeToString(Date date){
        String out = formatDateTime.format(date);
        return out;
    }

    public String dateToString(Date date){
        String out = formatDate.format(date);
        return out;
    }

    public String timeToString(Date date){
        String out = formatTime.format(date);
        return out;
    }


}
