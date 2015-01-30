package pl.touk.slack.janusz.command.bus;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import pl.touk.slack.janusz.commands.bus.Route;
import pl.touk.slack.janusz.commands.bus.TransitApi;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TransitApiTest {

    TransitApi api = new TransitApi();

    @Test
    public void shouldReturnRoutes() throws UnirestException {
        assertThat(api.getDirections("Zebra Tower", "Abrahama 18, Warszawa")).isNotEmpty();
    }

}