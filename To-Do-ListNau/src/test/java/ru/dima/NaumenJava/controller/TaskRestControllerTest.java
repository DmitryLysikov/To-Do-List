package ru.dima.naumenjava.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testFindByStatusAndPriority_validRequest_returns200() {
        given()
                .baseUri("http://localhost")
                .port(port)
                .accept(ContentType.JSON)
                .queryParam("status", "NEW")
                .queryParam("priority", 1L)
                .when()
                .get("/rest/tasks")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFindByStatusAndPriority_noParams_returns200() {
        when()
                .get("/rest/tasks")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFindTasksByUserId_validUserId_returns200() {
        long validUserId = 1L;
        when()
                .get("/rest/find-tasks-by-userid/{userId}", validUserId)
                .then()
                .statusCode(200);
    }

    @Test
    void testFindTasksByUserId_unauthenticated_redirectsToLogin() {
        given()
                .pathParam("userId", 1)
                .when()
                .get("/rest/find-tasks-by-userid/{userId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(containsString("Вход в систему"));
    }
}

