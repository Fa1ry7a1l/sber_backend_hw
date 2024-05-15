package org.example.task20_1.service;

import lombok.AllArgsConstructor;
import org.example.task20_1.entity.SavedPage;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.WeakHashMap;

@Service
@AllArgsConstructor
public class CacheService {

    private final WeakHashMap<URI, SavedPage> cache = new WeakHashMap<>();

    private final ClientService clientService;

    public SavedPage getDataFromUrl(URI url) {
        SavedPage page = cache.get(url);
        if (page == null) {
            page = clientService.makeRequest(url);
            cache.put(url, page);
        }
        return page;
    }

}
