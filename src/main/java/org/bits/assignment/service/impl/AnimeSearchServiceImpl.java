package org.bits.assignment.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bits.assignment.entity.AnimeEntity;
import org.bits.assignment.model.SearchRequest;
import org.bits.assignment.model.SearchResponse;
import org.bits.assignment.repositories.AnimeRepository;
import org.bits.assignment.service.AnimeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


@Service
@Slf4j
public class AnimeSearchServiceImpl implements AnimeSearchService {

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<SearchResponse> searchQuery(SearchRequest searchRequest) {
            List<SearchResponse> searchResponses;
        try{

            Query query = QueryBuilder(searchRequest);
            log.info(query.toString());
            List<AnimeEntity> animeSearch = mongoTemplate.find(query, AnimeEntity.class);
            searchResponses = animeSearch.stream().map(
                    anime ->
                        SearchResponse.builder()
                                .status(HttpStatus.OK)
                                .respMsg("Success")
                                .title(anime.getTitle())
                                .description(anime.getDescription())
                                .mediaType(anime.getMediaType())
                                .tags(anime.getTags())
                                .startYr(anime.getStartYr())
                                .sznOfRelease(anime.getSznOfRelease())
                                .eps(anime.getEps())
                                .contentWarn(anime.getContentWarn())
                                .ongoing(anime.getOngoing())
                                .finishYr(anime.getFinishYr())
                                .studios(anime.getStudios())
                                .watched(anime.getWatched())
                                .watching(anime.getWatching())
                                .wantWatch(anime.getWantWatch())
                                .dropped(anime.getDropped())
                                .rating(anime.getRating())
                                .votes(anime.getVotes())
                                .build()
                    ).collect(Collectors.toList());

        }catch(Exception ex){
            log.error(ex.getMessage());
            return Collections.singletonList(SearchResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .respMsg(ex.getMessage())
                    .build());
        }
        return searchResponses;
    }

    private Query QueryBuilder(SearchRequest searchRequest) {
        Query query = new Query();
        if(
                searchRequest.getPageSzie() != 0
                        && searchRequest.getPageNbr() != 0
        ){
            int skip = (searchRequest.getPageNbr() - 1) * searchRequest.getPageSzie();
            query.skip(skip).limit(searchRequest.getPageSzie());
        }else{
            query.skip(0).limit(50);
        }
        if (nonNull(searchRequest.getTags()) && !searchRequest.getTags().isEmpty()) {
            StringJoiner valueToSearch = new StringJoiner("|");
            searchRequest.getTags().forEach(valueToSearch::add);
            log.info(valueToSearch.toString());
            Pattern regExSearch = Pattern.compile(valueToSearch.toString(), Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("tags").regex(regExSearch));
        }

        if (nonNull(searchRequest.getTitle()) && StringUtils.hasLength(searchRequest.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(searchRequest.getTitle()));
        }

        if (nonNull(searchRequest.getDescription()) && StringUtils.hasLength(searchRequest.getDescription())) {
            query.addCriteria(Criteria.where("description").regex(searchRequest.getDescription()));
        }

        query.fields().exclude("_id");
        return query;
    }

}
