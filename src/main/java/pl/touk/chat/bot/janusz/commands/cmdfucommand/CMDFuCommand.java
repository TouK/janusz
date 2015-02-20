package pl.touk.chat.bot.janusz.commands.cmdfucommand;

import com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.touk.chat.bot.janusz.commands.JanuszCommand;

import java.util.Base64;
import java.util.List;

public class CMDFuCommand implements JanuszCommand {

    public static final String CMDFU_API_PATH = "http://www.commandlinefu.com/commands";

    public static final String HELP_MESSAGE = "Zagnij devopsa komendą";
    public static final String ERROR_MESSAGE = "Przepraszam, zachowałem się jak gówniarz";

    Logger log = LoggerFactory.getLogger(CMDFuCommand.class);

    @Override
    public String invoke(String sender, List<String> words) {
        String question = buildCommand(words);

        if (isCommandEmpty(question)) {
            return HELP_MESSAGE;
        }

        try {
            return askCMDFu(question);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ERROR_MESSAGE;
    }

    private boolean isCommandEmpty(String question) {
        return question == null || "".equals(question);
    }

    private String buildCommand(List<String> params) {
        return Joiner.on(",").join(params);
    }

    private String askCMDFu(String question) throws UnirestException {
        try {
            return retriveFromCMDFu(question);
        } catch (UnirestException e) {
            log.error("Przepraszam, zachowałem się jak gówniarz {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private String retriveFromCMDFu(String question) throws UnirestException {
        HttpResponse<JsonNode> cmdfuResponse = Unirest.get(CMDFU_API_PATH + "//matching/{search}/{b64_search}/bys=/sort-by-votes/json")
                .routeParam("search", question)
                .routeParam("b64_search", new String(Base64.getEncoder().encode(question.getBytes())))
                .asJson();
        System.out.println("cmdfuResponse = " + cmdfuResponse);
        log.info("Command for {}", question);
        log.info("CMDFu response: {}", cmdfuResponse.getBody().toString());

        String response = "";

        for (int i = 0; i < 3; i++) {
            response += cmdfuResponse.getBody().getArray().getJSONObject(i).get("command").toString()+"\n";
        }
        log.info("Got commands {}", response);

        return response;
    }
}
