package pl.touk.chat.bot.janusz.commands.stack;

import com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.touk.chat.bot.janusz.commands.JanuszCommand;

import java.util.List;

public class StackOverflowCommand implements JanuszCommand {

    public static final String STACK_API_PATH = "https://api.stackexchange.com/2.2/search/advanced";

    public static final String HELP_MESSAGE = "StackOverflow rocks! Zadaj pytanie, a ja Ci odpowiem!\nNapisz:\n`stack tu leci pytanie;)";
    public static final String ERROR_MESSAGE = "Daj mi spokój, nie znasz wujka googla? http://www.google.pl";

    Logger log = LoggerFactory.getLogger(StackOverflowCommand.class);

    @Override
    public String invoke(String sender, List<String> args) {
        String question = buildQuestion(args);

        if (isQuestionEmpty(question)) {
            return HELP_MESSAGE;
        }

        try {
            return askStackOverflow(question);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ERROR_MESSAGE;
    }

    private boolean isQuestionEmpty(String question) {
        return question == null || "".equals(question);
    }

    private String buildQuestion(List<String> params) {
        return Joiner.on(" ").join(params);
    }

    private String askStackOverflow(String question) throws UnirestException {
        try {
            return retriveFromStack(question);
        } catch (UnirestException e) {
            log.error("Ups something gone wrong {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private String retriveFromStack(String question) throws UnirestException {
        HttpRequest request = Unirest.get(STACK_API_PATH)
                .queryString("q", "\"" + question + "\"")
                .queryString("site", "stackoverflow")
                .queryString("sort", "relevance");

        log.info("Asking for {}", question);
        HttpResponse<JsonNode> stackResponse = request.asJson();
        log.info("Stack response: {}", stackResponse.getBody().toString());

        JSONObject jsonLink = stackResponse.getBody().getObject().getJSONArray("items").getJSONObject(1);
        String response = jsonLink.getString("link").toString();
        log.info("Got answer {}", response);

        return response;
    }
}
