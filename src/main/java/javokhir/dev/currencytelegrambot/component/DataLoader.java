package javokhir.dev.currencytelegrambot.component;

import javokhir.dev.currencytelegrambot.entity.SmsForToken;
import javokhir.dev.currencytelegrambot.entity.UserState;
import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import javokhir.dev.currencytelegrambot.repo.SmsForApiRepo;
import javokhir.dev.currencytelegrambot.repo.UserRepo;
import javokhir.dev.currencytelegrambot.repo.UserStateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepo userRepo;
    private final UserStateRepo stateRepo;
    private final SmsForApiRepo smsForApiRepo;
    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            List<UserState> userStates=new ArrayList<>();
            UserStateNames[] values = UserStateNames.values();
            for (UserStateNames value : values) {
                userStates.add(new UserState(value));
            }
            stateRepo.saveAll(userStates);
            smsForApiRepo.save(new SmsForToken(1L,"test@eskiz.uz","j6DWtQjjpLDNjWEk74Sx",
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUsInJvbGUiOiJ1c2VyIiwiZGF0YSI6eyJpZCI6NSwibmFtZSI6Ilx1MDQyN1x1MDQxZiBCZXN0IEludGVybmV0IFNvbHV0aW9uIiwiZW1haWwiOiJ0ZXN0QGVza2l6LnV6Iiwicm9sZSI6InVzZXIiLCJhcGlfdG9rZW4iOm51bGwsInN0YXR1cyI6ImFjdGl2ZSIsInNtc19hcGlfbG9naW4iOiJlc2tpejIiLCJzbXNfYXBpX3Bhc3N3b3JkIjoiZSQkayF6IiwidXpfcHJpY2UiOjUwLCJiYWxhbmNlIjo4MDAsImlzX3ZpcCI6MCwiaG9zdCI6InNlcnZlcjEiLCJjcmVhdGVkX2F0IjpudWxsLCJ1cGRhdGVkX2F0IjoiMjAyMi0wNi0yMVQyMToyMDoxOC4wMDAwMDBaIn0sImlhdCI6MTY1NTg3NTAzMCwiZXhwIjoxNjU4NDY3MDMwfQ.C188ZlXSDlVXBIazy4UlmQfCqUcM8S77VP94xeTR9Ps",
                    "https://notify.eskiz.uz/api/auth/login"));
        }
    }
}
