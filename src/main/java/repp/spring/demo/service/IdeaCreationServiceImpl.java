package repp.spring.demo.service;

import com.google.common.collect.ImmutableMap;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import repp.spring.demo.Bot;

@Service
public class IdeaCreationServiceImpl implements IdeaCreationService {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    Bot bot;

    public int createIdea(final String text) {
        Preconditions.checkNotNull(text);

        notifySubscribers(text);

        int result = 0;
        try {
            result = jdbcTemplate.update("INSERT INTO ideas(text) VALUES (:text)", ImmutableMap.of("text", text));
        } catch (DataAccessException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        return result;
    }

    private void notifySubscribers(String text) {
        bot.notifySubscribers(text);
    }
}
