package com.trodix.episodate.api.controllers;

import java.io.IOException;
import com.trodix.episodate.api.core.interfaces.services.SeriesService;
import com.trodix.episodate.api.models.SerieSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/public/series")
public class SeriesFinderController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/search/{serieName}")
    public SerieSearchResponse getSerie(@PathVariable final String serieName, @RequestParam final Integer season, @RequestParam final Integer episode)
            throws IOException {
        final SerieSearchResponse result = seriesService.searchEpisode(serieName, season, episode);

        if (result != null) {
            return result;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
