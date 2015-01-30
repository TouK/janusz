package pl.touk.slack.janusz.commands.bus;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import pl.touk.slack.janusz.JanuszStarter;

import static org.assertj.core.api.Assertions.assertThat;

public class BusCommandTest {

    private final BusCommand command = JanuszStarter.createContext().getBean(BusCommand.class);

    @Test
    public void shouldSearchRoutes() {
        assertThat(command.invoke(ImmutableList.of("mokotowska 1, warszawa", "abrahama 18, warszawa"))).isNotEmpty();
    }

}