package pl.touk.slack.janusz;

import com.ullink.slack.simpleslackapi.SlackMessage;
import com.ullink.slack.simpleslackapi.SlackMessageListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import pl.touk.slack.janusz.commands.JanuszCommand;
import pl.touk.slack.janusz.commands.bus.BusCommand;

import java.util.HashMap;
import java.util.Map;

public class Start {

    public static void main(String [] args) throws InterruptedException {
        final SlackSession session = SlackSessionFactory.createWebSocketSlackSession("xoxb-3564096395-GwPNb0nqUQGUEAyxQQ3wZSWW");


        Map<String, JanuszCommand> commands = new HashMap<String, JanuszCommand>() {{
            put("bus", new BusCommand());
        }};

        CommandInvoker commandInvoker = new CommandInvoker(commands);

        session.addMessageListener(new SlackMessageListener()
        {
            @Override
            public void onSessionLoad(SlackSession slackSession)
            {
            }

            @Override
            public void onMessage(SlackMessage slackMessage)
            {
                session.sendMessageOverWebSocket(slackMessage.getChannel(), commandInvoker.invoke(slackMessage.getMessageContent()), null);
            }
        });
        session.connect();

        while (true)
        {
            Thread.sleep(1000);
        }
    }

}
