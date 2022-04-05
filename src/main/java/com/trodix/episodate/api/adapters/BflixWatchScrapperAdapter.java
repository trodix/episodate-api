package com.trodix.episodate.api.adapters;

import java.io.IOException;
import java.text.MessageFormat;
import com.trodix.episodate.api.core.interfaces.ScrapperAdapter;
import com.trodix.episodate.api.models.SerieSearchResponse;
import com.trodix.episodate.core.exception.ResourceNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BflixWatchScrapperAdapter implements ScrapperAdapter {

    public static final String SCRAPPER_BASE_URL = "https://bflix.watch";
    public static final String SCRAPPER_SERIE_CONTEXT = "episode";

    @Override
    public String getUrl() {
        return SCRAPPER_BASE_URL;
    }

    @Override
    public SerieSearchResponse findEpisodeLink(final String serieName, final Integer season, final Integer episode) throws ResourceNotFoundException {
        final String URL = SCRAPPER_BASE_URL + "/" + SCRAPPER_SERIE_CONTEXT + "/" + serieName + "-season-" + season + "-episode-" + episode;

        try {
            final Document serieListEpisodePage = Jsoup.connect(URL).get();

            final Elements episodePageUrlElement = serieListEpisodePage.select("meta[property='og:url']");

            final SerieSearchResponse response = new SerieSearchResponse();
            response.setSerieName(serieName);
            response.setSeason(season);
            response.setEpisode(episode);
            response.setEpisodeName(serieListEpisodePage.attr("title"));
            response.addUrl(episodePageUrlElement.attr("content"));

            return response;

        } catch (final IOException e) {
            throw new ResourceNotFoundException(MessageFormat.format("The serie {0} season {1}, episode {2} was not found.", serieName, season, episode));
        }

    }

}
