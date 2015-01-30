package pl.touk.slack.janusz;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import pl.touk.slack.janusz.commands.JanuszCommand;
import pl.touk.slack.janusz.commands.unknown.UnknownCommand;

import java.util.List;
import java.util.Map;

public class CommandInvoker {

    private final Map<String, JanuszCommand> commands;
    private final UnknownCommand unknownCommand = new UnknownCommand();

    private static final String COMMAND_PREFIX = "`";

    public CommandInvoker(Map<String, JanuszCommand> commands) {
        this.commands = commands;
    }

    public String invoke(String messageContent) {
        try {
            String command = messageContent.substring(0, messageContent.indexOf(' '));
            String args = messageContent.substring(messageContent.indexOf(' ') + 1);

            CSVParser csvRecords = CSVParser.parse(args, CSVFormat.newFormat(' ').withQuote('"'));
            List<String> commandArgs = Lists.newArrayList(csvRecords.iterator().next().iterator());

            return commands.getOrDefault(command, unknownCommand).invoke(commandArgs);
        } catch (Exception ex) {
            throw new JanuszException(ex);
        }
    }

    public boolean isCommandPrefix(String messageContent) {
        return messageContent.startsWith(COMMAND_PREFIX);
    }
}
