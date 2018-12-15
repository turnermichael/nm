## Problem Statement
Create a responsive (phone, tablet, desktop) web application that allows the user to quick filter a list of things.
The top of the page will have a search input field and then below that a list of things in response to the filter.
The things should be sorted alphabetically. The things could be anything, but should be AJAX pulled from a backend
service that you write and should ultimately be pulled from an open public API.

Here’s an example list of API’s curated on GitHub, https://github.com/toddmotto/public-apis but feel free to use any
public API you wish.

When you have completed the following challenge, place your code in a code repository, ex. github, bitbucket,
dropbox, etc.

## Solution Overview
The 'Things' I chose are Public APIs available [here](https://api.publicapis.org/entries). The solution is a Spring Boot
App with a single rest endpoint, '/api/v1/things', backed by an embedded H2 database. The database schema is managed by
[flyway](https://flywaydb.org/). The list of things is read from the public service and loaded into the database upon the
first request. If there is any problem loading the list of the things from the public service the server falls back to
reading the list from a file bundled in the jar. The front end is an AngularJS solution composed of a single template 
supported by an Angular controller and service and styled with bootstrap. The controller supplies the template with the 
model while the service is responsible for AJAX requests to the back end. No server side templating is used. Filtering
is done using an Angular filter and alpha sorting is left to a Spring Paging & Sorting Repository. This is a minimalist 
approach. The problem was solved using as conventional of means as possible and with a minimum amount of new code. A 
couple JUnit tests are provided for illustration.

## Limitations
1. Will not scale to a large number of things. There is no data paging between the browser and server, all 'things' are
   returned to the browser with each request.
2. There is no security.

## Basic architecture is as follows:

#### Back end
````
model
   Thing - JPA Entity
   ThingRepository - Spring Paging & Sorting Repository
service
   ThingRestController - Annotated with @RestController
   ThingService -  Service bean, annotated with @Service, used by the controller
support
   ThingDataLoader - Supporting Component that loads data into the database either from the public service or the local file
   LocalThingReader - Supporting Component that reads things from the local file
   PublicThingServiceRequest - Simple wrapper around RestTemplate.getForEntity()
   PublicThingServiceResponse - Response from public rest service is unmarshalled into this simple POJO
````

#### Front end
````
index.html - SPA template
thingController - AngularJS Controller
thingService - AngularJS Service
````

## Build
````
mvn package
````

## Run
````
java -jar target/nm-0.0.1-SNAPSHOT.jar
````

Supports standard Spring property overrides. Eg..
````
java -jar target/nm-0.0.1-SNAPSHOT.jar --server.port=9090
````

## Reference
[Todd Moto's Public API Project](https://github.com/toddmotto/public-apis)
