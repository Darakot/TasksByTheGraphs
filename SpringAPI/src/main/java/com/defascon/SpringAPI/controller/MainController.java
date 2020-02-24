package com.defascon.SpringAPI.controller;

import com.defascon.SpringAPI.dto.Graph;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *   Documentation is available here http://localhost:8080/swagger-ui.html
 *   I worked with the swagger for the first time. In the end, I realized that I had to write in yaml, it would be easier to read the code.But I did not have time to redo it.
 */

@RestController
@RequestMapping("/restAPI")
@Api(value = "Solution 3 tasks")
@Log4j
public class MainController {
    Map<String, Graph> graphs = new HashMap<>();


    @PostMapping("/saveGraph")
    @ApiOperation(value = "Saves the graph in HashMap.",
            notes = "Id is used as a key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "New graph successfully added"),
                    @ApiResponse(code = 409, message = "A graph with the same name already exists.")
            }
    )

    public void saveGraph(@RequestBody Graph graph, HttpServletResponse response){
        if (graphs.containsKey(graph.getId())) {
            log.info("A graph with the same name already exists. Id: " + graph.getId() + " code: 409");
            response.setStatus(409);
        } else {
            graphs.put(graph.getId(),graph);
            graphs.get(graph.getId()).formGraph();
            log.info("Graph with id "+ graph.getId() + " created code: 201");
            response.setStatus(201);
        }
    }

    @GetMapping("/getSortByNumberOfParents/{id}")
    @ApiOperation(value = "Shows how many parents a child has",
            notes = "Accepts the id of the graph that was previously generated in the saveGraph method",
            response = String.class
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Graph with the same id not found"),
                    @ApiResponse(code = 200, message = "Successfully")
            }
    )
    public String getSortByNumberOfParents(@PathVariable("id") String id, HttpServletResponse response){
        if (!graphs.containsKey(id)){
            response.setStatus(404);
            log.info("Graph with id " + id + " not found" + " code: 404");
        }else {
            response.setStatus(200);
            return graphs.get(id).getSortByNumberOfParents();
        }
        log.info("Method getSortByNumberOfParents failed. Id: " + id);
        return null;
    }

    @GetMapping("/getFromOneParent/{id}&{child1}&{child2}")
    @ApiOperation(value = "Search for a common parent",
            notes = "If a common parent is found, returns true, if not, it goes up the graph until it finds or the graph does not end",
            response = String.class
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Graph with the same id not found"),
                    @ApiResponse(code = 200, message = "Successfully")
            }
    )
    public String getFromOneParent(@PathVariable("id") String id,
                                   @PathVariable("child1") String child1,
                                   @PathVariable("child2") String child2,
                                    HttpServletResponse response){
        if (!graphs.containsKey(id)){
            response.setStatus(404);
            log.info("Graph with id " + id + " not found" + " code: 404");
        }else {
            response.setStatus(200);
            return String.valueOf(graphs.get(id).getFromOneParent(Integer.parseInt(child1),Integer.parseInt(child2)));
        }
        log.info("Method getFromOneParent failed. Id: " + id + ", child1: " + child1 + ", child2: " + child2);
        return null;
    }

    @ApiOperation(value = "Delete Graph")
    @DeleteMapping("/delete/{id}")
    public void deleteGragph(@PathVariable("id") String deleteId){
        graphs.remove(deleteId);
    }
}
