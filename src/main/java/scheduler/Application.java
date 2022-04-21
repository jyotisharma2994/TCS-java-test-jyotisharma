package scheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader =
                new BufferedReader(new FileReader("src/main/resources/input.txt"));
        String inputRequest = readAllLinesWithStream(reader);
        MeetingScheduler meetingScheduler = new MeetingScheduler();
        meetingScheduler.schedule(inputRequest);
    }

    private static String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

