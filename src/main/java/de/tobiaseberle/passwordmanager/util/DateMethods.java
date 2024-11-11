package de.tobiaseberle.passwordmanager.util;

import java.text.SimpleDateFormat;

public class DateMethods {

    public static String getFormattedDate(long millis, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(millis);
    }
}
