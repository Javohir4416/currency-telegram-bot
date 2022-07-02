package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.entity.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {
    public User getUserFromUpdate(Update update){
        org.telegram.telegrambots.meta.api.objects.User fromUser = update.getMessage().getFrom();
        return new User(fromUser.getId(), fromUser.getFirstName(), fromUser.getLastName(), fromUser.getUserName());
    }
}
