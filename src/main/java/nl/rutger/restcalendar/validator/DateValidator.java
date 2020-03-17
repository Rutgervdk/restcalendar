package nl.rutger.restcalendar.validator;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DateValidator {

    /**
     * This class checks whether a given String contains a valid date and time in a specified format
     *
     * @param dateTimeToValidate the String object to check for a valid date and time
     * @return a boolean value representing whether a valid date and time was found
     */

    public static boolean validate(String dateTimeToValidate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        format.setLenient(false);

        if (!dateTimeToValidate.trim().equals("")) {

            try {
                format.parse(dateTimeToValidate);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
        return false;
    }
}
