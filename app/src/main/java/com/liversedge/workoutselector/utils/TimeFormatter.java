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

        String minutesAsString = String.valueOf(minutes);
        if(minutes < 10) { minutesAsString = "0" + minutesAsString;}

        String secondsAsString = String.valueOf(seconds);
        if(seconds < 10) { secondsAsString = "0" + secondsAsString;}

        return minutesAsString + ":" + secondsAsString;
    }

}
