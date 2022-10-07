package com.trodix.episodate.api.config.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "scrapperConfig")
public class ScrapperConfig {

    @XmlAttribute(name = "baseUrl")
    private String baseUrl;

    @XmlElement
    private PatternConfig patternConfig;

}
