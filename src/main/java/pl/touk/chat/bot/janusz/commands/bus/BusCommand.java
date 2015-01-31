package pl.touk.chat.bot.janusz.commands.bus;

import com.mashape.unirest.http.exceptions.UnirestException;
import pl.touk.chat.bot.janusz.JanuszException;
import pl.touk.chat.bot.janusz.commands.JanuszCommand;

import java.util.List;
import java.util.stream.Collectors;

public class BusCommand implements JanuszCommand {

    private TransitApi transitApi;

    public BusCommand(TransitApi transitApi) {
        this.transitApi = transitApi;
    }

    @Override
    public String invoke(List<String> args) {
        try {
            List<Route> directions = transitApi.getDirections(args.get(0), args.get(1));
            return directions.stream().map(route ->
                    route.getSteps().stream()
                                    .map(step -> step.getLine() + " (" + step.getStop() + " o " + step.getDeparture() + ")")
                                    .collect(Collectors.joining(" -> "))
                    + " czas: " + route.getDuration()
            ).collect(Collectors.joining("\n"));
        } catch (UnirestException e) {
            throw new JanuszException(e);
        }
    }
}
