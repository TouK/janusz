package pl.touk.chat.bot.janusz;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import pl.touk.chat.bot.janusz.store.Store;

public class MapStore implements Store {

    private final Table<String, String, Object> table = HashBasedTable.create();

    @Override
    public <T> void put(String user, String key, T value) {
        table.put(user, key, value);
    }

    @Override
    public <T> T get(String user, String key, Class<T> clazz) {
        return (T) table.get(user, key);
    }
}
