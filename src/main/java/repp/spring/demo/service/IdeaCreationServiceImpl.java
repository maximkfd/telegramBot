package repp.spring.demo.service;

import com.google.common.collect.ImmutableMap;
import org.assertj.core.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import repp.spring.demo.Application;
import repp.spring.demo.Bot;


@Service
public class IdeaCreationServiceImpl implements IdeaCreationService {

    private Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private
    Bot bot;

    public int createIdea(final String text) {
        Preconditions.checkNotNull(text);


        int result = 0;
        try {
            result = jdbcTemplate.update("INSERT INTO ideas(text) VALUES (:text)", ImmutableMap.of("text", text));
        } catch (DataAccessException e) {
            log.error("Error inserting new idea", e);
            Thread.currentThread().interrupt();
        }
        if (result > 0) {
            notifySubscribers(text);
        }

        return result;
    }

    private void notifySubscribers(String text) {
        bot.notifySubscribers(text);
    }
}
