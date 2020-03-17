package nl.rutger.restcalendar.persistence.converter;

import nl.rutger.restcalendar.persistence.model.Meeting;
import nl.rutger.restcalendar.persistence.dto.MeetingDto;
import nl.rutger.restcalendar.validator.DateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
public class DtoToMeeting {

    public Meeting convertFromDto(MeetingDto dto) {

        if (DateValidator.validate(dto.getStartDateTime())) {

            if (dto.getNrOfHourSlots() < 1) {
                return null;
            }

            try {

                LocalDateTime convertedStartDateTime = DateConverter.convert(dto.getStartDateTime());

                LocalDateTime convertedEndDateTime = convertedStartDateTime.plusHours(dto.getNrOfHourSlots());

                Meeting m = new Meeting();
                m.setStartDateTime(convertedStartDateTime);
                m.setEndDateTime(convertedEndDateTime);
                m.setDescription(dto.getDescription());

                return m;

            } catch (DateTimeParseException e) {
                return null;
            }
        }
        return null;
    }
}
