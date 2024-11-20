package com.inz.WMS_Backend.utils;

import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static Date asDate(java.time.LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static java.sql.Date asSqlDate(java.time.LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
}
