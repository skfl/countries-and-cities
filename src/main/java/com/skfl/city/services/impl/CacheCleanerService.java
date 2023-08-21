package com.skfl.city.services.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CacheCleanerService {

    @Scheduled(cron = "0 0/15 * * * *")
    @CacheEvict(value = "cityByCountryName", allEntries = true)
    public void clearCityByCountryNameCache() {
        log.info("CityByCountryName Cache cleared");
    }

    @Scheduled(cron = "0 0/15 * * * *")
    @CacheEvict(value = "cityByCityName", allEntries = true)
    public void clearCityByCityNameCache() {
        log.info("CityByCityName Cache cleared");
    }

    @Scheduled(cron = "0 0/15 * * * *")
    @CacheEvict(value = "cityCache", allEntries = true)
    public void clearCityCache() {
        log.info("City Cache cleared");
    }

    @Scheduled(cron = "0 0/15 * * * *")
    @CacheEvict(value = "uniqueNamesCache", allEntries = true)
    public void clearUniqueNamesCache() {
        log.info("UniqueNames Cache cleared");
    }
}
