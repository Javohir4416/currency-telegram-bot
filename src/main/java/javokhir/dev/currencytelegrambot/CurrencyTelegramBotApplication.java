package javokhir.dev.currencytelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyTelegramBotApplication.class, args);
    }

}
