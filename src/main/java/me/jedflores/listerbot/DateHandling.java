package me.jedflores.listerbot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandling {
    
    private String datetime = "dd/MM/yyyy hh:mm aa";
    private String date = "dd/MM/yyyy";
    private String time = "hh:mm aa";
    DateFormat formatDateTime = new SimpleDateFormat(datetime);
    DateFormat formatDate = new SimpleDateFormat(date);
    DateFormat formatTime = new SimpleDateFormat(time);


    /***
     * Set datetime pattern
     * @param dateFormat
     */
    public void setPattern(String dateFormat){
        datetime=dateFormat;
    }
    
    
    
    public static void main(String[] args) {
        DateHandling t = new DateHandling();
        //System.out.println(t.getCurrentDateTime());
        //System.out.println(t.stringToDate(t.getCurrentDateTime()));
        System.out.println("DATE:"+t.stringToDate("12/08/2021"));
        System.out.println("DATE TIME:"+t.stringToDateTime("12/08/2021 06:52 pm"));
        System.out.println("TIME:"+t.stringToTime("06:05 pm"));
        System.out.println("TIME:"+t.timeToString(t.stringToTime("06:05 pm")));
    }

    /***
     * Returns the current date and time in string format
     * @return currentDateTime
     */
    public String getCurrentDateTime(){
        String currentDateTime = formatDateTime.format(new Date());
        return currentDateTime;
    }

    /***
     * converts datetime string to Date datatype.
     * datetime format must be in "dd/MM/yyyy hh:mm aa"
     * datetime format/pattern can be changed by using {@link #setPattern(String) method.
     * @param dateTimeString
     * @return dateTimeDate
     */
    public Date stringToDateTime(String dateTimeString){
        Date out = null;
        try {
             out = formatDateTime.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

    /***
     * converts date string to Date datatype
     * date format must be in "dd/MM/yyyy"
     * @param dateString
     * @return dateDate
     */
    
    public Date stringToDate(String dateString){
        Date out = null;
        try {
            out = formatDate.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

    /***
     * converts string time to Date datatype.
     * time format must be in "hh:m aa"
     * @param timeString
     * @return timeDate
     */

    public Date stringToTime(String timeString){
        Date out = null;
        try {
            out = formatTime.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * converts datetime to string.
     * @param datetime
     * @return datetimeString
     */

    public String dateTimeToString(Date datetime){
        String out = formatDateTime.format(date);
        return out;
    }

    /**
     * converts date to string.
     * @param date
     * @return dateString
     */
    public String dateToString(Date date){
        String out = formatDate.format(date);
        return out;
    }

    /**
     * converts time to string.
     * @param time
     * @return timeString
     */
    public String timeToString(Date time){
        String out = formatTime.format(time);
        return out;
    }


}
