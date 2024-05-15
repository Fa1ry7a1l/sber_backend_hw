package ru.sber.edu.yetanotherchat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sber.edu.yetanotherchat.dto.AddUserDTO;
import ru.sber.edu.yetanotherchat.dto.CreateChatDTO;
import ru.sber.edu.yetanotherchat.dto.MessageDTO;
import ru.sber.edu.yetanotherchat.entity.Message;
import ru.sber.edu.yetanotherchat.entity.User;
import ru.sber.edu.yetanotherchat.service.ChatService;
import ru.sber.edu.yetanotherchat.service.UserService;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@Log4j2
public class ChatController {

    private final UserService userService;
    private final ChatService chatService;

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id, Authentication authentication, Model model) {
        log.info("get");
        DefaultOidcUser userId = (DefaultOidcUser) authentication.getPrincipal();

        User user = userService.getUser(userId.getEmail());

        var opChat = user.getChats().stream().filter(chat -> chat.getId().equals(id)).findFirst();
        if (opChat.isEmpty()) {
            return "redirect:/";
        }
        var chat = opChat.get();

        MessageDTO messageDTO = new MessageDTO();

        var chats = user.getChats().stream().sorted().collect(Collectors.toList());

        log.info(chats.size());

        model.addAttribute("currentChat", chat);
        model.addAttribute("isChatPublic", chat.getIsMultiPerson());
        model.addAttribute("messageDTO", messageDTO);
        model.addAttribute("user", user);
        model.addAttribute("chats", chats);
        model.addAttribute("messages", chat.getMessages().stream().sorted().toList());
        model.addAttribute("selectedChatId", id);

        return "chat";
    }

    @PostMapping("/chat/{id}")
    public String chat(@PathVariable Long id, MessageDTO messageDTO, Authentication authentication) {
        log.info("post");

        DefaultOidcUser userId = (DefaultOidcUser) authentication.getPrincipal();

        User user = userService.getUser(userId.getEmail());

        var opChat = user.getChats().stream().filter(chat -> chat.getId().equals(id)).findFirst();
        if (opChat.isEmpty()) {
            return "redirect:/";
        }
        var chat = opChat.get();
        Message message = new Message();
        message.setChat(chat);
        message.setAuthor(user);
        message.setText(messageDTO.getText());
        message.setTimeCreated(OffsetDateTime.now());

        chat.getMessages().add(message);
        chatService.save(chat);

        return String.format("redirect:/chat/%d", id);
    }

    @GetMapping("/chat/create")
    public String chatCreateGet(Model model) {
        log.info("creatingForm");

        CreateChatDTO chatDTO = new CreateChatDTO();
        model.addAttribute("currentChat", chatDTO);


        return "createChatForm";
    }

    @PostMapping("/chat/create")
    public String chatCreatePost(Authentication authentication, CreateChatDTO createChatDTO) {
        log.info("creatingFormPost");
        log.info(createChatDTO.getIsPublic());

        DefaultOidcUser userId = (DefaultOidcUser) authentication.getPrincipal();
        chatService.createChat(userId.getEmail(), createChatDTO);
        return "redirect:/";
    }

    @GetMapping("/chat/add/{id}")
    public String addUserToChat(@PathVariable Long id, Authentication authentication, Model model) {
        var chat = chatService.getChatById(id);

        model.addAttribute("chat", chat);
        model.addAttribute("id", id);
        model.addAttribute("userDTO", new AddUserDTO());
        return "addUserToChat";
    }

    @PostMapping("/chat/add/{id}")
    public String addUserToChatPosh(@PathVariable Long id, AddUserDTO addUserDTO, Authentication authentication) {

        log.info(addUserDTO.getUserName());
        return String.format("redirect:/chat/%d", id);
    }
}
