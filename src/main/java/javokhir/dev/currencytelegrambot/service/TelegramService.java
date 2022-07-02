package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.entity.User;
import javokhir.dev.currencytelegrambot.feign.TelegramFeign;
import javokhir.dev.currencytelegrambot.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramService {
   private final WebHookService webHookService;
   private final UserRepo userRepo;
   private final UserService userService;



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
