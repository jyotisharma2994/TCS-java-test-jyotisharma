package scheduler;

import java.io.IOException;
import java.util.Map;

public class Application {

    public static void main(String[] args) throws IOException {
        MeetingScheduler meetingScheduler = new MeetingScheduler();
        String fileName = "src/main/resources/input.txt";
        Map<Long, Meeting> scheduledMeetings = meetingScheduler.schedule(fileName);
        String bookedMeetings = meetingScheduler.generateOutput(scheduledMeetings);
        System.out.println(bookedMeetings);
    }
}

