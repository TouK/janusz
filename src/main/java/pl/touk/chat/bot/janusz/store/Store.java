package pl.touk.chat.bot.janusz.store;

public interface Store {
    public <T> void put(String user, String key, T value);
    public <T> T get(String user, String key, Class<T> clazz);
}
