package com.trodix.episodate.api.core.interfaces.services;

import com.trodix.episodate.api.models.SerieSearchResponse;
import com.trodix.episodate.core.exception.ResourceNotFoundException;

public interface SeriesService {

    public String normalizeName(final String rawName);

    public SerieSearchResponse searchEpisode(final String serieName, final Integer season, final Integer episode) throws ResourceNotFoundException;

}
