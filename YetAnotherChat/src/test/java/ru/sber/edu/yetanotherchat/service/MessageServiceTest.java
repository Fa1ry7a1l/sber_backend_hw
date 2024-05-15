package ru.sber.edu.yetanotherchat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.edu.yetanotherchat.entity.Chat;
import ru.sber.edu.yetanotherchat.entity.Message;
import ru.sber.edu.yetanotherchat.entity.User;
import ru.sber.edu.yetanotherchat.repository.MessageRepository;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(messageRepository);
    }


    @Test
    public void givenMessageService_whenSendMessageWithCorrectData_thenReturnMessage() {
        User user = new User(1L, "Andre", "login", new HashSet<>(), new TreeSet<>());
        Chat chat = new Chat(1L, "chat1", false, new HashSet<>(), Set.of(user));
        user.getChats().add(chat);

        Message message = new Message(1L, user, "text", OffsetDateTime.now(), chat);

        when(messageRepository.save(message)).thenReturn(message);

    }
}