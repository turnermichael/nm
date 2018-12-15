package com.acme.thing.service;

import com.acme.thing.model.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/things")
public class ThingRestController {

    private ThingService service;

    @Autowired
    public ThingRestController(ThingService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Thing>> get() throws Exception {
        List<Thing> entries = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(entries);
    }

    @ExceptionHandler
    private ResponseEntity<String> exceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(ex.getMessage());
    }
}
