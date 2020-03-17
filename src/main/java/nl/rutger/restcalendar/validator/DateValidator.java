package nl.rutger.restcalendar.validator;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DateValidator {

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
