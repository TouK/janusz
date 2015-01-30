package pl.touk.slack.janusz.commands.unknown;

import pl.touk.slack.janusz.commands.JanuszCommand;

import java.util.List;

public class UnknownCommand implements JanuszCommand {
    @Override
    public String invoke(List<String> words) {
        return "Unknown command";
    }
}
