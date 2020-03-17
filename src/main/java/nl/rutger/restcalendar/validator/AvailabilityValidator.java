package nl.rutger.restcalendar.validator;

import nl.rutger.restcalendar.persistence.model.Meeting;
import nl.rutger.restcalendar.persistence.dao.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class AvailabilityValidator {

    private static final int SLOT_INCREMENT = 1;

    @Autowired
    private MeetingRepository meetingRepository;

    public boolean validate(Meeting newMeeting) {

        ArrayList<Meeting> allMeetings = (ArrayList<Meeting>) meetingRepository.findAll();

        for (Meeting scheduledMeeting : allMeetings) {

            if (newMeeting.getStartDateTime().isBefore(scheduledMeeting.getEndDateTime()) && newMeeting.getEndDateTime().isAfter(scheduledMeeting.getStartDateTime())) {
                return false;
            }
        }
        return true;
    }

    public LocalDateTime findFirstFreeSlot(Meeting dateTimeToSearchFrom) {

        while (!validate(dateTimeToSearchFrom)) {

            dateTimeToSearchFrom.setStartDateTime(dateTimeToSearchFrom.getStartDateTime().plusHours(SLOT_INCREMENT));
            dateTimeToSearchFrom.setEndDateTime(dateTimeToSearchFrom.getEndDateTime().plusHours(SLOT_INCREMENT));

            //validate(dateTimeToSearchFrom);
        }

        return dateTimeToSearchFrom.getStartDateTime();
    }

}
