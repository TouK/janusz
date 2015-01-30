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

    @Test
    public void shouldInvokeProperCommand() {
        CommandInvoker commandInvoker = new CommandInvoker(commands);

        String response = commandInvoker.invoke("bus mokotowska1 182");

        assertThat(response).isEqualTo("invoked");
    }

}