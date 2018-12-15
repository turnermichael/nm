package com.acme.thing.support;

import com.acme.thing.model.Thing;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class LocalThingReader {

    private static final String LOCAL_DATA_RESOURCE = "classpath:data/things.json";

    public List<Thing> readThings() throws IOException {
        File dataFile = ResourceUtils.getFile(LOCAL_DATA_RESOURCE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dataFile,new TypeReference<List<Thing>>(){});
    }

    String getResourceLocation() {
        return LOCAL_DATA_RESOURCE;
    }
}
