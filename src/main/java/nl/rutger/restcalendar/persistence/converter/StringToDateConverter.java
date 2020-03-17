package nl.rutger.restcalendar.persistence.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




public class StringToDateConverter {


    /**
     * Method to convert a date written in a String to a LocalDateTime.
     *
     * @param dateToConvert The String to be converted into a date
     * @return the localDateTime
     */
    public static LocalDateTime convert(String dateToConvert) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        return LocalDateTime.parse(dateToConvert, formatter);

    }
}
