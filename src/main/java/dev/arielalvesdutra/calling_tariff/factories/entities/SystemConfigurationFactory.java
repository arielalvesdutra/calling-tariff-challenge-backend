package dev.arielalvesdutra.calling_tariff.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.SystemConfiguration;

/**
 * Factories of SystemConfiguration objects.
 */
public class SystemConfigurationFactory {

    /**
     * Configuration for the default call charge
     * that should be used when there is no tariff map
     * of origin and destination of the cost of a call minute.
     *
     * @return SystemConfiguration of default charge per call minute
     */
    public static SystemConfiguration defaultCallChargeConfiguration() {
        return new SystemConfiguration()
                .setValue("10.00")
                .setName("Default call minute charge")
                .setDescription("Default call minute charge is used when " +
                        "there is no map for tariff call")
                .setCode("default_call_minute_charge");
    }

    /**
     * This is the percentage that must be applied to be used in a charge
     * after the monthly minutes of a plan is exceeded
     *
     * @return SystemConfiguration
     */
    public static SystemConfiguration exceedingPlanFeeConfiguration() {
        return new SystemConfiguration()
                .setValue("10")
                .setName("Fee for having call plan minutes exceeded")
                .setDescription("This percentage is used " +
                        "in a charge after the monthly minutes of a plan is exceeded.")
                .setCode("exceeding_call_plan_fee");
    }

    /**
     * This configuration is created after the system installation.
     *
     * @return
     */
    public static SystemConfiguration installationConfiguration() {
        return new SystemConfiguration()
                .setValue("installed")
                .setName("System installation")
                .setDescription("This configuration is created after the system installation")
                .setCode("installation");
    }
}
