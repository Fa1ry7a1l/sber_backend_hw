package org.example.task20_1.service;

import org.example.task20_1.entity.SavedPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.Calendar;

@Service
public class ClientService {

    public SavedPage makeRequest(URI uri) {
        WebClient client = WebClient.builder().baseUrl(uri.toString())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                )).build();

        return client.get()

                .exchangeToMono(clientResponse -> {
                    if (!clientResponse.statusCode().is2xxSuccessful()) {
                        throw new RuntimeException("Ошибка получения ответа от сервера");
                    }

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("remembered_at", Calendar.getInstance().getTime().toString());

                    var headers = clientResponse.headers();

                    httpHeaders.addAll(headers.asHttpHeaders());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(s ->
                                    {
                                        var v =Mono.just(new SavedPage(httpHeaders, s));
                                        return v;
                                    }
                                    );


                }).block();
    }
}
