package com.trodix.episodate.api.config;

import java.util.ArrayList;
import java.util.List;
import com.trodix.episodate.api.adapters.BflixWatchScrapperAdapter;
import com.trodix.episodate.api.adapters.BsToScrapperAdapter;
import com.trodix.episodate.api.core.interfaces.ScrapperAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperAdapterConfig {

    @Bean
    public List<ScrapperAdapter> registeredScrapperAdapters() {
        final List<ScrapperAdapter> registeredScrapperAdapters = new ArrayList<>();

        registeredScrapperAdapters.add(new BsToScrapperAdapter());
        registeredScrapperAdapters.add(new BflixWatchScrapperAdapter());

        return registeredScrapperAdapters;
    }

}
