package pl.touk.chat.bot.janusz.commands.stack;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

public class StackOverflowCommandTest {

    @Test
    public void asksStack() {
        StackOverflowCommand command = new StackOverflowCommand();
        command.invoke("sender", ImmutableList.of("java"));
    }

}