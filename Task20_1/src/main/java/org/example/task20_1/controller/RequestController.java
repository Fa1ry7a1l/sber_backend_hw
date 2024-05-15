package org.example.task20_1.controller;

import lombok.AllArgsConstructor;
import org.example.task20_1.service.CacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@AllArgsConstructor
@RestController
public class RequestController {

    private final CacheService cacheService;


    @GetMapping("/get")
    public ResponseEntity<String> getPage(@RequestParam("url") URI url) {
        System.out.println(url.toString());
        if (!url.toString().startsWith("http")) {
            url = URI.create("https://" + url.toString());
        }

        var page = cacheService.getDataFromUrl(url);


        return new ResponseEntity<String>(page.getBody(), page.getHeaders(), HttpStatus.OK);
    }
}
