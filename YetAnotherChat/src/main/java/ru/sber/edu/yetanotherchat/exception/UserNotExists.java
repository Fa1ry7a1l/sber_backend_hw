package ru.sber.edu.yetanotherchat.exception;

public class UserNotExists extends RuntimeException {
    public UserNotExists(String email) {
        super(String.format("User with email '%s' does not exist", email));
    }
}
