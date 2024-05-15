package ru.sber.edu.yetanotherchat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateChatDTO {
    private String chatName;
    private Boolean isPublic;
    private String users;

}
