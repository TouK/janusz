package pl.touk.slack.janusz.commands;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.sun.deploy.util.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class StackOverflowCommand implements JanuszCommand {
    public static final String HELP_MESSAGE = "StackOverflow rocks! Ask any question, lets check the answer!";

    Logger log = LoggerFactory.getLogger(StackOverflowCommand.class);

    @Override
    public String invoke(String[] words) {
        String question = buildQuestion(words);

        if (question == null || "".equals(question)) {
            return HELP_MESSAGE;
        }

        String answer = "no answer";
        try {
            answer = askStackOverflow(question);
        } catch (Exception e) {
            return "Wrong answer:(";
        }

        return answer;
    }

    private String buildQuestion(String[] words) {
        List<String> paramsWithCommand = Arrays.asList(words);
        List<String> params = paramsWithCommand.subList(1, paramsWithCommand.size());
        return StringUtils.join(params, " ");
    }

    private String askStackOverflow(String question) throws UnirestException {
        HttpRequest request = Unirest.get("https://api.stackexchange.com/2.2/search/advanced")
                .queryString("q", question)
                .queryString("site", "stackoverflow");

        try {
            log.info("Asking for {}", question);
            HttpResponse<JsonNode> stackResponse = request.asJson();
            JSONObject jsonLink = stackResponse.getBody().getObject().getJSONArray("items").getJSONObject(1);
            String response = jsonLink.getString("link").toString();
            log.info("Got answer {}", response);
            return response;
        } catch (UnirestException e) {
            log.error("Ups something gone wrong {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
