package ru.javawebinar.topjava.util.customDateTimeFormat;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomTimeFormatter implements Formatter<LocalTime> {

    private static final String TIME_PATTERN = "HH:mm";

    @Override
    public LocalTime parse(String text, Locale locale) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(TIME_PATTERN));
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.toString();
    }
}