package pl.touk.chat.bot.janusz.commands.unknown;

import pl.touk.chat.bot.janusz.commands.JanuszCommand;

import java.util.List;

public class UnknownCommand implements JanuszCommand {

    @Override
    public String invoke(String sender, List<String> args) {
        boolean isJanusz = args.stream().anyMatch((input) -> input.toLowerCase().equals("janusz"));

        if (isJanusz) {
            return "No siema, co tam?";
        }

        return "Nie znam się, zarobiony jestem";
    }
}
