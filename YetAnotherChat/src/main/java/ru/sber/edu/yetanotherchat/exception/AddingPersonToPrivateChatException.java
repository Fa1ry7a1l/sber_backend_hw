package ru.sber.edu.yetanotherchat.exception;

import ru.sber.edu.yetanotherchat.entity.Chat;
import ru.sber.edu.yetanotherchat.entity.User;

public class AddingPersonToPrivateChatException extends RuntimeException {

    public AddingPersonToPrivateChatException(User user, Chat chat) {
        super(String.format("trying to add a user %s to a private chat %s", user.getLogin(), chat.getName()));
    }
}
