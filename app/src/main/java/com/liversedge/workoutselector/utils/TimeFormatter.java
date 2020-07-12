package com.liversedge.workoutselector.utils;

public class TimeFormatter {

    /**
     * Converts milliseconds into the HH:MM:SS format
     *
     * @param milliseconds
     * @return
     */
    public static String convertMilliSecondsToString(float milliseconds) {

        int seconds = (int) Math.floor(milliseconds / 1000.0f);

        int minutes = (int) Math.floor(seconds / 60);
        seconds = seconds - (minutes * 60);

        int hours = (int) Math.floor(minutes / 60);
        minutes = minutes - (hours * 60);

        String hoursAsString = String.valueOf(hours);
        if(hours < 10) { hoursAsString = "0" + hoursAsString;}

        String minutesAsString = String.valueOf(minutes);
        if(minutes < 10) { minutesAsString = "0" + minutesAsString;}

        String secondsAsString = String.valueOf(seconds);
        if(seconds < 10) { secondsAsString = "0" + secondsAsString;}

        return hoursAsString + ":" + minutesAsString + ":" + secondsAsString;
    }

}
