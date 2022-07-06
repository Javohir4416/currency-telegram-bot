package javokhir.dev.currencytelegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String otp;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public User(String firstName, String lastName, String username, String phoneNumber, UserState state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.state = state;
    }

    public User(Long id, String firstName, String lastName, String username, UserState state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.state = state;
    }

    @OneToOne
    private UserState state;
}
