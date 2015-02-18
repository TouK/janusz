package pl.touk.chat.bot.janusz.commands.store;

import pl.touk.chat.bot.janusz.commands.JanuszCommand;
import pl.touk.chat.bot.janusz.store.Store;

import java.util.List;

public class StoreCommand implements JanuszCommand {

    private final Store store;

    public StoreCommand(Store store) {
        this.store = store;
    }

    @Override
    public String invoke(String sender, List<String> args) {
        String key = args.get(0);
        String value = args.get(1);
        store.put(sender, key, value);
        return "Zapisał \"" + value + "\" pod kluczem " + key;
    }
}
