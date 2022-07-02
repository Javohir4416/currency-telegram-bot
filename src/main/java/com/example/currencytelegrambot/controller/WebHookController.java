package com.example.currencytelegrambot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/telegram")
public class WebHookController {
    @Value("${telegram.api}")
    private String  telegramAPI;

    @PostMapping
    public void getUpdates(@RequestBody Object object){
        System.out.println(object);
        System.out.println(telegramAPI);
    }
}
