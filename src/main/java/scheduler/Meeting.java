package scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meeting {
    String empId;
    LocalDate meetingDate;
    LocalTime startTime;
    LocalTime endTime;
    LocalDateTime meetingStartTime;
    LocalDateTime meetingEndTime;

    public Meeting(String empId, LocalDate meetingDate, LocalTime startTime, LocalTime endTime) {
        this.empId = empId;
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        meetingStartTime = LocalDateTime.of(meetingDate, startTime);
        meetingEndTime = LocalDateTime.of(meetingDate, endTime);
    }

    public String getEmpId() {
        return empId;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public LocalDateTime getMeetingEndTime() {
        return meetingEndTime;
    }
}

