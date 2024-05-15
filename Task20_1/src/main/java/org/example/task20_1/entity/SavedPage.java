package org.example.task20_1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;


@Getter
@Setter
@AllArgsConstructor
public class SavedPage {
    private final HttpHeaders headers;
    private final String body;
}
