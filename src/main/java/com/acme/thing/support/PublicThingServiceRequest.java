package com.acme.thing.support;

import com.acme.thing.model.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

class PublicThingServiceRequest {
    private static final Logger LOG = LoggerFactory.getLogger(PublicThingServiceRequest.class);
    private static final String SERVICE_URL = "https://api.publicapis.org/entries";

    /*
     * may return null
    */
    List<Thing> getThingsFromPublicApi() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<PublicThingServiceResponse> responseEntity = restTemplate.getForEntity(SERVICE_URL, PublicThingServiceResponse.class);
            return responseEntity.getBody().getEntries();
        } catch (RestClientException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    static String getServiceUrl() {
        return SERVICE_URL;
    }
}
