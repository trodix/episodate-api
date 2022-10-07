package com.trodix.episodate.api.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.trodix.episodate.api.config.xml.ScrapperConfigListRoot;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class ScrapperConfigLoader {

    private final ResourceLoader resourceLoader;

    @Value("${app.scrapper-config-location}")
    private String scrapperConfigLocation;

    public ScrapperConfigLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public ScrapperConfigListRoot loadConfig() throws IOException {
        log.debug("Loading config file from resource " + scrapperConfigLocation);
        final Resource configFile = resourceLoader.getResource(scrapperConfigLocation);
        final XmlMapper mapper = new XmlMapper();
        final File file = configFile.getFile();

        return mapper.readValue(file, ScrapperConfigListRoot.class);
    }

}
