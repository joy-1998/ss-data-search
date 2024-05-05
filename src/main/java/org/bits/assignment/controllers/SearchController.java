package org.bits.assignment.controllers;

import org.bits.assignment.model.SearchRequest;
import org.bits.assignment.model.SearchResponse;
import org.bits.assignment.service.AnimeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;


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

        if(!nonNull(searchRequest.getTags())){
            List<SearchResponse> searchResponses = Collections.singletonList(SearchResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .respMsg("genre tags is missing")
                    .build());
            return new ResponseEntity<>(searchResponses,HttpStatus.BAD_REQUEST);
        }else if(!searchRequest.getTags().isEmpty()){
            List<SearchResponse> searchResponse = animeSearchService.searchQuery(searchRequest);
            return new ResponseEntity<>(searchResponse, HttpStatus.OK);
        }
        List<SearchResponse> searchResponses = Collections.singletonList(SearchResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .respMsg("genre cannot be empty")
                .build());
        return new ResponseEntity<>(searchResponses,HttpStatus.BAD_REQUEST);

    }

    @PostMapping(path = "/searchByTitle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchResponse>> title_search(@RequestBody SearchRequest searchRequest) {
        if(StringUtils.hasLength(searchRequest.getTitle())){
            List<SearchResponse> searchResponse = animeSearchService.searchQuery(searchRequest);
            return new ResponseEntity<>(searchResponse, HttpStatus.OK);
        }

        List<SearchResponse> searchResponses = Collections.singletonList(SearchResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .respMsg("missing title attribute")
                .build());
        return new ResponseEntity<>(searchResponses,HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/searchByDescription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchResponse>> desc_search(@RequestBody SearchRequest searchRequest) {
        if(StringUtils.hasLength(searchRequest.getDescription())){
            List<SearchResponse> searchResponse = animeSearchService.searchQuery(searchRequest);
            return new ResponseEntity<>(searchResponse, HttpStatus.OK);
        }
        List<SearchResponse> searchResponses = Collections.singletonList(SearchResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .respMsg("missing description attribute")
                .build());
        return new ResponseEntity<>(searchResponses,HttpStatus.BAD_REQUEST);

    }

}
