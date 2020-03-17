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

    /**
     * This method checks whether the newly requested meeting interferes with any already scheduled meetings in the calendar
     *
     * @param newMeeting the new meeting to be checked against the calendar
     * @return boolean value to represent availability of the requested time slot
     */


    public boolean validate(Meeting newMeeting) {

        ArrayList<Meeting> allMeetings = (ArrayList<Meeting>) meetingRepository.findAll();

        for (Meeting scheduledMeeting : allMeetings) {

            if (newMeeting.getStartDateTime().isBefore(scheduledMeeting.getEndDateTime()) && newMeeting.getEndDateTime().isAfter(scheduledMeeting.getStartDateTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method finds the first available time slot after a certain date and time
     *
     * @param dateTimeToSearchFrom this meeting object holds the time and date where to start the search from
     * @return the date and time of the first available slot after the originally requested date and time
     */

    public LocalDateTime findFirstFreeSlot(Meeting dateTimeToSearchFrom) {

        while (!validate(dateTimeToSearchFrom)) {

            dateTimeToSearchFrom.setStartDateTime(dateTimeToSearchFrom.getStartDateTime().plusHours(SLOT_INCREMENT));
            dateTimeToSearchFrom.setEndDateTime(dateTimeToSearchFrom.getEndDateTime().plusHours(SLOT_INCREMENT));
        }

        return dateTimeToSearchFrom.getStartDateTime();
    }

}
