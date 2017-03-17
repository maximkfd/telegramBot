package repp.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import repp.spring.demo.model.Subscriber;
import repp.spring.demo.service.SubscriptionService;

public class Bot extends TelegramLongPollingBot {

    @Autowired
    SubscriptionService subscriptionService;

    private enum Command {
        SUBSCRIBE,
        UNSUBSCRIBE;

        public static Command of(final String text) {
            return valueOf(text.substring(1).toUpperCase());
        }
    }

    @Override
    public String getBotToken() {
        return "311584248:AAHDQcrpoDsX2AUt31J5PZhOxAv1Zf3cFMo";
//        return "360154389:AAH3xXsvMbb_AOOqovHl8qDJE5TVCI0ffjA";
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
        for (Subscriber i :
                subscriptionService.getSubscribers()) {
            newMessage(i.getId(), text);
        }
    }

    private void newMessage(Long id, String text) {
        SendMessage message = new SendMessage()
            .setText(text)
            .setChatId(id);
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
