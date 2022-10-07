package com.trodix.episodate.api.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EpisodeQuery {

    private String serieName;

    private Integer season;

    private Integer episode;
    
}
