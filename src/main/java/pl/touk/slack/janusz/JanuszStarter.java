package pl.touk.slack.janusz;

import com.ullink.slack.simpleslackapi.SlackMessage;
import com.ullink.slack.simpleslackapi.SlackMessageListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import pl.touk.slack.janusz.commands.JanuszCommand;
import pl.touk.slack.janusz.commands.StackOverflowCommand;
import pl.touk.slack.janusz.commands.bus.BusCommand;

import java.util.HashMap;
import java.util.Map;

public class JanuszStarter {

    private SlackSession session;

    private Map<String, JanuszCommand> commands = new HashMap<String, JanuszCommand>() {{
        put("bus", new BusCommand());
        put("stack", new StackOverflowCommand());
    }};

    private CommandInvoker commandInvoker = new CommandInvoker(commands);

    public static void main(String [] args) throws InterruptedException {
        JanuszStarter januszStarter = new JanuszStarter(
            System.getProperty("apiToken", "xoxb-3564096395-Z1ZRglpJIIyIAFX6DNRqvb5o")
        );
        januszStarter.startListening();

        while (true) {
            Thread.sleep(1000);
        }
    }

    public JanuszStarter(String apiToken) {
        session = SlackSessionFactory.createWebSocketSlackSession(apiToken);
    }

    private void startListening() {
        session.addMessageListener(new SlackMessageListener() {
            @Override
            public void onSessionLoad(SlackSession slackSession) {
            }

            @Override
            public void onMessage(SlackMessage slackMessage) {
                if (commandInvoker.isCommandPrefix(slackMessage.getMessageContent())) {
                    String messageWithoutCommandPrefix = slackMessage.getMessageContent().substring(1);
                    session.sendMessageOverWebSocket(slackMessage.getChannel(), commandInvoker.invoke(messageWithoutCommandPrefix), null)
                    ;
                }
            }
        });

        session.connect();
    }

}
