package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.entities.DDD;

import java.math.BigDecimal;

/**
 * Factory to create fake objects for testing.
 */
public class CallTariffMapFactory {

    /**
     * Build a valid CallTariffMap object.
     *
     * id attribute not fulfilled.
     *
     * @return
     */
    public static CallTariffMap validCallTariffMapWithoutId(
            DDD originDDD,
            DDD destinationDDD) {

        return new CallTariffMap()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setPricePerMinute(new BigDecimal("3.20"));
    }
}
