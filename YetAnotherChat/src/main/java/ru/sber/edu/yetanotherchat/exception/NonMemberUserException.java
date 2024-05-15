package ru.sber.edu.yetanotherchat.exception;

import ru.sber.edu.yetanotherchat.entity.Chat;
import ru.sber.edu.yetanotherchat.entity.User;

public class NonMemberUserException extends RuntimeException {
    public NonMemberUserException(User user, Chat chat) {
        super(String.format("user %s is not a member of %s", user.getLogin(), chat.getName()));
    }
}
