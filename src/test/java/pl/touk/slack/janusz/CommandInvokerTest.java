package pl.touk.slack.janusz;

import org.junit.Test;
import pl.touk.slack.janusz.commands.JanuszCommand;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandInvokerTest {

    private Map<String, JanuszCommand> commands = new HashMap<>(new HashMap<String, JanuszCommand>() {{
        put("bus", words -> "invoked");
    }});

    private CommandInvoker commandInvoker = new CommandInvoker(commands);

    @Test
    public void shouldInvokeProperCommand() {
        String response = commandInvoker.invoke("bus mokotowska1 182");

        assertThat(response).isEqualTo("invoked");
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