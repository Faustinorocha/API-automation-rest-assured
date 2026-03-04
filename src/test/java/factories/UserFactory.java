package factories;

import com.github.javafaker.Faker;
import models.User;
import services.UserService;

public class UserFactory {

    private static final Faker faker = new Faker();

    public static User createUser() {

        User user = new User();

        user.setId(0);
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password(6, 8));
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setUserStatus(0);

        return user;
    }
}
