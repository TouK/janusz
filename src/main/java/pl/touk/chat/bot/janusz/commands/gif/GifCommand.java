package pl.touk.chat.bot.janusz.commands.gif;

import com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.touk.chat.bot.janusz.commands.JanuszCommand;
import pl.touk.chat.bot.janusz.config.JanuszConfiguration;

import java.util.List;

public class GifCommand implements JanuszCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(GifCommand.class);
    private static final String GIPHY_API_URL = "http://api.giphy.com/v1/gifs/search";

    private final String apiKey;
    private final Joiner spaceJoiner = Joiner.on(" ");

    public GifCommand(JanuszConfiguration configuration) {
        this.apiKey = configuration.giphy.apiToken;
    }

    @Override
    public String invoke(String sender, List<String> args) {
        String response = String.format("Sorry, gif '%s' not found", spaceJoiner.join(args));
        try {
            HttpResponse<JsonNode> giphyAPIResponse = Unirest.get(GIPHY_API_URL)
                .queryString("limit", "1")
                .queryString("api_key", apiKey)
                .queryString("q", spaceJoiner.join(args))
                .asJson();

            JSONArray jsonArray = giphyAPIResponse.getBody().getObject().getJSONArray("data");

            if (jsonArray.length() == 0) {
                return response;
            }

            String url = jsonArray.getJSONObject(0).getJSONObject("images").getJSONObject("fixed_width").getString("url");

            return String.format("gif %s %s", spaceJoiner.join(args), url);
        } catch (UnirestException e) {
            LOGGER.warn("Something went wrong while sending request to giphy", e);
        }

        return response;
    }

}
