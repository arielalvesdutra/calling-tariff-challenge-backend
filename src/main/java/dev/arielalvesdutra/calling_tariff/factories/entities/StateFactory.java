package dev.arielalvesdutra.calling_tariff.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.State;

public class StateFactory {
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
}
