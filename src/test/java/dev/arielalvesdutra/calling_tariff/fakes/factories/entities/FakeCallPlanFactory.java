package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;

import java.math.BigDecimal;

/**
 * Factory to create fake objects for testing.
 */
public class FakeCallPlanFactory {

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
