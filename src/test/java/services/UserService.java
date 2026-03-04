package services;

import client.RestClient;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class UserService {

    public static Response login(String username, String password) {

        return RestClient.get("/user/login?username=" + username + "&password=" + password);
    }

    public static Response createUser(Object body) {

        return RestClient.post("/user", body);
    }

    public static Response getUser(String username) {

        return RestClient.get("/user/" + username);
    }

    public static Response deleteUser(String username) {

        return RestClient.delete("/user/" + username);
    }

}
