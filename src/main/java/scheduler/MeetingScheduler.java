package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

public class MeetingScheduler {

    /**
     *
     * @param fileName input file
     * @return scheduled meetings
     * @throws IOException
     */
    public Map<Long, Meeting> schedule(String fileName) throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader(fileName));
        String[] officeTime = reader.readLine().split(" ");
        Map<Long, Meeting> meetingReqMap = new TreeMap<>();
        Map<String, LocalTime> officeHours = calculateOfficeHours(officeTime);

        String requestLine;
        while ((requestLine = reader.readLine()) != null) {
            String nextLine = reader.readLine();
            String[] lineSplit = requestLine.split(" ");
            String[] dateSplit = lineSplit[0].split("-");
            String[] timeSplit = lineSplit[1].split(":");
            String[] meetingRequestSplit = nextLine.split(" ");
            String[] meetingDateSplit = meetingRequestSplit[0].split("-");
            String[] meetingStartTime = meetingRequestSplit[1].split(":");

            LocalDateTime submissionDateTime = LocalDateTime.of(parseInt(dateSplit[0]), parseInt(dateSplit[1]),
                    parseInt(dateSplit[2]), parseInt(timeSplit[0]), parseInt(timeSplit[1]),
                    parseInt(timeSplit[2]));
            LocalTime meetingStartLocalTime = LocalTime.of(parseInt(meetingStartTime[0]),
                    parseInt(meetingStartTime[1]));
            LocalTime meetingEndLocalTime = meetingStartLocalTime.plusHours(Long.parseLong(meetingRequestSplit[2]));
            long submissionDateTimeEpoch = submissionDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();

            Meeting meeting = new Meeting(lineSplit[2],
                    LocalDate.of(parseInt(meetingDateSplit[0]), parseInt(meetingDateSplit[1]),
                            parseInt(meetingDateSplit[2])),
                    meetingStartLocalTime, meetingEndLocalTime);

            meetingReqMap.put(submissionDateTimeEpoch, meeting);
        }
        return extractMeeting(meetingReqMap, officeHours);
    }

    /**
     * @param scheduledMeetings confirmed meetings
     * @return output as desired with meeting details and empId of requestor
     */
    public String generateOutput(Map<Long, Meeting> scheduledMeetings) {
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
     * @param meetingReqMap meeting requests
     * @param officeHours office hours
     * @return possible confirmed meetings as per request
     */
    private Map<Long, Meeting> extractMeeting(Map<Long, Meeting> meetingReqMap, Map<String, LocalTime> officeHours) {
        Map<Long, Meeting> scheduledMeetings = new TreeMap<>();
        LocalTime officeStartTime = officeHours.get("officeStartTime");
        LocalTime officeEndTime = officeHours.get("officeEndTime");
        meetingReqMap.forEach((k, v) -> {
            if (v.getStartTime().isAfter(officeStartTime) && v.getEndTime().isBefore(officeEndTime)) {
                if (scheduledMeetings.size() == 0) {
                    scheduledMeetings.put(v.getMeetingStartTime().atZone(ZoneId.systemDefault()).toEpochSecond(), v);
                } else {
                    boolean isOverlap = true;
                    for (Map.Entry<Long, Meeting> entry : scheduledMeetings.entrySet()) {
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
                            scheduledMeetings.put(v.meetingStartTime.atZone(ZoneId.systemDefault()).toEpochSecond(), v);
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
    private Map<String, LocalTime> calculateOfficeHours(String[] officeTime) {
        Map<String, LocalTime> officeTimes = new HashMap<>();
        LocalTime officeStartTime = LocalTime
                .of(parseInt(officeTime[0].substring(0, 2)), parseInt(officeTime[0].substring(2, 4)))
                .minusMinutes(1);
        LocalTime officeEndTime = LocalTime
                .of(parseInt(officeTime[1].substring(0, 2)), parseInt(officeTime[1].substring(2, 4)))
                .plusMinutes(1);
        officeTimes.put("officeStartTime", officeStartTime);
        officeTimes.put("officeEndTime", officeEndTime);
        return officeTimes;
    }
}
