package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestClient {

    public static Response get(String endpoint) {
        return given()
                .when()
                .get(endpoint);
    }

    public static Response post(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response delete(String endpoint) {
        return given()
                .when()
                .delete(endpoint);
    }
}
