package nl.rutger.restcalendar;

import nl.rutger.restcalendar.persistence.model.Meeting;
import nl.rutger.restcalendar.persistence.converter.DtoToMeeting;
import nl.rutger.restcalendar.persistence.dao.MeetingRepository;
import nl.rutger.restcalendar.persistence.dto.MeetingDto;
import nl.rutger.restcalendar.validator.AvailabilityValidator;
import nl.rutger.restcalendar.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/calendar")
public class MainController {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private AvailabilityValidator availabilityValidator;

    @Autowired
    private DtoToMeeting dtoToMeeting;


    @PostMapping(path = "/add")
    public ResponseEntity<?> addMeeting(@RequestBody MeetingDto dto) {

        Meeting newMeeting = dtoToMeeting.convertFromDto(dto);

        if (newMeeting == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!availabilityValidator.validate(newMeeting)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        meetingRepository.save(newMeeting);
        return new ResponseEntity<Meeting>(newMeeting,HttpStatus.CREATED);
    }

    @GetMapping(path = "/freeslot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firstFreeSlot(@RequestBody MeetingDto dto) {

        Meeting freeSlotFrom = dtoToMeeting.convertFromDto(dto);

        if (freeSlotFrom == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime firstAvailableSlot = availabilityValidator.findFirstFreeSlot(freeSlotFrom);


        return new ResponseEntity<>(firstAvailableSlot, HttpStatus.I_AM_A_TEAPOT);
    }


}
