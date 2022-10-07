package com.trodix.episodate.api.services;

import java.net.URI;
import java.text.MessageFormat;

import org.springframework.stereotype.Service;

import com.trodix.episodate.api.config.xml.ScrapperConfig;
import com.trodix.episodate.api.config.xml.ScrapperConfigListRoot;
import com.trodix.episodate.api.entity.SerieDictionaryRecord;
import com.trodix.episodate.api.models.EpisodeQuery;
import com.trodix.episodate.api.models.response.SerieSearchResponse;
import com.trodix.episodate.api.repository.SerieDictionaryRecordRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SeriesServiceXmlImpl {

    private final ScrapperConfigListRoot scrapperConfigList;
    private final ScrapperServiceImpl scrapperServiceImpl;
    private final SerieDictionaryRecordRepository serieDictionaryRecordRepository;

    public SeriesServiceXmlImpl(final ScrapperServiceImpl scrapperServiceImpl,
            final ScrapperConfigListRoot scrapperConfigList,
            final SerieDictionaryRecordRepository serieDictionaryRecordRepository) {
        this.scrapperConfigList = scrapperConfigList;
        this.scrapperServiceImpl = scrapperServiceImpl;
        this.serieDictionaryRecordRepository = serieDictionaryRecordRepository;
    }

    public SerieSearchResponse searchEpisode(final EpisodeQuery query) {
        SerieSearchResponse result = null;

        for (final ScrapperConfig config : scrapperConfigList.getScrapperConfigList()) {

            try {

                final String hostname = new URI(config.getBaseUrl()).getHost();
                final SerieDictionaryRecord item = this.serieDictionaryRecordRepository
                        .findByWebsiteHostnameAndSerieName(hostname, query.getSerieName()).orElse(null);

                if (item != null) {
                    log.info(MessageFormat.format("Using found URL from database for serieName {0} => {1}", query.getSerieName(), item.getSeriePartialUri()));
                    query.setSerieName(item.getSeriePartialUri());
                }

                if (result == null) {
                    final String url = scrapperServiceImpl.getUrl(config, query);
                    result = new SerieSearchResponse();
                    result.addUrl(url);
                    result.setEpisode(query.getEpisode());
                    result.setSerieName(query.getSerieName());
                    result.setSeason(query.getSeason());
                } else {
                    final String url = scrapperServiceImpl.getUrl(config, query);
                    result.addUrl(url);
                }

            } catch (final Exception e) {
                log.debug("Error while fetching episode", e);
            }
        }

        return result;
    }

}
