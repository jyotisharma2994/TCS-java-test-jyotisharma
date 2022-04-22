package booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scheduler.Meeting;
import scheduler.MeetingScheduler;

import org.junit.jupiter.api.Assertions;
import scheduler.Schedule;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class MeetingTest {

    private MeetingScheduler meetingScheduler;

    @BeforeEach
    public void setUp() {
        meetingScheduler = new MeetingScheduler();
    }

    @Test
    public void shouldPrintMeetingSchedule() throws IOException {
        Schedule schedule = meetingScheduler.schedule("src/test/resources/inputCorrect.txt");
        String actualOutput = meetingScheduler.generateOutput(schedule.getScheduledMeeting());

        String expectedOutput = "2016-07-21\n" + "09:00 11:00 EMP001\n"
                + "2016-07-22\n" + "14:00 16:00 EMP003\n"
                + "16:00 17:00 EMP004\n";

        Assertions.assertEquals(actualOutput, expectedOutput);
    }
}
