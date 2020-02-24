package com.defascon.SpringAPI.dto;

import lombok.Data;

@Data
public class Graph {
    private String[] points;
    private String id;

    public Graph(String[] points, String id) {
        this.points = points;
        this.id = id;
    }
}
