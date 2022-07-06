package javokhir.dev.currencytelegrambot.feign;

import javokhir.dev.currencytelegrambot.constants.RestConstants;
import javokhir.dev.currencytelegrambot.payload.ResultTelegram;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FeignClient(url = RestConstants.TELEGRAM_BASE_URL,name = "TelegramFeign")
public interface TelegramFeign {
    @PostMapping("/bot5448676746:AAHOahsQhEZcOGvkRTP5N73mNQ5fZPN2rac/sendMessage")
    ResultTelegram sendMessageToUser(@RequestBody SendMessage sendMessage);
//    @PostMapping("/bot5076755668:AAG3FwT8xgnwD3LiEHMPzdGzkNtZhSoh_QQ/deleteMessage")
//    void deleteMessage(@RequestParam String chat_id, @RequestParam int message_id);

//    @PostMapping("/bot5076755668:AAG3FwT8xgnwD3LiEHMPzdGzkNtZhSoh_QQ/sendPhoto")


}
