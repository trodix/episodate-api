package com.trodix.episodate.api.models.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddLinkRequest {

    @NotBlank
    private String serieName;

    @NotBlank
    private String websiteHostname;

    @NotBlank
    private String seriePartialPath;

}
