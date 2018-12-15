package com.acme.thing.support;

import com.acme.thing.model.Thing;
import com.acme.thing.model.ThingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ThingDataloader {
    private static final Logger LOG = LoggerFactory.getLogger(ThingDataloader.class);

    private ThingRepository repository;

    private LocalThingReader dataReader;

    @Autowired
    ThingDataloader(ThingRepository repository, LocalThingReader dataReader) {
        this.repository = repository;
        this.dataReader = dataReader;
    }

    public void loadData() throws IOException {
        List<Thing> things = new PublicThingServiceRequest().getThingsFromPublicApi();
        if ( things == null || things.isEmpty() ) {
            LOG.warn("Failed to load data from {}", PublicThingServiceRequest.getServiceUrl());
            things = dataReader.readThings();
            LOG.info("Loaded {} things from {}", things.size(), dataReader.getResourceLocation());
        } else {
            LOG.info("Loaded {} things from {}", things.size(), PublicThingServiceRequest.getServiceUrl());
        }
        repository.saveAll(things);
    }
}
