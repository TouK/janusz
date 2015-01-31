package pl.touk.chat.bot.janusz;

import com.truward.di.InjectionContext;
import com.truward.di.support.DefaultInjectionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.touk.chat.bot.janusz.commands.JanuszCommand;
import pl.touk.chat.bot.janusz.commands.bus.BusCommand;
import pl.touk.chat.bot.janusz.commands.bus.TransitApi;
import pl.touk.chat.bot.janusz.commands.gif.GifCommand;
import pl.touk.chat.bot.janusz.commands.stack.StackOverflowCommand;
import pl.touk.chat.bot.janusz.config.ConfigLoader;
import pl.touk.chat.bot.janusz.config.JanuszConfiguration;
import pl.touk.chat.bot.janusz.hipchat.HippyJanusz;
import pl.touk.chat.bot.janusz.slack.SlackJanusz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanuszStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JanuszStarter.class);
    private InjectionContext context;
    private List<JanuszListener> listeners;

    public static void main(String [] args) throws InterruptedException, IOException {
        JanuszConfiguration januszConfiguration = ConfigLoader.load(args);
        JanuszStarter januszStarter = new JanuszStarter(januszConfiguration);
        januszStarter.startListening();

        while (true) {
            Thread.sleep(1000);
        }
    }

    public JanuszStarter(JanuszConfiguration januszConfiguration) {
        context = createContext(januszConfiguration);
        listeners = context.getBeans(JanuszListener.class);
    }

    public void startListening() {
        LOGGER.info("Starting listeners");
        listeners.forEach(januszListener -> {
            if (januszListener.isEnabled()) januszListener.listen();
        });
        LOGGER.info("Finished starting listeners");
    }

    public InjectionContext createContext(JanuszConfiguration januszConfiguration) {
        InjectionContext context = new DefaultInjectionContext();
        context.registerBean(januszConfiguration);
        context.registerBean(new TransitApi());
        context.registerBean(BusCommand.class);
        context.registerBean(new StackOverflowCommand());
        context.registerBean(new GifCommand(januszConfiguration.giphy.apiToken));

        context.registerBean(new CommandInvoker(createCommands(context)));
        context.registerBean(new JanuszCommander());
        context.registerBean(new SlackJanusz());
        context.registerBean(new HippyJanusz());

        context.freeze();
        return context;
    }

    private Map<String, JanuszCommand> createCommands(InjectionContext context) {
        return new HashMap<String, JanuszCommand>() {{
            put("bus", context.getBean(BusCommand.class));
            put("stack", context.getBean(StackOverflowCommand.class));
            put("gif", context.getBean(GifCommand.class));
        }};
    }
}
