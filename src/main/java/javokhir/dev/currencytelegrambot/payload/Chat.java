package javokhir.dev.currencytelegrambot.payload;

import lombok.Data;

@Data
public class Chat{
	private int id;
	private String type;
	private String firstName;
	private String username;
}