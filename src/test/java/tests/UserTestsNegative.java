package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserService;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToObject;

public class UserTestsNegative {


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

        Response response = UserService.createUser("invalidBody");

        response
                .then()
                .statusCode(400)
                .body("type", equalTo("unknown"))
                .body("size()", equalTo(3));

    }

    @Test
    public void Login_WithInvalidUser_ReturnError() {
        // Known issue:
        // Swagger Petstore login endpoint does not validate credentials.
        // Even invalid username/password returns 200.
        Response response = UserService.login(
                "aaa123.com",
                "Senhainvalida"
        );
        response
                .then()
                .statusCode(400)
                .body("type", equalTo("Invalid username/password supplied"));

    }

    @Test
    public void GetUser_WithInvalidUsername_ReturnNotFound() {

        Response response = UserService.getUser("userDoesNotExist");

        response
                .then()
                .statusCode(404)
                .body("message", equalTo("User not found"));


    }
}
