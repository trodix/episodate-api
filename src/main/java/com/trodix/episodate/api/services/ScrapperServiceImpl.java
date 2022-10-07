package com.trodix.episodate.api.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trodix.episodate.api.config.xml.Binding;
import com.trodix.episodate.api.config.xml.PatternConfig;
import com.trodix.episodate.api.config.xml.ScrapperConfig;
import com.trodix.episodate.api.models.EpisodeQuery;
import com.trodix.episodate.core.exception.ResourceNotFoundException;

@Service
public class ScrapperServiceImpl {

    public static final String TPL_BASE_URL = "baseUrl";
    public static final String TPL_SERIE_NAME = "serieName";
    public static final String TPL_SEASON_NUMBER = "seasonNumber";
    public static final String TPL_EPISODE_NUMBER = "episodeNumber";

    @Qualifier("apiRestTemplate")
    private final RestTemplate apiRestTemplate;

    public ScrapperServiceImpl(final RestTemplate restTemplate) {
        this.apiRestTemplate = restTemplate;
    }

    public String getUrl(final ScrapperConfig config, final EpisodeQuery query) throws ResourceNotFoundException {

        final String url = buildUrl(config, query);
        final HttpStatus status = apiRestTemplate.getForEntity(url, String.class).getStatusCode();

        if (status.is2xxSuccessful()) {
            return url;
        }

        throw new ResourceNotFoundException("Resource not found for url " + url + " and query " + query);
    }

    private String findBindingFormat(final List<Binding> patternBindings, final String bindingVarableName) {
        return patternBindings.stream()
                .filter(i -> i.getPatternVariable().equalsIgnoreCase(bindingVarableName)).findFirst().orElseThrow()
                .getNumberFormat();
    }

    private String buildUrl(final ScrapperConfig config, final EpisodeQuery query) {
        final PatternConfig patternConfig = config.getPatternConfig();
        final List<Binding> patternBindings = patternConfig.getPatternBindings();

        final String seasonNumberFormatter = findBindingFormat(patternBindings, TPL_SEASON_NUMBER);
        final String episodeNumberFormatter = findBindingFormat(patternBindings, TPL_EPISODE_NUMBER);

        final Map<String, Serializable> params = new HashMap<>();
        params.put(TPL_BASE_URL, config.getBaseUrl());
        params.put(TPL_SERIE_NAME, query.getSerieName());
        params.put(TPL_SEASON_NUMBER, String.format(seasonNumberFormatter, query.getSeason()));
        params.put(TPL_EPISODE_NUMBER, String.format(episodeNumberFormatter, query.getEpisode()));

        return config.getBaseUrl() + StringSubstitutor.replace(patternConfig.getPatternDefinition(), params, "${", "}");
    }

}
