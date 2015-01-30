package pl.touk.slack.janusz;

import pl.touk.slack.janusz.commands.JanuszCommand;
import pl.touk.slack.janusz.commands.unknown.UnknownCommand;

import java.util.Map;

public class CommandInvoker {

    private Map<String, JanuszCommand> commands;
    private UnknownCommand unknownCommand = new UnknownCommand();

    private static final String COMMAND_PREFIX = "`";

    public CommandInvoker(Map<String, JanuszCommand> commands) {
        this.commands = commands;
    }

    public String invoke(String messageContent) {
        String [] words = messageContent.split("\\s+");
        return commands.getOrDefault(words[0], unknownCommand).invoke(words);
    }

    public boolean isCommandPrefix(String messageContent) {
        return messageContent.startsWith(COMMAND_PREFIX);
    }
}
