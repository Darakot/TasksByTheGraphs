package com.defascon.SpringAPI.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class Graph {
    private List<String> points = new ArrayList<>();
    private String id;

    private GraphService graphService;

    public void formGraph(){
        graphService = new GraphService(points);
    }

    public String getSortByNumberOfParents (){
        return graphService.sortByNumberOfParents();
    }

    public boolean getFromOneParent(int child1, int child2){
        return graphService.fromOneParent(child1,child2);
    }
}
