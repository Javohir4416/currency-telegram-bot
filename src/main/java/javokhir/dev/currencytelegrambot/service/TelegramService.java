package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.entity.User;
import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import javokhir.dev.currencytelegrambot.repo.UserRepo;
import javokhir.dev.currencytelegrambot.repo.UserStateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class TelegramService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserStateRepo userStateRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private ReplyMarkup replyMarkup;


    public void getUpdates(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasContact()) {
                User userFromUpdate = userService.getUserFromUpdate(update);
                userService.sendMessageAboutSendCode(userFromUpdate);
            }


            else if (update.getMessage().hasLocation()) {
                User userFromUpdate = userService.getUserFromUpdate(update);

            }



            else {
                 User userFromUpdate = userService.getUserFromUpdate(update);
                String text = update.getMessage().getText();
                if(text!=null) {
                    switch (text) {
                        case "/start":
                            Optional<User> userOptional = userRepo.findById(userFromUpdate.getId());
                            if (userStateRepo.findByUserState(UserStateNames.START).equals(userFromUpdate.getState())) {
                                if (userOptional.isPresent()) {
                                    User user = userOptional.get();
                                    userService.whenStart(user);
                                }
                            }
                            break;
                        case "/admin":
                            adminService.checkForAdmin(userFromUpdate);
                            break;

                        default:
                            if (userFromUpdate.getState().equals(userStateRepo.findByUserState(UserStateNames.SEND_MESSAGE_TO_USERS))) {
                                adminService.sendMessageToUsers(text,userFromUpdate);
                            } else if (userFromUpdate.getState().equals(userStateRepo.findByUserState(UserStateNames.SHARE_NUMBER))) {
                                userService.sendMessageAboutSendCode(userFromUpdate);
                            } else if (userFromUpdate.getState().equals(userStateRepo.findByUserState(UserStateNames.ENTER_CODE))) {
                                userService.checkCode(userFromUpdate, text);
                            } else if (userFromUpdate.getState().equals(userStateRepo.findByUserState(UserStateNames.ENTER_PASSWORD_FOR_ADMIN))) {
                                adminService.throwToAdminCabinet(update);
                            }

                    }
                }
            }




        }


        else if (update.hasCallbackQuery()) {
            User userFromUpdate = userService.getUserFromUpdate(update);
            String data = update.getCallbackQuery().getData();
            if (data.equals("XABAR")) {
                adminService.sendMessageToAdmin(userFromUpdate);
            }
            else if (data.equals("INFORMATION")){
                userService.sendInformationToUser(userFromUpdate);
            }
            else if (userFromUpdate.getState().equals(userStateRepo.findByUserState(UserStateNames.GET_INFORMATION))){
                userService.getInformation(update);
            }
        }
    }
}
