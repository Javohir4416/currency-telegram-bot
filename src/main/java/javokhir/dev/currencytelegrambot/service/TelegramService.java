package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.entity.User;
import javokhir.dev.currencytelegrambot.feign.TelegramFeign;
import javokhir.dev.currencytelegrambot.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class TelegramService {
    @Autowired
   private WebHookService webHookService;
    @Autowired
   private  UserRepo userRepo;
    @Autowired
   private  UserService userService;



   public void getUpdates(Update update){
       if (update.hasMessage()){
           String text = update.getMessage().getText();
           switch (text) {
               case "/start":
                   userRepo.save(userService.getUserFromUpdate(update));
                   webHookService.whenStart(update);
                   break;
           }
       }
   }
}
