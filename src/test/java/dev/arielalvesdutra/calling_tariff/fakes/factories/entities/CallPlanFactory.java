package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;

import java.math.BigDecimal;

/**
 * Factory to create fake objects for testing.
 */
public class CallPlanFactory {

    /**
     * Create a fake CallPlan without id attribute fulfilled.
     *
     * @return
     */
    public static CallPlan validCallPlanWithoutId() {
        return new CallPlan()
                .setName("FaleMais 33")
                .setDescription("Fale mais com 33 minutos")
                .setMinutes(33)
                .setPrice(new BigDecimal("33.00"))
                .setVisible(true);
    }

    /**
     * Create a fake CallPlan.
     *
     * @return
     */
    public static CallPlan fakeCallPlan() {
        return new CallPlan()
                .setId(1L)
                .setName("FaleMais 30")
                .setDescription("Fale mais durante o mes")
                .setPrice(new BigDecimal("40.00"))
                .setMinutes(30)
                .setVisible(true);

    }
}
