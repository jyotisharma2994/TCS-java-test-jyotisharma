package scheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class Schedule {
    private LocalTime officeStartTime;
    private LocalTime officeEndTime;
    private Map<LocalDateTime, Meeting> scheduledMeeting;

    public LocalTime getOfficeStartTime() {
        return officeStartTime;
    }

    public void setOfficeStartTime(LocalTime officeStartTime) {
        this.officeStartTime = officeStartTime;
    }

    public LocalTime getOfficeEndTime() {
        return officeEndTime;
    }

    public void setOfficeEndTime(LocalTime officeEndTime) {
        this.officeEndTime = officeEndTime;
    }

    public Map<LocalDateTime, Meeting> getScheduledMeeting() {
        return scheduledMeeting;
    }

    public void setScheduledMeeting(Map<LocalDateTime, Meeting> scheduledMeeting) {
        this.scheduledMeeting = scheduledMeeting;
    }
}
