package org.bits.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {

    private String _id;
    private String title;
    private List<String> tags;
    private String description;
}
