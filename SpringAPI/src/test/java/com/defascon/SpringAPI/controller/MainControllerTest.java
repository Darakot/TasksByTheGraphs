package com.defascon.SpringAPI.controller;

import com.defascon.SpringAPI.dto.Graph;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class MainControllerTest {

    @BeforeAll
    static void createGraph() {
        final String URL_RESTAPI_CREATEGRAPH = "http://localhost:8080/restAPI/saveGraph";
        RestTemplate restTemplate = new RestTemplate();

        Graph graph1 = new Graph(new String[]{"10,3", "2,3", "3,6", "5,6", "5,17", "4,5", "4,8", "8,9",
                "6,33", "6,22", "17,22", "17,1"}, "g2");
        Graph graph2 = new Graph(new String[]{"9,1", "5,1", "1,12", "7,12", "7,14", "10,14", "10,19", "8,7",
                "6,10", "12,20", "14,20", "19,4"}, "g3");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Graph> requestBody = new HttpEntity<>(graph1, headers);
        restTemplate.postForObject(URL_RESTAPI_CREATEGRAPH, requestBody, Graph.class);

        requestBody = new HttpEntity<>(graph2, headers);
        restTemplate.postForObject(URL_RESTAPI_CREATEGRAPH, requestBody, Graph.class);
    }

    @Test
    void saveGraph() {
        Graph graph = new Graph(new String[]{"10,3","2,3","3,6","5,6","5,17","4,5","4,8","8,9",
            "6,33","6,22","17,22","17,1"}, "g");
        given().log().body()
                .contentType("application/json").body(graph)
                .when().post("/restAPI/saveGraph")
                .then().log().body()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    void sortByNumberOfParents() {

        given().pathParam("id", "g2")
                .when().get("/restAPI/getSortByNumberOfParents/{id}")
                .then().log().body().statusCode(200);

        given().pathParam("id", "g3")
                .when().get("/restAPI/getSortByNumberOfParents/{id}")
                .then().log().body().statusCode(200);

    }

    @Test
    void fromOneParent() {
        String response9_22 = get("/restAPI/getFromOneParent/g2&9&22").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertThat(response9_22).isEqualTo("true");

        String response33_1 = get("/restAPI/getFromOneParent/g2&33&1").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertThat(response33_1).isEqualTo("true");

        String response9_1 = get("/restAPI/getFromOneParent/g2&9&1").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertThat(response9_1).isEqualTo("true");


        String response3_8= get("/restAPI/getFromOneParent/g2&3&8").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertThat(response3_8).isEqualTo("false");
    }

    @AfterAll
    static void deleteGraph() {
        final String URL_RESTAPI_DELETEGRAPH = "http://localhost:8080/restAPI/delete/";
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(URL_RESTAPI_DELETEGRAPH + "g2");

        restTemplate.delete(URL_RESTAPI_DELETEGRAPH + "g3");

        restTemplate.delete(URL_RESTAPI_DELETEGRAPH + "g");
    }
}