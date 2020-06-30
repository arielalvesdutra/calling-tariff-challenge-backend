package dev.arielalvesdutra.calling_tariff.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;

public class DDDFactory {

    /**
     * DDD from state of Rio Grande do Sul
     *
     * @param state
     * @return
     */
    public static DDD ddd51(State state) {
        if (!state.getAbbreviation().equals("RS")) {
            throw new CallingTariffException("Invalid state parameter!");
        }

        return new DDD()
                .setCode(51)
                .setState(state);
    }

    /**
     * DDD from state of Rio Grande do Sul
     *
     * @param state
     * @return
     */
    public static DDD ddd11(State state) {
        if (!state.getAbbreviation().equals("SP")) {
            throw new CallingTariffException("Invalid state parameter!");
        }

        return new DDD()
                .setCode(11)
                .setState(state);
    }
}
