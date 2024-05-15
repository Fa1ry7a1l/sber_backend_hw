package ru.sber.edu.yetanotherchat.exception;

public class PrivateChatAlreadyExistException extends RuntimeException {
    public PrivateChatAlreadyExistException() {
        super("private chat already exists");
    }
}
