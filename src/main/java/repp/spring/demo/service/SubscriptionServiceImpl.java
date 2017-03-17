package repp.spring.demo.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import repp.spring.demo.model.Subscriber;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Inject
    NamedParameterJdbcTemplate jdbcTemplate;

    public int addSubscriber(final Subscriber subscriber) {
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(subscriber.getId());

        return jdbcTemplate.update("INSERT INTO subscribers  VALUES (:id, :name)", new BeanPropertySqlParameterSource(subscriber));
    }

    public int unsubscribe(final Long chatId) {
        return jdbcTemplate.update("DELETE FROM subscribers WHERE id = :id", ImmutableMap.of("id", chatId));
    }

    public List<Subscriber> getSubscribers() {
        return jdbcTemplate.query("SELECT ID, NAME FROM SUBSCRIBERS", new RowMapper<Subscriber>() {
            public Subscriber mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return new Subscriber(resultSet.getLong(1), resultSet.getString(2));
            }
        });
    }
}
