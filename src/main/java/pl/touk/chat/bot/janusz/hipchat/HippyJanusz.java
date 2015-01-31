package pl.touk.chat.bot.janusz.hipchat;

import com.ep.hippyjava.HippyJava;
import com.ep.hippyjava.bot.HippyBot;
import com.ep.hippyjava.model.Room;
import pl.touk.chat.bot.janusz.JanuszCommander;
import pl.touk.chat.bot.janusz.JanuszListener;
import pl.touk.chat.bot.janusz.config.JanuszConfiguration;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HippyJanusz extends HippyBot implements JanuszListener {

    @Resource
    private JanuszConfiguration januszConfiguration;

    @Resource
    private JanuszCommander januszCommander;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void listen() {
        //runBot() is blocking
        executorService.execute(() -> HippyJava.runBot(HippyJanusz.this));
    }

    @Override
    public void receiveMessage(String message, String from, Room room) {
        januszCommander.processCommand(message, result -> room.sendMessage(result, nickname()));
    }

    @Override
    public String apiKey() {
        return januszConfiguration.hipchat.apiToken;
    }

    @Override
    public String username() {
        return januszConfiguration.hipchat.jid;
    }

    @Override
    public String nickname() {
        return januszConfiguration.nickname;
    }

    @Override
    public String password() {
        return januszConfiguration.hipchat.password;
    }

    @Override
    public boolean isEnabled() {
        return januszConfiguration.hipchat.enabled;
    }

    @Override
    public void onLoad() {

    }
}
