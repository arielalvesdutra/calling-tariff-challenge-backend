package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Factory to create fake objects for testing.
 */
public class FakeUserFactory {

    public static User fakeUser() {
        return new User()
                .setId(1L)
                .setName("Geralt of Rivia")
                .setEmail("test@test.com")
                .setPassword("132456")
                .setActive(true);
    }

    public static Set<User> fakeUserSet(User user) {
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        return userSet;
    }
}
