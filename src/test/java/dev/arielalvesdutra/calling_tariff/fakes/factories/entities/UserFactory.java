package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Factory to create fake objects for testing.
 */
public class UserFactory {

    /**
     * Create a valid User object to be used in tests
     *
     * id attribute not fulfilled.
     *
     * @return
     */
    public static User validUserWithoutId() {
        return new User()
                .setName("Geralt of Rivia")
                .setEmail("test@test.com")
                .setPassword("132456")
                .setActive(true);
    }

    /**
     *
     * @return
     */
    public static User fakeUser() {
        return new User()
                .setId(1L)
                .setName("Geralt of Rivia")
                .setEmail("test@test.com")
                .setPassword("132456")
                .setActive(true);
    }

    /**
     *
     * @param user
     * @return
     */
    public static Set<User> fakeUserSet(User user) {
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        return userSet;
    }
}
