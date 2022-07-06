package javokhir.dev.currencytelegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SmsForToken {
    @Id
    private Long id;

    @NotNull(message = "email bo`sh bo`lishi mumkin emas")
    private String email="test@eskiz.uz";

    @NotNull(message = "parol bo`sh bo`lishi mumkin emas")
    private String password="j6DWtQjjpLDNjWEk74Sx";

    @Type(type = "text")
    private String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUsInJvbGUiOiJ1c2VyIiwiZGF0YSI6eyJpZCI6NSwibmFtZSI6Ilx1MDQyN1x1MDQxZiBCZXN0IEludGVybmV0IFNvbHV0aW9uIiwiZW1haWwiOiJ0ZXN0QGVza2l6LnV6Iiwicm9sZSI6InVzZXIiLCJhcGlfdG9rZW4iOm51bGwsInN0YXR1cyI6ImFjdGl2ZSIsInNtc19hcGlfbG9naW4iOiJlc2tpejIiLCJzbXNfYXBpX3Bhc3N3b3JkIjoiZSQkayF6IiwidXpfcHJpY2UiOjUwLCJiYWxhbmNlIjo4MDAsImlzX3ZpcCI6MCwiaG9zdCI6InNlcnZlcjEiLCJjcmVhdGVkX2F0IjpudWxsLCJ1cGRhdGVkX2F0IjoiMjAyMi0wNi0yMVQyMToyMDoxOC4wMDAwMDBaIn0sImlhdCI6MTY1NTg3NTAzMCwiZXhwIjoxNjU4NDY3MDMwfQ.C188ZlXSDlVXBIazy4UlmQfCqUcM8S77VP94xeTR9Ps";

    private String url="https://notify.eskiz.uz/api/auth/login";

    public SmsForToken(String email, String password, String token, String url) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.url = url;
    }
}
