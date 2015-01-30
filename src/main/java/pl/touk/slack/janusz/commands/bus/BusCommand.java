package pl.touk.slack.janusz.commands.bus;

import pl.touk.slack.janusz.commands.JanuszCommand;

public class BusCommand implements JanuszCommand {
    @Override
    public String invoke(String[] words) {
        return words[1];
    }
}
