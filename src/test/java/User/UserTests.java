package User;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {
    public static User user;

    public static Faker faker;

    public static RequestSpecification request;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        faker = new Faker();

        user = new User(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                faker.internet().password(8, 10),
                faker.phoneNumber().toString()
                );
    }

    @BeforeEach
    void setRequest() {
        request = given().config(
                RestAssured.config()
                        .logConfig(
                                logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .header("api-key", "special-key")
                .contentType(ContentType.JSON);

    }

    @Test
    @Order(1)
    public void CreateNewUser_WithValidData_ReturnOk() {

        request
                .body(user)
                .when()
                .post("/user")
                .then()
                .assertThat().statusCode(200).and()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", isA(String.class))
                .body("size()", equalTo(3));

    }
    @Test
    @Order(2)
    public void GetLogin_ValidUser_ReturnOk() {

        request
                .param("username", "nedafsdfasdto123@qa.com")
                .param("password", "Senha@1235555")
                .when()
                .get("/user/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000L))
                .and().body(matchesJsonSchemaInClasspath("loginResponseSchema.json"));


    }
    @Test
    @Order(3)
    public void GetUserByUsername_userIsValid_ReturnOk() {

        request
                .when()
                .get("/user/" + user.getUsername())
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000l))
                .and().body("firstName", equalTo(user.getFirstName()))
                .and().body(matchesJsonSchemaInClasspath("getUserSchema.json"));
    }

    @Test
    @Order(4)
    public void DeleteUser_UserExists_ReturnOk() {

        request
                .when()
                .delete("/user/" + user.getUsername())
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000l))
                .and().body(matchesJsonSchemaInClasspath("deleteUserSchema.json"))
                .log();
    }
}
