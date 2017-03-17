package repp.spring.demo.service;

import repp.spring.demo.model.Subscriber;

import java.util.List;

public interface SubscriptionService {
    int addSubscriber(final Subscriber subscriber);

    int unsubscribe(Long chatId);

    List<Subscriber> getSubscribers();
}
