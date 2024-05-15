package ru.sber.edu.yetanotherchat.exception;

import ru.sber.edu.yetanotherchat.entity.Chat;

public class ChatNoExist extends RuntimeException {
    public ChatNoExist(String message, Chat chat) {
        super(message + " " + chat.getId());
    }
}
