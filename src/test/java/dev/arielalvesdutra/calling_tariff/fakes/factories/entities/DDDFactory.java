package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.State;

/**
 * Factory to create fake objects for testing.
 */
public class DDDFactory {

    /**
     * @param state
     * @return
     */
    public static DDD validAOriginDDDWithoutId(State state) {
        return new DDD()
                .setCode(51)
                .setState(state);
    }

    /**
     * @param state
     * @return
     */
    public static DDD validDestinationDDDWithoutId(State state) {
        return new DDD()
                .setCode(11)
                .setState(state);
    }
}
