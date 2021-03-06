package pl.touk.chat.bot.janusz.commands.bus;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransitApiTest {

    TransitApi api = new TransitApi();

    @Test
    public void shouldReturnRoutes() throws UnirestException {
        assertThat(api.getDirections("Zebra Tower", "Abrahama 18, Warszawa")).isNotEmpty();
    }

}