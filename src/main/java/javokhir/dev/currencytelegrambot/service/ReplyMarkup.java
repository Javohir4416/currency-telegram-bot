package javokhir.dev.currencytelegrambot.service;
import javokhir.dev.currencytelegrambot.entity.User;
import javokhir.dev.currencytelegrambot.feign.TelegramFeign;
import javokhir.dev.currencytelegrambot.payload.ResultTelegram;
import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import javokhir.dev.currencytelegrambot.repo.UserStateRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyMarkup {

    @Autowired
    UserStateRepo userStateRepo;

    @Autowired
    TelegramFeign telegramFeign;
    public ReplyKeyboardMarkup markup(User user) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton row1Button1 = new KeyboardButton();
        if (user.getState().equals(userStateRepo.findByUserState(UserStateNames.START))) {
            row1Button1.setText("Raqamingizni jo'nating  ☎");
            row1Button1.setRequestContact(true);
            row1.add(row1Button1);
            rowList.add(row1);
        } else if (user.getState().equals(userStateRepo.findByUserState(UserStateNames.REENTER_CODE))) {
            row1Button1.setText("Qayta kod olish");
            row1.add(row1Button1);
            rowList.add(row1);
        }
        markup.setKeyboard(rowList);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }

    public InlineKeyboardMarkup inlineMarkup(User user) {
        InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton row1Button1 = new InlineKeyboardButton();
        if (user.getState().equals(userStateRepo.findByUserState(UserStateNames.THROW_TO_ADMIN_CABINET))) {
            row1Button1.setText("Bot foydalanuvchilariga xabar jo'natish ✍️ ");
            row1Button1.setCallbackData("XABAR");
            row1.add(row1Button1);
            rowList.add(row1);
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton row2Button1 = new InlineKeyboardButton();
            row2Button1.setText("Bot foydalanuvchilariga reklama jo'natish ");
            row2Button1.setCallbackData("REKLAMA");
            row2.add(row2Button1);
            rowList.add(row2);
        } else if (user.getState().equals(userStateRepo.findByUserState(UserStateNames.SHOW_MENU))) {
            row1Button1.setText("Jahon valyuta kurslarini " +
                    "o'zbek so'midagi qiymatini bilish ");
            row1Button1.setCallbackData("INFORMATION");
            row1.add(row1Button1);
            rowList.add(row1);
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton row2Button1 = new InlineKeyboardButton();
            row2Button1.setText("Valyutalarni konvertatsiya qilish");
            row2Button1.setCallbackData("CONVERTOR");
            row2.add(row2Button1);
            rowList.add(row2);
        }
        else if (user.getState().equals(userStateRepo.findByUserState(UserStateNames.GET_INFORMATION))){
            row1Button1.setText("\uD83C\uDDFA\uD83C\uDDF8USD\uD83C\uDDFA\uD83C\uDDF8");
            row1Button1.setCallbackData("USD");
            InlineKeyboardButton row1Button2;
            row1Button2 = new InlineKeyboardButton();
            row1Button2.setText("\uD83C\uDDF7\uD83C\uDDFARUB\uD83C\uDDF7\uD83C\uDDFA");
            row1Button2.setCallbackData("RUB");
            InlineKeyboardButton row1Button3=new InlineKeyboardButton();
            row1Button3.setText("\uD83C\uDDEA\uD83C\uDDFAEUR\uD83C\uDDEA\uD83C\uDDFA");
            row1Button3.setCallbackData("EUR");
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton row2Button1 = new InlineKeyboardButton();
            row2Button1.setText("Qolgan valyutalarni bilish");
            row2Button1.setCallbackData("OTHERS");
            List<InlineKeyboardButton> row3 = new ArrayList<>();
            InlineKeyboardButton row3Button1 = new InlineKeyboardButton();
            row3Button1.setText("Back");
            row3Button1.setCallbackData("BACK");
            row1.add(row1Button1);
            row1.add(row1Button2);
            row1.add(row1Button3);
            rowList.add(row1);
            row2.add(row2Button1);
            rowList.add(row2);
            row3.add(row3Button1);
            rowList.add(row3);
        }
        inlineMarkup.setKeyboard(rowList);
        return inlineMarkup;
    }
//    @SneakyThrows
//    public void deleteMessage(User user) {
//        SendMessage sendMessageRemove = new SendMessage();
//        sendMessageRemove.setChatId(user.getId().toString());
//        sendMessageRemove.setText("ㅤ");
//        sendMessageRemove.setReplyMarkup(new ReplyKeyboardRemove(true));
//        ResultTelegram resultTelegram = telegramFeign.sendMessageToUser(sendMessageRemove);
//        DeleteMessage deleteMessage = new DeleteMessage(user.getId().toString(),resultTelegram.getResult().getMessageId());
//        telegramFeign.deleteMessage(deleteMessage.getChatId(),deleteMessage.getMessageId());
//    }
}
