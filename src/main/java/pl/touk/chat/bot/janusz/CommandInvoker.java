package pl.touk.chat.bot.janusz;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import pl.touk.chat.bot.janusz.commands.Commands;
import pl.touk.chat.bot.janusz.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

public class CommandInvoker {

    private final Commands commands;
    private final Store store;

    private static final String COMMAND_PREFIX = "`";

    public CommandInvoker(Commands commands, Store store) {
        this.commands = commands;
        this.store = store;
    }

    public String invoke(String sender, String messageContent) {
        try {
            String command = messageContent;
            List<String> commandArgs = new ArrayList<>();
            if (messageContent.contains(" ")) {
                command = substringBefore(messageContent, " ");
                String args = substringAfter(messageContent, " ");

                CSVParser csvRecords = CSVParser.parse(args, CSVFormat.newFormat(' ').withQuote('"'));
                commandArgs = Lists.newArrayList(csvRecords.iterator().next().iterator());

                commandArgs = commandArgs.stream()
                        .map((arg) -> arg.startsWith("$") ? store.get(sender, substringAfter(arg, "$"), String.class) : arg)
                        .collect(Collectors.toList());
            }
            return commands.get(command).invoke(sender, commandArgs);
        } catch (Exception ex) {
            throw new JanuszException(ex);
        }
    }

    public boolean isCommand(String messageContent) {
        return messageContent.startsWith(COMMAND_PREFIX);
    }
}
