package ru.sber.edu.yetanotherchat.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.sber.edu.yetanotherchat.dto.CreateChatDTO;
import ru.sber.edu.yetanotherchat.entity.Chat;
import ru.sber.edu.yetanotherchat.entity.User;
import ru.sber.edu.yetanotherchat.exception.AddingPersonToPrivateChatException;
import ru.sber.edu.yetanotherchat.exception.ChatNoExist;
import ru.sber.edu.yetanotherchat.exception.NonMemberUserException;
import ru.sber.edu.yetanotherchat.repository.ChatRepository;
import ru.sber.edu.yetanotherchat.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;


    public Chat createPrivateChat(String name, Set<User> members) {

        Chat chat = new Chat(name, members, false);
        chat = chatRepository.save(chat);

        return chat;
    }

    public Chat createPublicChat(String name, Set<User> users) {
        Chat chat = new Chat(name, users, true);
        chat = chatRepository.save(chat);

        return chat;
    }

    public Chat addUserToChat(Chat chat, User userMember, User userNew) {
        checkChatExisting(chat);

        checkIfUserMemberIsRealMember(chat, userMember);

        checkChatMultiperson(chat, userNew);

        chat.getMembers().add(userNew);

        chat = chatRepository.save(chat);
        return chat;
    }

    private void checkIfUserMemberIsRealMember(Chat chat, User userMember) {
        if (!chat.getMembers().contains(userMember)) {
            throw new NonMemberUserException(userMember, chat);
        }
    }

    private void checkChatMultiperson(Chat chat, User user) {
        if (!chat.getIsMultiPerson()) {
            throw new AddingPersonToPrivateChatException(user, chat);
        }
    }

    private void checkChatExisting(Chat chat) {
        if (!chatRepository.existsById(chat.getId())) {
            throw new ChatNoExist("Chat doesnt exist ", chat);
        }
    }

    public List<Chat> getAllChatsByUser(User user) {
        return new ArrayList<>(user.getChats());

    }


    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public void createChat(String author, CreateChatDTO createChatDTO) {
        var users = Arrays.stream(createChatDTO.getUsers().split(",")).map(String::trim).collect(Collectors.toSet());
        users.add(author);
        var chatMembers = userRepository.findByLoginIn(users);
        if (chatMembers.size() != 2 && createChatDTO.getIsPublic()) {
            throw new RuntimeException("error creating private chat");
        }

        if (!createChatDTO.getIsPublic()) {
            createPrivateChat(createChatDTO.getChatName(), chatMembers);
        } else {
            createPublicChat(createChatDTO.getChatName(), chatMembers);
        }
    }

    public Chat getChatById(Long id) {
        return chatRepository.getReferenceById(id);
    }
}
