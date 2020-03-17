package nl.rutger.restcalendar.persistence.dto;

public class MeetingDto {

    private String startDateTime;

    private int nrOfHourSlots;

    private String description;

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNrOfHourSlots() {
        return nrOfHourSlots;
    }

    public void setNrOfHourSlots(int nrOfHourSlots) {
        this.nrOfHourSlots = nrOfHourSlots;
    }
}

