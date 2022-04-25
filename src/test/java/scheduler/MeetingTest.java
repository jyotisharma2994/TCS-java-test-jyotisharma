package scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class MeetingTest {

    private MeetingScheduler meetingScheduler;

    @BeforeEach
    public void setUp() {
        meetingScheduler = new MeetingScheduler();
    }

    @Test
    public void shouldPrintMeetingSchedule() throws IOException {
        Schedule actualOutput = meetingScheduler.schedule("0900 1730\n" +
                "2016-07-18 10:17:06 EMP001\n" +
                "2016-07-21 09:00 2\n" +
                "2016-07-18 12:34:56 EMP002\n" +
                "2016-07-21 09:00 2\n" +
                "2016-07-18 09:28:23 EMP003\n" +
                "2016-07-22 14:00 2\n" +
                "2016-07-18 11:23:45 EMP004\n" +
                "2016-07-22 16:00 1\n" +
                "2016-07-15 17:29:12 EMP005\n" +
                "2016-07-21 16:00 3\n");
        String actual = meetingScheduler.generateOutput(actualOutput.getScheduledMeeting());
        String expectedOutput = "2016-07-21\n" + "09:00 11:00 EMP001\n"
                + "2016-07-22\n" + "14:00 16:00 EMP003\n"
                + "16:00 17:00 EMP004\n";
        Assertions.assertEquals(actual, expectedOutput);
    }

    @Test
    public void shouldParseOfficeHours() {
        String officeHourRequest = "0900 1730\n";

        Schedule schedule = meetingScheduler.calculateOfficeHours(officeHourRequest.split(" "));
        Assertions.assertEquals(schedule.getOfficeStartTime().getHour(), 9);
        Assertions.assertEquals(schedule.getOfficeEndTime().getHour(), 17);
        Assertions.assertEquals(schedule.getOfficeEndTime().getMinute(), 30);
    }

    @Test
    public void noPartOfMeetingMayFallOutsideOfficeHours() throws IOException {
        String request = "0900 1730\n" + "2011-03-15 17:29:12 EMP005\n"
                + "2011-03-21 16:00 3\n";
        Schedule schedule = meetingScheduler.schedule(request);
        Assertions.assertEquals(0, schedule.getScheduledMeeting().size());
    }

    @Test
    public void meetingsShouldNotOverlap() {
        String request = "0900 1730\n" + "2011-03-17 10:17:06 EMP001\n"
                + "2011-03-21 09:00 2\n" + "2011-03-16 12:34:56 EMP002\n"
                + "2011-03-21 10:00 1\n";

        Schedule schedule = meetingScheduler.schedule(request);
        LocalDateTime meetingDate = LocalDateTime.of(2011, 3, 21, 10, 0, 0);
        Assertions.assertEquals(1, schedule.getScheduledMeeting().size());
        Meeting meeting = schedule.getScheduledMeeting().get(meetingDate);
        Assertions.assertEquals("EMP002", meeting.getEmpId());
        Assertions.assertEquals(LocalDateTime.of(2011, 3, 21, 10, 0, 0), meeting.getMeetingStartTime());
        Assertions.assertEquals(10, meeting.getMeetingStartTime().getHour());
        Assertions.assertEquals(0, meeting.getMeetingStartTime().getMinute());
        Assertions.assertEquals(11, meeting.getMeetingEndTime().getHour());
        Assertions.assertEquals(0, meeting.getMeetingEndTime().getMinute());
    }
}
