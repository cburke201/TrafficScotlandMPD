package org.me.gcu.trafficscotlandmpd;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.lang.Double.valueOf;


class Format
{

    // https://developers.google.com/maps/documentation/android-sdk/start
    // suggests that could not use georss for google map so need to extract the
    // latitude and longitude from the georss:point tag
    double[] getLatLng(String georss)
    {
        String[] latLngString = georss.split(" ");
        return new double[]{valueOf(latLngString[0]), valueOf(latLngString[1])};
    }

    public String[] getDateStrings(String input)
    {
        if (!input.contains("Start Date: ") || !input.contains("End Date: "))
        {
            // if tag does not contain any Start or End Date then return no value
            return null;
        }
        else
        {
            // otherwise store the date input from description tag and store the Start Date
            String startDateIndex = input.substring(input.indexOf("Start Date: "), input.indexOf(':'));
            String startDateString = input.substring(startDateIndex.length() + 2, input.indexOf(" - 00:00"));

            // do the same for the End Date by removing the Start Date from the input
            input = input.substring(input.indexOf('>') + 1);

            String endDateIndex = input.substring(input.indexOf("End Date: "), input.indexOf(':'));
            String endDateString = input.substring(endDateIndex.length() + 2, input.indexOf(" - 00:00"));

            String[] datesArray = new String[2];
            datesArray[0] = startDateString;
            datesArray[1] = endDateString;
            return datesArray;
        }
    }

    // when taking the string data from the description input
    // and convert it to Calendar
    public Calendar getCalendarFromString(String date)
    {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        String pattern = "EEEE, dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            calendar.setTime(simpleDateFormat.parse(date));
            calendar.setTimeZone(tz);
        } catch (ParseException e) {
            Log.d("Catch Error", "Catch Error");
        }
        return calendar;
    }

    // assign a value to measure the severity of disruption caused by the length it will last
    // less than 1 day disruption - minor - 1
    // between 1 day and a week - major - 2
    // more than a week - critical - 3
    public int getLengthOfDisruption(Calendar startDate, Calendar endDate)
    {
        long s = startDate.getTimeInMillis();
        long e = endDate.getTimeInMillis();
        long daysBetween = TimeUnit.MILLISECONDS.toDays(e - s);

        if (daysBetween <= 1)
        {
            return 1;
        }
        else if (daysBetween > 1 && daysBetween <= 7)
        {
            return 2;
        }
        else {
            return 3;
        }
    }

    // add a new line in order to space out the presentation of data
    // to make it easier to read
    String addNewLine(String input)
    {
        input = input.replaceAll("<br />", "\n");
        input = input.replace("Location : ", "\nLocation: ");
        input = input.replace("Traffic Management:", "\nTraffic Management: ");
        input = input.replace("Diversion Information:", "\nDiversion Information: ");
        input = input.replace("Lane Closures :", "\nLane Closures: ");
        input = input.replace("Reason :", "\nReason: ");
        return input;
    }

}
