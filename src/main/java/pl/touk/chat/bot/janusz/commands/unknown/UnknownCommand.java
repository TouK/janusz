package pl.touk.chat.bot.janusz.commands.unknown;

import pl.touk.chat.bot.janusz.commands.JanuszCommand;

import java.util.List;

public class UnknownCommand implements JanuszCommand {

    @Override
    public String invoke(List<String> words) {
        boolean isJanusz = words.stream().anyMatch((input) -> input.toLowerCase().equals("janusz"));

        if (isJanusz) {
            return "No siema, co tam?";
        }

        return "Nie znam się, zarobiony jestem";
    }
}
