package com.trodix.episodate.api.config.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "patternConfig")
public class PatternConfig {

    @XmlElement
    private String patternDefinition;

    @XmlElementWrapper(name = "patternBindings")
    @XmlElement(name = "bind")
    private List<Binding> patternBindings;

}
