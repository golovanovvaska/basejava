package com.basejava.webapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.now();

    public static String dateToString(LocalDate date) {
        if (date == null) return "";
        return date.isAfter(NOW) ? "Сейчас" : date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }


}
