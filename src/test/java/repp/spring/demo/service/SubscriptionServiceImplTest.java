package repp.spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import repp.spring.demo.Application;
import repp.spring.demo.model.Subscriber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = Application.class)
public class SubscriptionServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    public void testGetSubscribers() throws Exception {
        final List<Subscriber> subscribers = subscriptionService.getSubscribers();
        assertThat(subscribers).as("excepted subscriber").isNotNull();
    }

    @Test
    public void testAddSubscriber() throws Exception {
        long id = 1L;
        String ololo = "OLOLO";
        final Subscriber subs = new Subscriber(id, ololo);
        final int i = subscriptionService.addSubscriber(subs);
        assertThat(i).isGreaterThan(0);

        final List<Subscriber> s = jdbcTemplate.query("SELECT ID, NAME FROM SUBSCRIBERS", new RowMapper<Subscriber>() {
            public Subscriber mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return new Subscriber(resultSet.getLong(1), resultSet.getString(2));
            }
        });
        assertThat(s.size()).isGreaterThan(0);
        assertThat(s.get(0).getName()).isEqualTo(ololo);

    }

    @Test
    public void testUnsubscribe() throws Exception {

        final Subscriber subs = new Subscriber(1L, "OLOLO");
        final int i = subscriptionService.addSubscriber(subs);
        assertThat(i).isGreaterThan(0);

        final int unsubscribe = subscriptionService.unsubscribe(1L);
        assertThat(unsubscribe).isGreaterThan(0);

    }
}