package com.trodix.episodate.api.services;

import java.io.IOException;
import com.trodix.episodate.api.adapters.BsToScrapperAdapter;
import com.trodix.episodate.api.core.interfaces.services.SeriesService;
import com.trodix.episodate.api.models.SerieSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private BsToScrapperAdapter scrapperConfig;

    @Override
    public String normalizeName(final String rawName) {
        return rawName.replaceAll("\\s+", "-");
    }

    @Override
    public SerieSearchResponse searchEpisode(final String serieName, final Integer season, final Integer episode) throws IOException {
        return scrapperConfig.findEpisodeLink(normalizeName(serieName), season, episode);
    }

}
