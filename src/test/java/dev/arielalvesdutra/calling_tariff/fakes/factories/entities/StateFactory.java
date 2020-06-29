package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.State;

/**
 * Factories of State entity.
 */
public class StateFactory {
    /**
     * Return one valid State object, but without id attribute fulfilled.
     *
     * @return
     */
    public static State validStateWithoutId() {
        return RS();
    }

    /**
     * State: Rio Grande do Sul
     *
     * @return
     */
    public static State RS() {
        return new State()
                .setName("Rio Grande do Sul")
                .setAbbreviation("RS");
    }

    /**
     * State: São Paulo
     *
     * @return
     */
    public static State SP() {
        return new State()
                .setName("São Paulo")
                .setAbbreviation("SP");
    }

    /**
     *
     * @return
     */
    public static State fakeState() {
        return new State()
                .setId(1L)
                .setAbbreviation("RS")
                .setName("Rio Grande do Sul");
    }

}
