package pl.touk.slack.janusz.commands.bus;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransitApi {

    private final static String DIRECTIONS_URL =
            "https://maps.googleapis.com/maps/api/directions/json";

    public List<Route> getDirections(String from, String to) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(DIRECTIONS_URL)
                .queryString("origin", from)
                .queryString("destination", to)
                .queryString("departure_time", Clock.systemUTC().millis() / 1000)
                .queryString("mode", "transit")
                .queryString("alternatives", "true")
                .asJson();

        List<JSONObject> routes = toList(response.getBody().getObject().getJSONArray("routes"));
        return routes.stream().map(route -> {
                    JSONObject leg = route.getJSONArray("legs").getJSONObject(0);
                    List<JSONObject> stepsJson = toList(leg.getJSONArray("steps"));
                    List<Step> steps =
                            stepsJson.stream()
                                    .filter(step -> "TRANSIT".equals(step.getString("travel_mode")))
                                    .map(stepJson -> {
                                        JSONObject transitDetails = stepJson.getJSONObject("transit_details");
                                        return new Step(
                                                transitDetails.getJSONObject("line").getString("short_name"),
                                                transitDetails.getJSONObject("departure_stop").getString("name"),
                                                transitDetails.getJSONObject("departure_time").getString("text"));
                                    }).collect(Collectors.toList());
                    return new Route(
                            leg.getJSONObject("departure_time").getString("text"),
                            leg.getJSONObject("arrival_time").getString("text"),
                            leg.getJSONObject("duration").getString("text"),
                            steps);
                }
        ).collect(Collectors.toList());
    }

    private List<JSONObject> toList(JSONArray array) {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        return list;
    }
}
