package services;

import client.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PetService {

    public static Response createPet(Object petbody) {

        return RestAssured.post("/pet", petbody);
    }

    public static Response GetPetById(long petId) {

        return RestClient.get("/pet/" + petId);
    }

    public static Response deletePet(long petId) {

        return RestClient.delete("/pet/" + petId);
    }
}
