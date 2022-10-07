package com.trodix.episodate.api.config.xml;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.Data;

@Data
public class Binding {

    @XmlAttribute(name = "patternVariable")
    private String patternVariable;

    @XmlAttribute(name = "numberFormat")
    private String numberFormat;
    
}
