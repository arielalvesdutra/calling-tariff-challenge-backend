package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.State;

/**
 * Factory to create fake objects for testing.
 */
public class FakeStateFactory {

    public static State fakeState() {
        return new State()
                .setId(1L)
                .setAbbreviation("RS")
                .setName("Rio Grande do Sul");
    }
}
