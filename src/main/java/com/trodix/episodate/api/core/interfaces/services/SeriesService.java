package com.trodix.episodate.api.core.interfaces.services;

import java.io.IOException;
import com.trodix.episodate.api.models.SerieSearchResponse;

public interface SeriesService {

    public String normalizeName(final String rawName);

    public SerieSearchResponse searchEpisode(final String serieName, final Integer season, final Integer episode) throws IOException;

}
