package dev.arielalvesdutra.calling_tariff.helpers;

import java.math.BigDecimal;

/**
 * Helper for common methods with BigDecimal.
 */
public class BigDecimalHelper {

    /**
     * Return a percentage of the value.
     *
     * @param value
     * @param percentage
     * @return
     */
    public static BigDecimal percentage(BigDecimal value, BigDecimal percentage) {
        return value.multiply(percentage.divide(new BigDecimal(100)));
    }
}
