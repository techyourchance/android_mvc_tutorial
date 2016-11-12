package com.techyourchance.www.android_mvc_tutorial.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains static util methods
 */
public class Utils {

    /**
     * Private constructor in order to prevent accidental instantiation
     */
    private Utils() {}

    /**
     * Convert Unix timestamp to a human readable representation of date
     */
    public static String convertToHumanReadableDate(String timestamp) {
        SimpleDateFormat fmtOut = new SimpleDateFormat();
        return fmtOut.format(new Date(Long.valueOf(timestamp)));
    }

}
