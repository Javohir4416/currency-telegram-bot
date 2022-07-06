package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.constants.RestConstants;
import javokhir.dev.currencytelegrambot.entity.User;
import javokhir.dev.currencytelegrambot.feign.TelegramFeign;
import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import javokhir.dev.currencytelegrambot.repo.UserRepo;
import javokhir.dev.currencytelegrambot.repo.UserStateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserStateRepo userStateRepo;
    @Autowired
    TelegramFeign telegramFeign;

    @Autowired
    ReplyMarkup replyMarkup;
    @Autowired
    UserService userService;

    public void throwToAdminCabinet(Update update){
        User userFromUpdate = userService.getUserFromUpdate(update);
        userFromUpdate.setState(userStateRepo.findByUserState(UserStateNames.THROW_TO_ADMIN_CABINET));
        User save = userRepo.save(userFromUpdate);
        if(update.getMessage().getText().equals(RestConstants.PASSWORD)) {
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Salom admin !!! ");
            sendMessage.setReplyMarkup(replyMarkup.inlineMarkup(save));
            telegramFeign.sendMessageToUser(sendMessage);
        }
        else {
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Parol xato ");
            sendMessage.setReplyMarkup(replyMarkup.inlineMarkup(save));
            telegramFeign.sendMessageToUser(sendMessage);
        }
    }

    public void sendMessageToUsers(String text) {
        List<User> userRepoAll = userRepo.findAll();
        for (User user : userRepoAll) {
            SendMessage sendMessage=new SendMessage(user.getId().toString(),text);
            telegramFeign.sendMessageToUser(sendMessage);
        }
    }

    public void sendMessageToAdmin(Update update) {
        User userFromUpdate = userService.getUserFromUpdate(update);
        userFromUpdate.setState(userStateRepo.findByUserState(UserStateNames.SEND_MESSAGE_TO_USERS));
        User save = userRepo.save(userFromUpdate);
        SendMessage sendMessage = new SendMessage(save.getId().toString(),
                "O'z xabaringizni kiriting ✍️: ");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void checkForAdmin(Update update) {
        User userFromUpdate = userService.getUserFromUpdate(update);
        userFromUpdate.setState(userStateRepo.findByUserState(UserStateNames.ENTER_PASSWORD_FOR_ADMIN));
        User save = userRepo.save(userFromUpdate);
        SendMessage sendMessage = new SendMessage(save.getId().toString(),
                "Parolni kiriting ✍️: ");
        telegramFeign.sendMessageToUser(sendMessage);
    }
}
