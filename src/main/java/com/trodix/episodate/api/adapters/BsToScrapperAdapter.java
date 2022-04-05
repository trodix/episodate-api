package com.trodix.episodate.api.adapters;

import java.io.IOException;
import java.text.MessageFormat;
import com.trodix.episodate.api.core.interfaces.ScrapperAdapter;
import com.trodix.episodate.api.models.SerieSearchResponse;
import com.trodix.episodate.core.exception.ResourceNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BsToScrapperAdapter implements ScrapperAdapter {

    public static final String SCRAPPER_BASE_URL = "https://bs.to";
    public static final String SCRAPPER_SERIE_CONTEXT = "serie";
    public static final String SCRAPPER_DEFAULT_LANG = "en";

    @Override
    public String getUrl() {
        return SCRAPPER_BASE_URL;
    }

    @Override
    public SerieSearchResponse findEpisodeLink(final String serieName, final Integer season, final Integer episode) throws ResourceNotFoundException {

        try {
            final Document serieListEpisodePage = Jsoup
                    .connect(
                            SCRAPPER_BASE_URL + "/" + SCRAPPER_SERIE_CONTEXT + "/" + serieName + "/" + season + "/" + SCRAPPER_DEFAULT_LANG)
                    .get();

            final Elements episodeListMeta = serieListEpisodePage.select("meta[property='og:url']");

            if (episodeListMeta.attr("content").contains("/" + season)) {

                final Elements episodeList = serieListEpisodePage.select("table.episodes a");

                for (final Element element : episodeList) {
                    if (element.ownText().equals(String.valueOf(episode))) {
                        final SerieSearchResponse response = new SerieSearchResponse();
                        response.setSerieName(serieName);
                        response.setSeason(season);
                        response.setEpisode(episode);
                        response.setEpisodeName(element.attr("title"));
                        response.addUrl(SCRAPPER_BASE_URL + "/" + element.attr("href"));
                        return response;
                    }
                }

            }

            throw new ResourceNotFoundException(MessageFormat.format("The serie {0} season {1}, episode {2} was not found.", serieName, season, episode));

        } catch (final IOException e) {
            throw new ResourceNotFoundException(MessageFormat.format("The serie {0} season {1}, episode {2} was not found.", serieName, season, episode), e);
        }

    }

}
