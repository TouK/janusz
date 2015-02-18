package pl.touk.chat.bot.janusz.store;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.h2.jdbcx.JdbcConnectionPool.create;

public class JdbcStoreTest {

    private JdbcStore jdbcStore;

    @Before
    public void setup() {
        JdbcConnectionPool ds = create("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql';DB_CLOSE_ON_EXIT=FALSE", "sa", "");
        jdbcStore = new JdbcStore(ds);
    }

    @Test
    public void shouldStoreAndRetrieveValue() {
        jdbcStore.put("user", "key", "value");
        jdbcStore.put("user", "int", 1);

        assertThat(jdbcStore.get("user", "key", String.class)).isEqualTo("value");
        assertThat(jdbcStore.get("user", "int", Integer.class)).isEqualTo(1);
    }

    @Test
    public void shouldUpdateValue() {
        jdbcStore.put("user", "key", "value");
        jdbcStore.put("user", "key", "value2");

        assertThat(jdbcStore.get("user", "key", String.class)).isEqualTo("value2");
    }

}
