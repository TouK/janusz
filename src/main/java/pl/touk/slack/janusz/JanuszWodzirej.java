package pl.touk.slack.janusz;

import com.ullink.slack.simpleslackapi.SlackMessage;

import java.util.ArrayList;
import java.util.List;

public class JanuszWodzirej {
    public boolean canHandle(SlackMessage slackMessage) {
        if (hasJanuszWord(slackMessage)) {
            return true;
        }

        if (isCreatorTeamMember(slackMessage)) {
            return true;
        }

        return false;
    }

    private boolean isCreatorTeamMember(SlackMessage slackMessage) {
        List<String> teamMembers = new ArrayList<String>(){{
            add("jagiel");
            add("ldr");
            add("cdr");
        }};
        return teamMembers.contains(slackMessage.getSender().getUserName());
    }

    private boolean hasJanuszWord(SlackMessage slackMessage) {
        return slackMessage.getMessageContent().toLowerCase().indexOf("janusz") != -1;
    }

    public String talk(SlackMessage slackMessage) {
        if (hasJanuszWord(slackMessage)) {
            return "Coś chcieliście ode mnie?";
        }

        if (isCreatorTeamMember(slackMessage) && Math.random()>0.9) {
            return "Dobrze prawisz...";
        }

        return null;
    }
}
