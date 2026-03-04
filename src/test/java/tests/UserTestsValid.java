package tests;

import factories.UserFactory;
import models.User;
import base.BaseTest;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import services.UserService;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;


public class UserTestsValid extends BaseTest {


    @Test
    public void CreateNewUser_WithValidData_ReturnOk() {

        User user = UserFactory.createUser();

        UserService.createUser(user);
        Response response = UserService.createUser(user);

        response
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", isA(String.class))
                .body("size()", equalTo(3));

    }

    @Test
    public void GetLogin_ValidUser_ReturnOk() {

        User user = UserFactory.createUser();

        UserService.createUser(user);

        Response response = UserService.login(
                user.getUsername(),
                user.getPassword()
        );

        response
                .then()
                .statusCode(200)
                .and().time(lessThan(2000L))
                .and().body(matchesJsonSchemaInClasspath("loginResponseSchema.json"));

    }

    @Test
    public void GetUserByUsername_userIsValid_ReturnOk() {

        User user = UserFactory.createUser();

        UserService.createUser(user);

        Response response = UserService.getUser(user.getUsername());

        response
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000l))
                .and().body("firstName", equalTo(user.getFirstName()))
                .and().body(matchesJsonSchemaInClasspath("getUserSchema.json"));
    }

    @Test
    public void DeleteUser_UserExists_ReturnOk() {

        User user = UserFactory.createUser();

        UserService.createUser(user);

        Response response = UserService.deleteUser(user.getUsername());

        response
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000l))
                .and().body(matchesJsonSchemaInClasspath("deleteUserSchema.json"))
                .log();
    }

}
