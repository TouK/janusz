package pl.touk.chat.bot.janusz;

import com.google.common.collect.ImmutableList;
import com.ullink.slack.simpleslackapi.SlackMessage;

import java.util.List;

public class JanuszWodzirej {

    public boolean canHandle(SlackMessage slackMessage) {
        return containsMagicWord(slackMessage) || isCreatorTeamMember(slackMessage);
    }

    private boolean isCreatorTeamMember(SlackMessage slackMessage) {
        List<String> teamMembers = ImmutableList.of("jagiel", "ldr", "cdr");
        return teamMembers.contains(slackMessage.getSender().getUserName());
    }

    private boolean containsMagicWord(SlackMessage slackMessage) {
        return slackMessage.getMessageContent().toLowerCase().contains("janusz");
    }

    public String talk(SlackMessage slackMessage) {
        if (containsMagicWord(slackMessage)) {
            return "Coś chcieliście ode mnie?";
        }

        if (isCreatorTeamMember(slackMessage) && Math.random()>0.9) {
            return "Dobrze prawisz...";
        }

        return null;
    }
}
