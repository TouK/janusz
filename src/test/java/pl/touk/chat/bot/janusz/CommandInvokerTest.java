package pl.touk.chat.bot.janusz;

import org.junit.Test;
import pl.touk.chat.bot.janusz.commands.JanuszCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandInvokerTest {

    private Map<String, JanuszCommand> commands = new HashMap<>(new HashMap<String, JanuszCommand>() {{
        put("bus", words -> "invoked with " + words.size());
    }});

    private CommandInvoker commandInvoker = new CommandInvoker(commands);

    @Test
    public void shouldInvokeProperCommand() throws IOException {
        assertThat(commandInvoker.invoke("bus \"mokotowska 1\" 182")).isEqualTo("invoked with 2");
    }

    @Test
    public void shouldRecognizeMessageAsCommand() {
        assertThat(commandInvoker.isCommandPrefix("`bus test")).isTrue();
    }

    @Test
    public void shouldNotRecognizeMessageAsCommand() {
        assertThat(commandInvoker.isCommandPrefix("bus test")).isFalse();
    }

    @Test
    public void shouldReturnUnknownCommand() {
        assertThat(commandInvoker.invoke("`sdsds").contains("Unknown"));
    }

}