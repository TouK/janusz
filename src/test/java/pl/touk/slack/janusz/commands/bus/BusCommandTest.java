package pl.touk.slack.janusz.commands.bus;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import pl.touk.slack.janusz.JanuszStarter;

import static org.assertj.core.api.Assertions.assertThat;

public class BusCommandTest {

    private final BusCommand command = new JanuszStarter("", "").createContext("").getBean(BusCommand.class);

    @Test
    public void shouldSearchRoutes() {
        String output = command.invoke(ImmutableList.of("mokotowska 1, warszawa", "abrahama 18, warszawa"));
        System.out.println(output);
        assertThat(output).isNotEmpty();
    }

}