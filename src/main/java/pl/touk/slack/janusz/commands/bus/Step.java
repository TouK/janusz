package pl.touk.slack.janusz.commands.bus;

public class Step {

    public final String line;
    public final String stop;
    public final String departure;

    public Step(String line, String stop, String departure) {
        this.line = line;
        this.stop = stop;
        this.departure = departure;
    }

    public String getLine() {
        return line;
    }

    public String getStop() {
        return stop;
    }

    public String getDeparture() {
        return departure;
    }

}
