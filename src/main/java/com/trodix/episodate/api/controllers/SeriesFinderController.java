package com.trodix.episodate.api.controllers;

import java.text.MessageFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trodix.episodate.api.models.EpisodeQuery;
import com.trodix.episodate.api.models.request.AddLinkRequest;
import com.trodix.episodate.api.models.response.SerieSearchResponse;
import com.trodix.episodate.api.services.SeriesServiceXmlImpl;
import com.trodix.episodate.core.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/public/series")
public class SeriesFinderController {

    @Autowired
    private SeriesServiceXmlImpl seriesService;

    @GetMapping("/search/{serieName}")
    public SerieSearchResponse getSerie(@PathVariable final String serieName, @RequestParam final Integer season,
            @RequestParam final Integer episode)
            throws ResourceNotFoundException {

        final EpisodeQuery query = new EpisodeQuery();
        query.setSerieName(serieName);
        query.setSeason(season);
        query.setEpisode(episode);

        final SerieSearchResponse result = seriesService.searchEpisode(query);

        if (result != null && !result.getUrls().isEmpty()) {
            return result;
        }

        throw new ResourceNotFoundException(MessageFormat.format("The serie {0} season {1}, episode {2} was not found.",
                serieName, season, episode));
    }

    @PostMapping("/register")
    public void addLink(@Valid @RequestBody final AddLinkRequest addLinkRequest) {
        // TODO
    }

}
