package pl.touk.chat.bot.janusz;

import javax.annotation.Resource;

public class JanuszCommander {

    @Resource
    private CommandInvoker commandInvoker;

    public void processCommand(String message, Responder responder) {
        if (commandInvoker.isCommandPrefix(message)) {
            String messageWithoutCommandPrefix = message.substring(1);
            responder.response(commandInvoker.invoke(messageWithoutCommandPrefix));
        }
    }

}
