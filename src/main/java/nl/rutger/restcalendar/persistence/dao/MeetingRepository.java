package nl.rutger.restcalendar.persistence.dao;

import nl.rutger.restcalendar.persistence.model.Meeting;
import org.springframework.data.repository.CrudRepository;

public interface MeetingRepository extends CrudRepository<Meeting, Integer> {


}
