package org.bits.assignment.controllers;

import org.bits.assignment.model.SearchRequest;
import org.bits.assignment.model.SearchResponse;
import org.bits.assignment.service.AnimeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;


@RestController
@Configuration
@Slf4j
@RequestMapping(value = "/v1/api")
public class SearchController {

    @Autowired
    AnimeSearchService animeSearchService;

    public SearchController(AnimeSearchService animeSearchService) {
        this.animeSearchService = animeSearchService;
    }

    @PostMapping(path = "/searchByGenre", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchResponse>> genre_search(@RequestBody SearchRequest searchRequest) {
        log.info("Request reached here");
        List<SearchResponse> searchResponse = animeSearchService.genreSearch(searchRequest);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

//    @PostMapping(path = "/searchByTitle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<SearchResponse> title_search(@RequestBody SearchRequest searchRequest) {
//        SearchResponse searchResponse = animeSearchService.insertOrUpdateRecords(searchRequest);
//        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
//    }
//
//    @PostMapping(path = "/searchByDescription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<SearchResponse> desc_search(@RequestBody SearchRequest searchRequest) {
//        SearchResponse searchResponse = animeSearchService.deleteRecords(searchRequest);
//        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
//    }

}
