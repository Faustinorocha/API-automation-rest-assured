package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

public class UserTestsInvalid {


    public static RequestSpecification request;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @BeforeEach
    void setRequest() {
        request = given().config(
                        config()
                                .logConfig(
                                        logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .header("api-key", "special-key")
                .contentType(ContentType.JSON);

    }

    @Test
    public void CreateNewUser_WithInvalidBody_ReturnBadRequest() {

        Response response = request
                .body("teste")
                .when()
                .post("/user")
                .then()
                .extract().response();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertTrue(response.getBody().asPrettyString().contains("unknown"));
        Assertions.assertEquals(3, response.body().jsonPath().getMap("$").size());

    }
}
