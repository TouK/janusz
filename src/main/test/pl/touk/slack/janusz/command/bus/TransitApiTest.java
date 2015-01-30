package pl.touk.slack.janusz.command.bus;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import pl.touk.slack.janusz.commands.bus.TransitApi;

public class TransitApiTest {

    TransitApi api = new TransitApi();

    @Test
    public void shouldReturnRoutes() throws UnirestException {
        System.out.println(api.getDirections("Zebra Tower", "Abrahama 18, Warszawa"));
    }

}