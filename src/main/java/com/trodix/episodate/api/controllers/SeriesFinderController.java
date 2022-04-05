package com.trodix.episodate.api.controllers;

import java.text.MessageFormat;
import com.trodix.episodate.api.core.interfaces.services.SeriesService;
import com.trodix.episodate.api.models.SerieSearchResponse;
import com.trodix.episodate.core.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/series")
public class SeriesFinderController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/search/{serieName}")
    public SerieSearchResponse getSerie(@PathVariable final String serieName, @RequestParam final Integer season, @RequestParam final Integer episode)
            throws ResourceNotFoundException {
        final SerieSearchResponse result = seriesService.searchEpisode(serieName, season, episode);

        if (result != null && !result.getUrls().isEmpty()) {
            return result;
        }

        throw new ResourceNotFoundException(MessageFormat.format("The serie {0} season {1}, episode {2} was not found.", serieName, season, episode));
    }

}
