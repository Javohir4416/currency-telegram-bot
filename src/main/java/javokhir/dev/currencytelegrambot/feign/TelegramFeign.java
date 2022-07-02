package javokhir.dev.currencytelegrambot.feign;

import javokhir.dev.currencytelegrambot.constants.RestConstants;
import javokhir.dev.currencytelegrambot.payload.ResultTelegram;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FeignClient(url = RestConstants.TELEGRAM_BASE_URL,name = "TelegramFeign")
public interface TelegramFeign {
    @PostMapping("/bot5076755668:AAFRHhP9_PoPPAl6Q-f4H4ZM6Bz6H7ppCUo/sendMessage")
    ResultTelegram sendMessageToUser(@RequestBody SendMessage sendMessage);
}
