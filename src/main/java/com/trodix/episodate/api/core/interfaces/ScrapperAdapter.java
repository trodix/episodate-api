package com.trodix.episodate.api.core.interfaces;

import java.io.IOException;
import com.trodix.episodate.api.models.SerieSearchResponse;

public interface ScrapperAdapter {

    public SerieSearchResponse findEpisodeLink(final String serieName, final Integer season, final Integer episode) throws IOException;

}
