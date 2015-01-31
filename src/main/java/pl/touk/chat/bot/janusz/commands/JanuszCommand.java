package pl.touk.chat.bot.janusz.commands;

import java.util.List;

public interface JanuszCommand {

    String invoke(List<String> words);
}
