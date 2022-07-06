package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.entity.User;
import javokhir.dev.currencytelegrambot.feign.TelegramFeign;
import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import javokhir.dev.currencytelegrambot.repo.UserRepo;
import javokhir.dev.currencytelegrambot.repo.UserStateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserStateRepo userStateRepo;
    @Autowired
    SmsService smsService;
    @Autowired
    UserRepo userRepo;

    @Autowired
    ReplyMarkup replyMarkup;
    @Autowired
    TelegramFeign telegramFeign;

    public User getUserFromUpdate(Update update) {
        User userAvailable = null;
        if (update.hasMessage()) {
            org.telegram.telegrambots.meta.api.objects.User fromUpdateUser = update.getMessage().getFrom();
            boolean userIsAvailable = userRepo.findById(fromUpdateUser.getId()).isPresent();
            userAvailable = new User();
            if (userRepo.findById(fromUpdateUser.getId()).isPresent()) {
                userAvailable = userRepo.findById(fromUpdateUser.getId()).get();
            }
            if (update.getMessage().hasContact()) {
                if (userIsAvailable) {
                    userAvailable.setPhoneNumber(update.getMessage().getContact().getPhoneNumber());
                    Random rnd = new Random();
                    int number = rnd.nextInt(99999);
                    String code = String.format("%06d", number);
                    userAvailable.setPhoneNumber(userAvailable.getPhoneNumber().startsWith("+") ? userAvailable.getPhoneNumber() : "+" + userAvailable.getPhoneNumber());
                    userAvailable.setPhoneNumber(userAvailable.getPhoneNumber().replace('+', ' '));
                    userAvailable.setOtp(code);
                    return userRepo.save(userAvailable);
                }
            } else {
                if (update.getMessage().getText() != null && update.getMessage().getText().equals("/start")) {
                    User user = new User(fromUpdateUser.getId(),
                            fromUpdateUser.getFirstName(),
                            fromUpdateUser.getLastName(),
                            fromUpdateUser.getUserName(),
                            userStateRepo.findByUserState(UserStateNames.START));
                    return userRepo.save(user);
                } else {
                    if (userIsAvailable) {
                        return userAvailable;
                    } else {
                        User user = new User(fromUpdateUser.getId(),
                                fromUpdateUser.getFirstName(),
                                fromUpdateUser.getLastName(),
                                fromUpdateUser.getUserName(),
                                userStateRepo.findByUserState(UserStateNames.START));
                        return userRepo.save(user);
                    }
                }
            }
        }
        org.telegram.telegrambots.meta.api.objects.User fromUpdateUser = update.getCallbackQuery().getFrom();

        if (userRepo.findById(fromUpdateUser.getId()).isPresent()) {
            userAvailable = userRepo.findById(fromUpdateUser.getId()).get();
        }
        return userAvailable;
    }

    public void whenStart(User user){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setText("Telefon raqamingizni tasdiqlang : ");
        sendMessage.setReplyMarkup(replyMarkup.markup(user));
        user.setState(userStateRepo.findByUserState(UserStateNames.SHARE_NUMBER));
        telegramFeign.sendMessageToUser(sendMessage);
        userRepo.save(user);
    }

    public void sendMessageAboutSendCode(User user) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getId().toString());
        user.setState(userStateRepo.findByUserState(UserStateNames.ENTER_CODE));
        User save = userRepo.save(user);
//        smsService.sendMessageCode(save.getPhoneNumber(), user.getOtp());
        sendMessage.setText("Telefon raqamingizga jo'natilgan sms kodni kiriting : ");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void checkCode(User user, String text) {
//
        if(text.equals("1111")){
            SendMessage sendMessage=new SendMessage();
            sendMessage.setChatId(user.getId().toString());
            sendMessage.setText("Muvaffaqiyatli registratsiya qilindingiz . Xizmatlardan birini tanlang : ");
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            telegramFeign.sendMessageToUser(sendMessage);
        }
        else {
            SendMessage sendMessage=new SendMessage();
            sendMessage.setChatId(user.getId().toString());
            sendMessage.setText("Noto'g'ri kod . Kodni qayta oling : ");
            user.setState(userStateRepo.findByUserState(UserStateNames.REENTER_CODE));
            sendMessage.setReplyMarkup(replyMarkup.markup(userRepo.save(user)));
            telegramFeign.sendMessageToUser(sendMessage);
            user.setState(userStateRepo.findByUserState(UserStateNames.SHARE_NUMBER));
            Random rnd = new Random();
            int number = rnd.nextInt(99999);
            String code = String.format("%06d", number);
            user.setOtp(code);
            userRepo.save(user);
        }
    }
}
