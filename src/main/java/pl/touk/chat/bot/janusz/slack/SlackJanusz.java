package pl.touk.chat.bot.janusz.slack;

import com.ullink.slack.simpleslackapi.SlackMessage;
import com.ullink.slack.simpleslackapi.SlackMessageListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import pl.touk.chat.bot.janusz.JanuszCommander;
import pl.touk.chat.bot.janusz.JanuszListener;
import pl.touk.chat.bot.janusz.config.JanuszConfiguration;

import javax.annotation.Resource;

public class SlackJanusz implements JanuszListener {

    @Resource
    private JanuszConfiguration januszConfiguration;

    @Resource
    private JanuszCommander janusz;

    private SlackSession session;

    @Override
    public void listen() {
        session = SlackSessionFactory.createWebSocketSlackSession(januszConfiguration.slack.apiToken);

        session.addMessageListener(new SlackMessageListener() {
            @Override
            public void onSessionLoad(SlackSession slackSession) {
            }

            @Override
            public void onMessage(SlackMessage slackMessage) {
                janusz.processCommand(
                    slackMessage.getMessageContent(),
                    slackMessage.getSender().getUserName(),
                    result -> session.sendMessageOverWebSocket(slackMessage.getChannel(), result, null)
                );
            }
        });

        session.connect();
    }

    @Override
    public boolean isEnabled() {
        return januszConfiguration.slack.enabled;
    }

}
