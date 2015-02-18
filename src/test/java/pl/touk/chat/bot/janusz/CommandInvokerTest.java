package pl.touk.chat.bot.janusz;

import org.junit.Before;
import org.junit.Test;
import pl.touk.chat.bot.janusz.commands.Commands;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandInvokerTest {

    private Commands commands;
    private CommandInvoker commandInvoker;
    private MapStore store;

    @Before
    public void setup() {
        store = new MapStore();
        commands = new Commands("bus", (sender, words) -> "invoked with " + words.size() + " " + words);
        commandInvoker = new CommandInvoker(commands, store);
    }

    @Test
    public void shouldInvokeProperCommand() throws IOException {
        assertThat(commandInvoker.invoke("sender", "bus \"mokotowska 1\" 182")).contains("invoked with 2");
    }

    @Test
    public void shouldRecognizeMessageAsCommand() {
        assertThat(commandInvoker.isCommand("`bus test")).isTrue();
    }

    @Test
    public void shouldNotRecognizeMessageAsCommand() {
        assertThat(commandInvoker.isCommand("bus test")).isFalse();
    }

    @Test
    public void shouldReturnUnknownCommand() {
        assertThat(commandInvoker.invoke("sender", "sdsds")).contains("Nie znam");
    }

    @Test
    public void shouldEvaluateVars() {
        store.put("sender", "from", "xxx");
        store.put("sender", "to", "yyy");
        assertThat(commandInvoker.invoke("sender", "bus $from $to")).contains("xxx").contains("yyy");
    }

}
