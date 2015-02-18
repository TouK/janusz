package pl.touk.chat.bot.janusz.commands.bus;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BusCommandTest {

    private final BusCommand command = new BusCommand(new TransitApi());

    @Test
    public void shouldSearchRoutes() {
        String output = command.invoke("sender", ImmutableList.of("mokotowska 1, warszawa", "abrahama 18, warszawa"));
        System.out.println(output);
        assertThat(output).isNotEmpty();
    }

}