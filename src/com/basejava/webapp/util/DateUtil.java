package com.basejava.webapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static String dateToString(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }
}
