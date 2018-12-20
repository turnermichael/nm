package com.acme.thing.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThingRepository extends PagingAndSortingRepository<Thing, String> {
}
