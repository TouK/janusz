package pl.touk.slack.janusz.commands.bus;

import com.mashape.unirest.http.exceptions.UnirestException;
import pl.touk.slack.janusz.JanuszException;
import pl.touk.slack.janusz.commands.JanuszCommand;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class BusCommand implements JanuszCommand {

    @Resource
    private TransitApi transitApi;

    @Override
    public String invoke(List<String> args) {
        try {
            List<Route> directions = transitApi.getDirections(args.get(0), args.get(1));
            return directions.stream().map(route ->
                    "busiki: " + route.getSteps().stream()
                                    .map(step -> step.getLine() + " (" + step.getDeparture() + ")")
                                    .collect(Collectors.joining(", "))
                    + ", czas: " + route.getDuration()
            ).collect(Collectors.joining(","));
        } catch (UnirestException e) {
            throw new JanuszException(e);
        }
    }
}
