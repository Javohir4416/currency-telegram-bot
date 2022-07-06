package javokhir.dev.currencytelegrambot.entity;

import javokhir.dev.currencytelegrambot.payload.enums.UserStateNames;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserState {
    @Id
    @GeneratedValue
    private Long state_id;

    private UserStateNames userState;
    public UserState(UserStateNames userState) {
        this.userState = userState;
    }
}
