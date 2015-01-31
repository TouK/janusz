package pl.touk.chat.bot.janusz.commands.bus;

import java.util.List;

public class Route {

    public final String start;
    public final String end;
    public final String duration;
    public final List<Step> steps;

    public Route(String start, String end, String duration, List<Step> steps) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.steps = steps;
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

    public List<Step> getSteps() {
        return steps;
    }
}
