package ru.sber.edu.yetanotherchat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sber.edu.yetanotherchat.dto.MessageDTO;
import ru.sber.edu.yetanotherchat.entity.Chat;
import ru.sber.edu.yetanotherchat.entity.Message;
import ru.sber.edu.yetanotherchat.entity.User;
import ru.sber.edu.yetanotherchat.exception.NonMemberUserException;
import ru.sber.edu.yetanotherchat.repository.MessageRepository;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void sendMessage(MessageDTO messageDTO, Chat chat, User user) {

        checIfUserIsMember(chat, user);
        Message message = new Message();
        message.setTimeCreated(OffsetDateTime.now());
        message.setAuthor(user);
        message.setText(messageDTO.getText());
        message.setChat(chat);

        message = messageRepository.save(message);
        user.getMessages().add(message);
    }

    private static void checIfUserIsMember(Chat chat, User user) {
        if (!chat.getMembers().contains(user)) {
            throw new NonMemberUserException(user, chat);
        }
    }

    public void sendGlobalMessage(MessageDTO messageDTO, User user) {
        var chats = user.getChats();
        for (var chat : chats) {
            this.sendMessage(messageDTO, chat, user);
        }
    }
}
