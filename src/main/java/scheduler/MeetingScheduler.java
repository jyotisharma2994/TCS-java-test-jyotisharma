package scheduler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

public class MeetingScheduler {

    /**
     * @param inputRequest input request
     * @return scheduled meetings
     * @throws IOException
     */
    public Schedule schedule(String inputRequest) {

        String[] request = inputRequest.split("\n");
        String[] officeTime = request[0].split(" ");
        Map<LocalDateTime, Meeting> meetingReqMap = new TreeMap<>();
        Schedule schedule = calculateOfficeHours(officeTime);

        for (int i = 1; i < request.length; i = i + 2) {
            String req = request[i];
            String nextLine = request[i + 1];
            String[] lineSplit = req.split(" ");
            String[] dateSplit = lineSplit[0].split("-");
            String[] timeSplit = lineSplit[1].split(":");
            String[] meetingRequestSplit = nextLine.split(" ");
            String[] meetingDateSplit = meetingRequestSplit[0].split("-");
            String[] meetingStartTime = meetingRequestSplit[1].split(":");

            LocalDateTime submissionDateTime = LocalDateTime.of(parseInt(dateSplit[0]), parseInt(dateSplit[1]),
                    parseInt(dateSplit[2]), parseInt(timeSplit[0]), parseInt(timeSplit[1]));
            LocalTime meetingStartLocalTime = LocalTime.of(parseInt(meetingStartTime[0]),
                    parseInt(meetingStartTime[1]));
            LocalTime meetingEndLocalTime = meetingStartLocalTime.plusHours(Long.parseLong(meetingRequestSplit[2]));

            Meeting meeting = new Meeting(lineSplit[2],
                    LocalDate.of(parseInt(meetingDateSplit[0]), parseInt(meetingDateSplit[1]),
                            parseInt(meetingDateSplit[2])),
                    meetingStartLocalTime, meetingEndLocalTime);

            meetingReqMap.put(submissionDateTime, meeting);
        }
        schedule.setScheduledMeeting(extractMeeting(meetingReqMap, schedule));
        return schedule;
    }

    /**
     * @param scheduledMeetings confirmed meetings
     * @return output as desired with meeting details and empId of requestor
     */
    public String generateOutput(Map<LocalDateTime, Meeting> scheduledMeetings) {
        StringBuilder sb = new StringBuilder();
        LocalDate meetingDate = null;
        for (Meeting meeting : scheduledMeetings.values()) {
            if (meetingDate == null || !meetingDate.equals(meeting.getMeetingDate())) {
                meetingDate = meeting.getMeetingDate();
                sb.append(meetingDate).append("\n");
            }
            sb.append(meeting.getStartTime()).append(" ").append(meeting.getEndTime()).append(" ").append(meeting.getEmpId()).append("\n");
        }
        return sb.toString();
    }

    /**
     * @param meetingReqMap  meeting requests
     * @param officeSchedule office hours
     * @return possible confirmed meetings as per request
     */
    private Map<LocalDateTime, Meeting> extractMeeting(Map<LocalDateTime, Meeting> meetingReqMap, Schedule officeSchedule) {
        Map<LocalDateTime, Meeting> scheduledMeetings = new TreeMap<>();
        LocalTime officeStartTime = officeSchedule.getOfficeStartTime();
        LocalTime officeEndTime = officeSchedule.getOfficeEndTime();
        meetingReqMap.forEach((k, v) -> {
            if (v.getStartTime().isAfter(officeStartTime) && v.getEndTime().isBefore(officeEndTime) ||
                    v.getEndTime().equals(officeEndTime) || v.getStartTime().equals(officeStartTime)) {
                if (scheduledMeetings.size() == 0) {
                    scheduledMeetings.put(v.getMeetingStartTime(), v);

                } else {
                    boolean isOverlap = true;
                    for (Map.Entry<LocalDateTime, Meeting> entry : scheduledMeetings.entrySet()) {
                        if ((v.meetingStartTime.isBefore(entry.getValue().meetingStartTime)
                                && (v.meetingEndTime.isBefore(entry.getValue().meetingStartTime)
                                || v.meetingEndTime.isEqual(entry.getValue().meetingStartTime)))
                                || ((v.meetingStartTime.isAfter(entry.getValue().meetingEndTime)
                                || v.meetingStartTime.isEqual(entry.getValue().meetingEndTime))
                                && v.meetingEndTime.isAfter(entry.getValue().meetingEndTime))) {
                            isOverlap = false;
                        } else {
                            break;
                        }
                        if (!isOverlap) {
                            scheduledMeetings.put(v.meetingStartTime, v);
                            break;
                        }
                    }
                }
            }
        });
        return scheduledMeetings;
    }

    /**
     * @param officeTime
     * @return office start and end time
     */
    public Schedule calculateOfficeHours(String[] officeTime) {
        LocalTime officeStartTime = LocalTime
                .of(parseInt(officeTime[0].substring(0, 2)), parseInt(officeTime[0].substring(2, 4)));
        LocalTime officeEndTime = LocalTime
                .of(parseInt(officeTime[1].substring(0, 2)), parseInt(officeTime[1].substring(2, 4)));
        Schedule schedule = new Schedule();
        schedule.setOfficeStartTime(officeStartTime);
        schedule.setOfficeEndTime(officeEndTime);
        return schedule;
    }
}
