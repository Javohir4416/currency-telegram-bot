package javokhir.dev.currencytelegrambot.service;

import javokhir.dev.currencytelegrambot.feign.TelegramFeign;
import javokhir.dev.currencytelegrambot.payload.ResultTelegram;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class WebHookService {

    @Autowired
    TelegramFeign telegramFeign;
     public void whenStart(Update update){
          SendMessage sendMessage=new SendMessage();
          sendMessage.setChatId(update.getMessage().getChatId());
          sendMessage.setText("Hello");
          ResultTelegram resultTelegram = telegramFeign.sendMessageToUser(sendMessage.getChatId(),sendMessage.getText());
          System.out.println(resultTelegram);
     }
}
