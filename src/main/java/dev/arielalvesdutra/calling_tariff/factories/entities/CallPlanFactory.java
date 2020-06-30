package dev.arielalvesdutra.calling_tariff.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;

import java.math.BigDecimal;

public class CallPlanFactory {

    /**
     * FaleMais30
     *
     * @return
     */
    public static CallPlan faleMais30() {
        int value = 30;
        return new CallPlan()
                .setName("FaleMais " + value)
                .setDescription("Fale mais com " + value + " minutos")
                .setMinutes(value)
                .setPrice(new BigDecimal("29.99"))
                .setVisible(true);
    }

    /**
     * FaleMais60
     *
     * @return
     */
    public static CallPlan faleMais60() {
        int value = 60;
        return new CallPlan()
                .setName("FaleMais " + value)
                .setDescription("Fale mais com " + value + " minutos")
                .setMinutes(value)
                .setPrice(new BigDecimal("59.99"))
                .setVisible(true);
    }

    /**
     * FaleMais120
     *
     * @return
     */
    public static CallPlan faleMais120() {
        int value = 120;
        return new CallPlan()
                .setName("FaleMais " + value)
                .setDescription("Fale mais com " + value + " minutos")
                .setMinutes(value)
                .setPrice(new BigDecimal("119.99"))
                .setVisible(true);
    }
}
