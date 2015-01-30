package pl.touk.slack.janusz.commands.unknown;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import pl.touk.slack.janusz.commands.JanuszCommand;

import java.util.List;

public class UnknownCommand implements JanuszCommand {
    @Override
    public String invoke(List<String> words) {
        boolean isJanusz = Iterables.contains(words, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.toLowerCase().equals("janusz");
            }
        });

        if (isJanusz) {
            return "No siema, co tam?";
        }

        return "Nie znam się, zarobiony jestem";
    }
}
