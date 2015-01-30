package pl.touk.slack.janusz.commands;

import org.junit.Test;

public class StackOverflowCommandTest {

    @Test
    public void asksStack() {
        StackOverflowCommand command = new StackOverflowCommand();
        String invoke = command.invoke(new String[]{
                "java"
        });

    }
}