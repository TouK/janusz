package pl.touk.slack.janusz.commands.bus;

import java.time.LocalDate;

public class Route {

    public final String start;
    public final String end;
    public final String duration;
    public final String line;

    public Route(String start, String end, String duration, String line) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.line = line;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDuration() {
        return duration;
    }

    public String getLine() {
        return line;
    }
}
