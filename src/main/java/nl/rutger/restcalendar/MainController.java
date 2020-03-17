package nl.rutger.restcalendar;

import nl.rutger.restcalendar.persistence.model.Meeting;
import nl.rutger.restcalendar.persistence.converter.DtoToMeeting;
import nl.rutger.restcalendar.persistence.dao.MeetingRepository;
import nl.rutger.restcalendar.persistence.dto.MeetingDto;
import nl.rutger.restcalendar.validator.AvailabilityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    public ResponseEntity<?> addMeeting(@RequestBody MeetingDto meetingToAdd) {

        Meeting newMeeting = dtoToMeeting.convertFromDto(meetingToAdd);

        if (newMeeting == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!availabilityValidator.validate(newMeeting)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        meetingRepository.save(newMeeting);
        return new ResponseEntity<Meeting>(newMeeting, HttpStatus.CREATED);
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


    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> seeAllMeetings() {

        ArrayList<Meeting> allMeetings = (ArrayList<Meeting>) meetingRepository.findAll();

        return new ResponseEntity<>(allMeetings, HttpStatus.OK);
    }


    @DeleteMapping(path = "/meeting/{id}")
    public ResponseEntity<?> deleteMeeting(@PathVariable Integer id) {

        if (meetingRepository.existsById(id)) {
            meetingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
