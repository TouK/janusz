package pl.touk.chat.bot.janusz;

import com.google.common.collect.ImmutableMap;
import com.truward.di.InjectionContext;
import com.truward.di.support.DefaultInjectionContext;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.touk.chat.bot.janusz.commands.Commands;
import pl.touk.chat.bot.janusz.commands.bus.BusCommand;
import pl.touk.chat.bot.janusz.commands.bus.TransitApi;
import pl.touk.chat.bot.janusz.commands.gif.GifCommand;
import pl.touk.chat.bot.janusz.commands.stack.StackOverflowCommand;
import pl.touk.chat.bot.janusz.commands.store.StoreCommand;
import pl.touk.chat.bot.janusz.config.ConfigLoader;
import pl.touk.chat.bot.janusz.config.JanuszConfiguration;
import pl.touk.chat.bot.janusz.hipchat.HippyJanusz;
import pl.touk.chat.bot.janusz.slack.SlackJanusz;
import pl.touk.chat.bot.janusz.store.JdbcStore;

import java.io.IOException;
import java.util.List;

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
        context.registerBean(connectionPool(januszConfiguration));
        context.registerBean(JdbcStore.class);
        context.registerBean(TransitApi.class);
        context.registerBean(BusCommand.class);
        context.registerBean(StoreCommand.class);
        context.registerBean(StackOverflowCommand.class);
        context.registerBean(GifCommand.class);
        context.registerBean(createCommands(context));

        context.registerBean(CommandInvoker.class);
        context.registerBean(JanuszCommander.class);
        context.registerBean(SlackJanusz.class);
        context.registerBean(HippyJanusz.class);

        context.freeze();
        return context;
    }

    private JdbcConnectionPool connectionPool(JanuszConfiguration configuration) {
        return JdbcConnectionPool.create(configuration.jdbc.url, configuration.jdbc.username, configuration.jdbc.password);
    }

    private Commands createCommands(InjectionContext context) {
        return new Commands(ImmutableMap.of(
                "bus", context.getBean(BusCommand.class),
                "stack", context.getBean(StackOverflowCommand.class),
                "gif", context.getBean(GifCommand.class),
                "store", context.getBean(StoreCommand.class)));
    }
}
