package com.acme.thing.service;

import com.acme.thing.model.Thing;
import com.acme.thing.model.ThingRepository;
import com.acme.thing.support.ThingDataloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ThingService {

    private ThingRepository repository;
    private ThingDataloader dataloader;

    @Autowired
    public ThingService(ThingRepository repository, ThingDataloader dataloader) {
        this.repository = repository;
        this.dataloader = dataloader;
    }

    List<Thing> getAll() throws IOException {
        this.checkForData();  // synchronized
        Pageable pageRequest = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC,"name");
        Page<Thing> page = repository.findAll(pageRequest);
        return page.getContent();
    }

    // synchronized to avoid redundant calls to the data loader in the event of simultaneous initial requests
    private synchronized void checkForData() throws IOException {
        if (repository.count() == 0) {
            dataloader.loadData();
        }
    }
}
