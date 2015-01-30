package pl.touk.slack.janusz;

import pl.touk.slack.janusz.commands.JanuszCommand;

import java.util.Map;

public class CommandInvoker {

    private Map<String, JanuszCommand> commands;

    public CommandInvoker(Map<String, JanuszCommand> commands) {
        this.commands = commands;
    }

    public String invoke(String messageContent) {
        String [] words = messageContent.split("\\s+");
        return commands.get(words[0]).invoke(words);
    }
}
