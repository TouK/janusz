package pl.touk.slack.janusz.commands.unknown;

import pl.touk.slack.janusz.commands.JanuszCommand;

public class UnknownCommand implements JanuszCommand {
    @Override
    public String invoke(String[] words) {
        return "Unknown command " + words[0];
    }
}
