package org.bits.assignment.service;


import org.bits.assignment.model.SearchRequest;
import org.bits.assignment.model.SearchResponse;

import java.util.List;

public interface AnimeSearchService {

    List<SearchResponse> genreSearch (SearchRequest searchRequest);


}
