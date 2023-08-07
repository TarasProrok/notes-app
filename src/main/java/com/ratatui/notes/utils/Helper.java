package com.ratatui.notes.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public final class Helper {

    private Helper() {
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static LocalDate getLocalDateFromString(String stringDate) {
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.from(europeanDateFormatter.parse(stringDate));
    }
}
