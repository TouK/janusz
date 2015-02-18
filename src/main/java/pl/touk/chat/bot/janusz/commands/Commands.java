package pl.touk.chat.bot.janusz.commands;

import com.google.common.collect.ImmutableMap;
import pl.touk.chat.bot.janusz.commands.unknown.UnknownCommand;

import java.util.Map;

public class Commands {

    private final Map<String, JanuszCommand> commands;
    private final UnknownCommand unknownCommand = new UnknownCommand();

    public Commands(Map<String, JanuszCommand> commands) {
        this.commands = commands;
    }

    public Commands(String key, JanuszCommand command) {
        this.commands = ImmutableMap.of(key, command);
    }

    public JanuszCommand get(String command) {
        return commands.getOrDefault(command, unknownCommand);
    }
}
