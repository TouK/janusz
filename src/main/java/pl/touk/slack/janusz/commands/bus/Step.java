package pl.touk.slack.janusz.commands.bus;

public class Step {

    public final String line;
    public final String departure;

    public Step(String line, String departure) {
        this.line = line;
        this.departure = departure;
    }

    public String getLine() {
        return line;
    }

    public String getDeparture() {
        return departure;
    }

}
