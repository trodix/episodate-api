package com.trodix.episodate.api.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class SerieSearchResponse {

    private String serieName;
    private Integer season;
    private Integer episode;
    private String episodeName;
    private List<String> urls;

    public void addUrl(final String url) {
        if (this.urls == null) {
            this.urls = new ArrayList<>();
        }
        this.urls.add(url);
    }

}
