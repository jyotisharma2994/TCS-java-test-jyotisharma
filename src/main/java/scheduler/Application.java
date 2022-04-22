package scheduler;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        MeetingScheduler meetingScheduler = new MeetingScheduler();
        String fileName = "src/main/resources/input.txt";
        Schedule schedule = meetingScheduler.schedule(fileName);
        String bookedMeetings = meetingScheduler.generateOutput(schedule.getScheduledMeeting());
        System.out.println(bookedMeetings);
    }
}

