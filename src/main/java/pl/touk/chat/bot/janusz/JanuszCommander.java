package pl.touk.chat.bot.janusz;

import javax.annotation.Resource;

public class JanuszCommander {

    @Resource
    private CommandInvoker commandInvoker;

    public void processCommand(String message, String sender, Responder responder) {
        if (commandInvoker.isCommand(message)) {
            String messageWithoutCommandPrefix = message.substring(1);
            responder.respond(commandInvoker.invoke(sender, messageWithoutCommandPrefix));
        }
    }

}
