package scheduler;

import java.time.LocalTime;
import java.util.Map;

public class Schedule {
    private LocalTime officeStartTime;
    private LocalTime officeEndTime;
    private Map<Long, Meeting> scheduledMeeting;

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

    public Map<Long, Meeting> getScheduledMeeting() {
        return scheduledMeeting;
    }

    public void setScheduledMeeting(Map<Long, Meeting> scheduledMeeting) {
        this.scheduledMeeting = scheduledMeeting;
    }
}
