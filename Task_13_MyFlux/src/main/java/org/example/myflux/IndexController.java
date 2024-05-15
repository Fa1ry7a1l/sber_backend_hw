package org.example.myflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    PersonService personService;

    @GetMapping(value = "",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux get()
    {
        return Flux.fromArray(personService.getPersons())
                .delayElements(Duration.ofMillis(2_000));
    }
}
