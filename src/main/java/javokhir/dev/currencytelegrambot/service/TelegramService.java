package javokhir.dev.currencytelegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramService {
   private final WebHookService webHookService;



   public void getUpdates(Update update){
       if (update.hasMessage()){
           String text = update.getMessage().getText();
           switch (text) {
               case "/start":
                   webHookService.whenStart(update);
                   break;
           }
       }
   }
}
