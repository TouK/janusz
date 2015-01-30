package pl.touk.slack.janusz.commands;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import pl.touk.slack.janusz.commands.stack.StackOverflowCommand;

public class StackOverflowCommandTest {

    @Test
    public void asksStack() {
        StackOverflowCommand command = new StackOverflowCommand();
        String invoke = command.invoke(ImmutableList.of("java"));
    }

}