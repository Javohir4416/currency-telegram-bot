package javokhir.dev.currencytelegrambot.repo;

import javokhir.dev.currencytelegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
