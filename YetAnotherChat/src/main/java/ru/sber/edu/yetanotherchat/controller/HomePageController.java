package ru.sber.edu.yetanotherchat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sber.edu.yetanotherchat.dto.MessageDTO;
import ru.sber.edu.yetanotherchat.entity.User;
import ru.sber.edu.yetanotherchat.service.ChatService;
import ru.sber.edu.yetanotherchat.service.MessageService;
import ru.sber.edu.yetanotherchat.service.UserService;

import java.util.stream.Collectors;

@AllArgsConstructor
@Log
@Controller
public class HomePageController {

    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping
    public String homePage(Authentication authentication, Model model) {
        DefaultOidcUser userId = (DefaultOidcUser) authentication.getPrincipal();
        User user = userService.getUser(userId.getEmail());


        var chats = user.getChats().stream().sorted().collect(Collectors.toList());

        model.addAttribute("chats", chats);
        model.addAttribute("messageDTO", new MessageDTO());

        return "home";
    }

    @PostMapping
    public String homePageGlobalMessage(Authentication authentication, MessageDTO messageDTO, Model model) {
        DefaultOidcUser userId = (DefaultOidcUser) authentication.getPrincipal();
        User user = userService.getUser(userId.getEmail());

        messageService.sendGlobalMessage(messageDTO,user);


        return "redirect:/";
    }
}
