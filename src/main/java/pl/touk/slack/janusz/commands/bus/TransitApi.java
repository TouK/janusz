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
                .asJson();

        List<JSONObject> legs = toList(response.getBody().getObject().getJSONArray("routes").getJSONObject(0).getJSONArray("legs"));
        return legs.stream().map(leg -> new Route(
                leg.getJSONObject("departure_time").getString("text"),
                leg.getJSONObject("arrival_time").getString("text"),
                leg.getJSONObject("duration").getString("text"),
                "411")
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
