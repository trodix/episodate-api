package com.trodix.episodate.api.services;

import com.trodix.episodate.api.config.ScrapperAdapterConfig;
import com.trodix.episodate.api.core.interfaces.ScrapperAdapter;
import com.trodix.episodate.api.core.interfaces.services.SeriesService;
import com.trodix.episodate.api.models.SerieSearchResponse;
import com.trodix.episodate.core.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private ScrapperAdapterConfig scrapperConfig;

    @Override
    public String normalizeName(final String rawName) {
        return rawName.replaceAll("\\s+", "-");
    }

    @Override
    public SerieSearchResponse searchEpisode(final String serieName, final Integer season, final Integer episode) {
        SerieSearchResponse result = null;


        for (final ScrapperAdapter adapter : scrapperConfig.registeredScrapperAdapters()) {
            try {
                if (result == null) {
                    result = adapter.findEpisodeLink(normalizeName(serieName), season, episode);
                } else {

                    result.addUrl(adapter.findEpisodeLink(normalizeName(serieName), season, episode).getUrls());

                }
            } catch (final ResourceNotFoundException e) {
                log.debug("Serie " + serieName + " not found for the base url " + adapter.getUrl());
            }
        }


        return result;
    }

}
