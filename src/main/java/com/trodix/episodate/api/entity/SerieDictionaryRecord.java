package com.trodix.episodate.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SerieDictionary")
public class SerieDictionaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = Website.class)
    private Website website;

    @ManyToOne(targetEntity = Serie.class)
    private Serie serie;

    @Column
    private String seriePartialUri;
    
}
