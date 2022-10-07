package com.trodix.episodate.api.config.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScrapperConfigListRoot {

    @XmlElementWrapper(name = "scrapperConfigList")
    @XmlElement(name = "scrapperConfig")
    private List<ScrapperConfig> scrapperConfigList;

}
