package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.*;
import dev.arielalvesdutra.calling_tariff.factories.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ApplicationDataSeedService {

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DDDService dddService;

    @Autowired
    private CallTariffMapService callTariffMapService;

    @Autowired
    private CallPlanService callPlanService;

    @Autowired
    private UserService userService;

    @Value("${spring.profiles.active:#{'dev'}}")
    private String activeProfile;

    public void seed() {
        if (activeProfile.equals("test")) {
            return;
        }

        try {
            customPrint("Starting");
            systemConfigurationService.findInstallation();
            customPrint("System already installed");
        } catch (EntityNotFoundException e) {
            customPrint("Seeding essential data");

            customPrint("Creating runner user");
            User runner = userService.create(UserFactory.runnerUser());

            customPrint("Creating states");
            State rs = stateService.create(StateFactory.RS());
            State sp = stateService.create(StateFactory.SP());

            customPrint("Creating DDD");
            DDD ddd51 = dddService.create(DDDFactory.ddd51(rs));
            DDD ddd11 = dddService.create(DDDFactory.ddd11(sp));

            customPrint("Creating call tariff maps");
            CallTariffMap ddd11ToDdd51 = callTariffMapService.create(
                    CallTariffMapFactory.from11To51(ddd11, ddd51));
            CallTariffMap ddd11ToDdd11 = callTariffMapService.create(
                    CallTariffMapFactory.from11To11(ddd11));
            CallTariffMap ddd51ToDdd11 = callTariffMapService.create(
                    CallTariffMapFactory.from51To11(ddd51, ddd11));
            CallTariffMap ddd51ToDdd51 = callTariffMapService.create(
                    CallTariffMapFactory.from51To51(ddd51));

            customPrint("Creating call plans");
            CallPlan faleMais30 = callPlanService.create(CallPlanFactory.faleMais30());
            CallPlan faleMais60 = callPlanService.create(CallPlanFactory.faleMais60());
            CallPlan faleMais120 = callPlanService.create(CallPlanFactory.faleMais120());

            customPrint("Creating system configurations");
            SystemConfiguration defaultChargePerMinute =  systemConfigurationService.create(
                    SystemConfigurationFactory.defaultCallChargeConfiguration());
            SystemConfiguration defaultExceedingFee = systemConfigurationService.create(
                    SystemConfigurationFactory.exceedingPlanFeeConfiguration());
            SystemConfiguration installation = systemConfigurationService.create(
                    SystemConfigurationFactory.installationConfiguration());

            customPrint("Seeding completed");
        }
    }

    private void customPrint(Object message) {
        System.out.println("_______________> "+ message + "...");
    }
}
