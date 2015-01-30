package pl.touk.slack.janusz;

import com.truward.di.InjectionContext;
import com.truward.di.support.DefaultInjectionContext;
import com.ullink.slack.simpleslackapi.SlackMessage;
import com.ullink.slack.simpleslackapi.SlackMessageListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import pl.touk.slack.janusz.commands.JanuszCommand;
import pl.touk.slack.janusz.commands.stack.StackOverflowCommand;
import pl.touk.slack.janusz.commands.bus.BusCommand;
import pl.touk.slack.janusz.commands.bus.TransitApi;

import java.util.HashMap;
import java.util.Map;

public class JanuszStarter {

    private final SlackSession session;

    private final CommandInvoker commandInvoker;

    public static void main(String [] args) throws InterruptedException {
        JanuszStarter januszStarter = new JanuszStarter(System.getProperty("apiToken"));
        januszStarter.startListening();

        while (true) {
            Thread.sleep(1000);
        }
    }

    public JanuszStarter(String apiToken) {
        session = SlackSessionFactory.createWebSocketSlackSession(apiToken);
        commandInvoker = new CommandInvoker(createCommands(createContext()));
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

    public static InjectionContext createContext() {
        InjectionContext context = new DefaultInjectionContext();
        context.registerBean(new TransitApi());
        context.registerBean(new BusCommand());
        context.registerBean(new StackOverflowCommand());
        context.freeze();
        return context;
    }

    private Map<String, JanuszCommand> createCommands(InjectionContext context) {
        return new HashMap<String, JanuszCommand>() {{
            put("bus", context.getBean(BusCommand.class));
            put("stack", context.getBean(StackOverflowCommand.class));
        }};
    }
}
