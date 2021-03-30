package com.reminder.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    private DateUtil() {}

    public static LocalDateTime getLocalDateTimeFromEpochMilli(long longDate) {
        return Instant.ofEpochMilli(longDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
