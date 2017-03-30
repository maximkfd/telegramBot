package repp.spring.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import repp.spring.demo.model.Subscriber;
import repp.spring.demo.service.SubscriptionService;


public class Bot extends TelegramLongPollingBot {

    private Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private SubscriptionService subscriptionService;

    @Value("${bot.token}")
    private String botToken;// = "311584248:AAHDQcrpoDsX2AUt31J5PZhOxAv1Zf3cFMo";



    private enum Command {
        SUBSCRIBE,
        UNSUBSCRIBE;

        public static Command of(final String text) {
            return valueOf(text.substring(1).toUpperCase());
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void onUpdateReceived(final Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String text = update.getMessage().getText();
            switch (Command.of(text)) {
                case SUBSCRIBE:
                    subscriptionService.addSubscriber(new Subscriber(update.getMessage().getChatId(), update.getMessage().getChat().getUserName()));
                    break;
                case UNSUBSCRIBE:
                    subscriptionService.unsubscribe(update.getMessage().getChatId());
                    break;
            }
        }

    }

    public String getBotUsername() {
        return "Repp test Bot";
    }

    public void notifySubscribers(String text){
        for (Subscriber subscriber :
                subscriptionService.getSubscribers()) {
            sendToSubscriber(subscriber.getId(), text);
        }
    }

    private void sendToSubscriber(Long id, String text) {
        SendMessage message = new SendMessage()
            .setText(text)
            .setChatId(id);
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }
}
