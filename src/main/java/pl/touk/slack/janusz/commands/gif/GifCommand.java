package pl.touk.slack.janusz.commands.gif;

import com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.touk.slack.janusz.commands.JanuszCommand;

import java.util.List;

public class GifCommand implements JanuszCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(GifCommand.class);
    private static final String GIPHY_API_URL = "http://api.giphy.com/v1/gifs/search";

    private final String apiKey;
    private final Joiner spaceJoiner = Joiner.on(" ");

    public GifCommand(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String invoke(List<String> words) {
        String response = String.format("Sorry, gif '%s' not found", spaceJoiner.join(words));
        try {
            HttpResponse<JsonNode> giphyAPIResponse = Unirest.get(GIPHY_API_URL)
                .queryString("limit", "1")
                .queryString("api_key", apiKey)
                .queryString("q", spaceJoiner.join(words))
                .asJson();

            JSONArray jsonArray = giphyAPIResponse.getBody().getObject().getJSONArray("data");

            if (jsonArray.length() == 0) {
                return response;
            }

            String url = jsonArray.getJSONObject(0).getJSONObject("images").getJSONObject("fixed_width_still").getString("url");

            return String.format("gif %s %s", spaceJoiner.join(words), url);
        } catch (UnirestException e) {
            LOGGER.warn("Something went wrong while sending request to giphy", e);
        }

        return response;
    }

}
