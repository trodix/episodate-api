package com.trodix.episodate.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trodix.episodate.api.entity.SerieDictionaryRecord;

@Repository
public interface SerieDictionaryRecordRepository extends JpaRepository<SerieDictionaryRecord, Long> {

    @Query("SELECT sdr FROM Website w JOIN SerieDictionaryRecord sdr JOIN Serie s WHERE w.websiteHostname = :websiteHostname AND s.serieName = :serieName")
    public Optional<SerieDictionaryRecord> findByWebsiteHostnameAndSerieName(@Param("websiteHostname") String websiteHostname, @Param("serieName") String serieName);
    
}
