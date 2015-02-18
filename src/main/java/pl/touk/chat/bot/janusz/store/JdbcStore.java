package pl.touk.chat.bot.janusz.store;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class JdbcStore implements Store {

    private final DSLContext create;
    private final Table<Record> STORE = table("STORE");
    private final Field<Object> USER = field("USER");
    private final Field<Object> KEY = field("KEY");
    private final Field<Object> VALUE = field("VALUE");

    public JdbcStore(DataSource dataSource) {
        this.create = DSL.using(dataSource, SQLDialect.H2);
    }

    @Override
    public <T> void put(String user, String key, T value) {
        if (exists(user, key)) {
            create.update(STORE)
                    .set(VALUE, value)
                    .where(USER.equal(user)
                        .and(KEY.equal(key)))
                    .execute();
        } else {
            create.insertInto(STORE)
                    .set(USER, user)
                    .set(KEY, key)
                    .set(VALUE, value).execute();
        }
    }

    @Override
    public <T> T get(String user, String key, Class<T> clazz) {
        return create.select()
                .from(STORE)
                .where(USER.equal(user)
                        .and(KEY.equal(key)))
                .fetchOne(VALUE, clazz);
    }

    private boolean exists(String user, String key) {
        return get(user, key, String.class) != null;
    }
}
