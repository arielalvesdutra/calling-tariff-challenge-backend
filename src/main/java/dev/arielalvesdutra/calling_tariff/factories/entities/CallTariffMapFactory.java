package dev.arielalvesdutra.calling_tariff.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;

import java.math.BigDecimal;

public class CallTariffMapFactory {

    /**
     * DDD 11 to DDD 51.
     *
     * @param originDDD
     * @param destinationDDD
     * @return
     */
    public static CallTariffMap from11To51(
            DDD originDDD,
            DDD destinationDDD) {

        if (originDDD.getCode() != 11) {
            throw new CallingTariffException("DDD origin must be 11");
        }

        if (destinationDDD.getCode() != 51) {
            throw new CallingTariffException("DDD destination must be 51");
        }

        return new CallTariffMap()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setPricePerMinute(new BigDecimal("2.60"));
    }

    /**
     * DDD 51 to DDD 51.
     *
     * @param ddd
     * @return
     */
    public static CallTariffMap from51To51(
            DDD ddd) {

        if (ddd.getCode() != 51) {
            throw new CallingTariffException("DDD code must be 51");
        }

        return new CallTariffMap()
                .setOrigin(ddd)
                .setDestination(ddd)
                .setPricePerMinute(new BigDecimal("1.60"));
    }

    /**
     * DDD 11 to DDD 11.
     *
     * @param ddd
     * @return
     */
    public static CallTariffMap from11To11(
            DDD ddd) {

        if (ddd.getCode() != 11) {
            throw new CallingTariffException("DDD code must be 11");
        }

        return new CallTariffMap()
                .setOrigin(ddd)
                .setDestination(ddd)
                .setPricePerMinute(new BigDecimal("2.10"));
    }

    /**
     * DDD 51 to DDD 11.
     *
     * @param originDDD
     * @param destinationDDD
     * @return
     */
    public static CallTariffMap from51To11(
            DDD originDDD,
            DDD destinationDDD) {

        if (originDDD.getCode() != 51) {
            throw new CallingTariffException("DDD origin must be 51");
        }

        if (destinationDDD.getCode() != 11) {
            throw new CallingTariffException("DDD destination must be 11");
        }

        return new CallTariffMap()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setPricePerMinute(new BigDecimal("2.40"));
    }
}
