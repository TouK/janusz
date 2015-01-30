package pl.touk.slack.janusz.commands;

import java.util.List;

public interface JanuszCommand {

    String invoke(List<String> words);
}
