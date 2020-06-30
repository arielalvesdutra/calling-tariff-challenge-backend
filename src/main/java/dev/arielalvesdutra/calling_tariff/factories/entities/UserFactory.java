package dev.arielalvesdutra.calling_tariff.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.User;

public class UserFactory {

    /**
     * User to execute system operations.
     *
     * @return
     */
    public static User runnerUser() {
        return new User()
                .setActive(true)
                .setPassword("h2BJ71Bp6TNrTgyO")
                .setEmail("runner@teste.com")
                .setName("Runner");
    }
}
