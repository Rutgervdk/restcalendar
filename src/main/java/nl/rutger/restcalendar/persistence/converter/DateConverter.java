package nl.rutger.restcalendar.persistence.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static LocalDateTime convert(String dateToConvert) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        return LocalDateTime.parse(dateToConvert, formatter);

    }
}
