package javokhir.dev.currencytelegrambot.repo;


import javokhir.dev.currencytelegrambot.entity.UserState;
import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStateRepo extends JpaRepository<UserState,Long> {
    UserState findByUserState(UserStateNames userState);
}
