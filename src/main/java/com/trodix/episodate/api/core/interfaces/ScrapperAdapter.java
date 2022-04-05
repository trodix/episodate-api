package com.trodix.episodate.api.core.interfaces;

import com.trodix.episodate.api.models.SerieSearchResponse;
import com.trodix.episodate.core.exception.ResourceNotFoundException;

public interface ScrapperAdapter {

    public SerieSearchResponse findEpisodeLink(final String serieName, final Integer season, final Integer episode) throws ResourceNotFoundException;

    public String getUrl();

}
