package pl.touk.chat.bot.janusz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import pl.touk.chat.bot.janusz.JanuszException;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static JanuszConfiguration load(String [] args) throws IOException {
        File fileConfig = new File(args.length > 0 ? args[0] : "application.yaml");
        if (!fileConfig.exists()) {
            throw new JanuszException(
                new IllegalArgumentException(String.format("Config file %s does not exists in classpath", fileConfig.getName()))
            );
        }

        return new ObjectMapper(new YAMLFactory()).readValue(fileConfig, JanuszConfiguration.class);
    }
}
