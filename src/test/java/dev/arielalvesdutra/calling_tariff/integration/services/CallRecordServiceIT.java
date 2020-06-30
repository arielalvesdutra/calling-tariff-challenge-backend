package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.*;
import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.*;
import dev.arielalvesdutra.calling_tariff.repositories.CallRecordRepository;
import dev.arielalvesdutra.calling_tariff.services.*;
import dev.arielalvesdutra.calling_tariff.services.dtos.CalculateCallRecordDTO;
import dev.arielalvesdutra.calling_tariff.services.dtos.UpdateUserCallPlanDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for CallRecordService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CallRecordServiceIT {

    @Autowired
    private CallPlanService callPlanService;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired
    private CallTariffMapService callTariffMapService;
    @Autowired
    private DDDService dddService;
    @Autowired
    private StateService stateService;
    @Autowired
    private SystemConfigurationService systemConfigurationService;
    @Autowired
    private UserService userService;

    @Autowired
    private CallRecordRepository callRecordRepository;

    private User client;
    private SystemConfiguration defaultCallChargeConfiguration;
    private SystemConfiguration exceedingPlanFeeConfiguration;
    private DDD originDDD;
    private DDD destinationDDD;
    private State originState;
    private State destinationState;
    private CallTariffMap callTariffMap;
    private CallPlan callPlan;

    @Before
    public void setUp() {
        defaultCallChargeConfiguration = systemConfigurationService.create(buildDefaultCallChargeConfiguration());
        exceedingPlanFeeConfiguration = systemConfigurationService.create(buildExceedingPlanFeeConfiguration());

        client = userService.create(buildAValidClient());
        originState = stateService.create(StateFactory.RS());
        destinationState = stateService.create(StateFactory.SP());
        originDDD = dddService.create(DDDFactory.validAOriginDDDWithoutId(originState));
        destinationDDD = dddService.create(DDDFactory.validDestinationDDDWithoutId(destinationState));
        callTariffMap = callTariffMapService.create(buildAValidCallTariffMap(
                originDDD, destinationDDD));
        callPlan = callPlanService.create(buildAValidCallPlan());
    }

    @After
    public void tearDown() {
        callRecordRepository.deleteAll();
        callTariffMapService.deleteAll();
        dddService.deleteAll();
        stateService.deleteAll();
        userService.deleteAll();
        systemConfigurationService.deleteAll();
        callPlanService.deleteAll();
    }

    @Test
    public void calculate_withoutOriginAndDestination_withoutDefaultCallChargeConfiguration_shouldThrowAnException() {
        try {
            systemConfigurationService.deleteAll();
            CalculateCallRecordDTO dto = new CalculateCallRecordDTO()
                    .setClient(client)
                    .setDuration(10);

            callRecordService.calculate(dto);

        } catch (CallingTariffException e) {
            assertThat(e.getMessage()).isEqualTo("Default call minute charge must be configured!");
        }
    }

    @Test
    public void calculate_clientWithoutPlan_withoutCallTariffMap_shouldWork() {
        CalculateCallRecordDTO dto = new CalculateCallRecordDTO()
                .setClient(client)
                .setDuration(10);

        CallRecord createdCallRecord = callRecordService.calculate(dto);
        BigDecimal expectedPrice = new BigDecimal(dto.getDuration())
                .multiply(new BigDecimal(defaultCallChargeConfiguration.getValue()));

        assertThat(createdCallRecord).isNotNull();
        assertThat(createdCallRecord.getId()).isNull();
        assertThat(createdCallRecord.getClient()).isEqualTo(client);
        assertThat(createdCallRecord.getMinutes()).isEqualTo(10);
        assertThat(createdCallRecord.getPrice()).isEqualTo(expectedPrice);
        assertThat(createdCallRecord.getCreatedAt()).isNotNull();
    }

    @Test
    public void calculate_clientWithoutPlan_withCallTariffMap_shouldWork() {
        CalculateCallRecordDTO dto = new CalculateCallRecordDTO()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setClient(client)
                .setDuration(10);

        CallRecord createdCallRecord = callRecordService.calculate(dto);
        BigDecimal expectedPrice = new BigDecimal(dto.getDuration())
                .multiply(callTariffMap.getPricePerMinute());

        assertThat(createdCallRecord).isNotNull();
        assertThat(createdCallRecord.getId()).isNull();
        assertThat(createdCallRecord.getClient()).isEqualTo(client);
        assertThat(createdCallRecord.getMinutes()).isEqualTo(10);
        assertThat(createdCallRecord.getPrice()).isEqualTo(expectedPrice);
        assertThat(createdCallRecord.getCreatedAt()).isNotNull();
    }

    @Test
    public void calculate_clientWithPlan_withCallTariffMap_shouldWork() {
        UpdateUserCallPlanDTO updateUserCallPlanDTO = new UpdateUserCallPlanDTO()
                .setUserUuid(client.getUuid())
                .setCallPlanUuid(callPlan.getUuid());
        userService.updateUserCallPlan(updateUserCallPlanDTO);
        client = userService.findById(client.getId());
        CalculateCallRecordDTO dto = new CalculateCallRecordDTO()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setClient(client)
                .setDuration(20);

        CallRecord createdCallRecord = callRecordService.calculate(dto);
        BigDecimal expectedPrice = BigDecimal.ZERO;

        assertThat(createdCallRecord).isNotNull();
        assertThat(createdCallRecord.getId()).isNull();
        assertThat(createdCallRecord.getClient()).isEqualTo(client);
        assertThat(createdCallRecord.getMinutes()).isEqualTo(20);
        assertThat(createdCallRecord.getPrice()).isEqualTo(expectedPrice);
        assertThat(createdCallRecord.getCreatedAt()).isNotNull();
    }

    @Test
    public void calculate_clientWithPlan_withCallTariffMap_exceedingCallPlanMinutes_shouldWork() {
        UpdateUserCallPlanDTO updateUserCallPlanDTO = new UpdateUserCallPlanDTO()
                .setUserUuid(client.getUuid())
                .setCallPlanUuid(callPlan.getUuid());
        userService.updateUserCallPlan(updateUserCallPlanDTO);
        client = userService.findById(client.getId());
        Integer duration = 100;
        CalculateCallRecordDTO dto = new CalculateCallRecordDTO()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setClient(client)
                .setDuration(duration);

        CallRecord createdCallRecord = callRecordService.calculate(dto);
        BigDecimal expectedPrice = new BigDecimal("235.840");

        assertThat(createdCallRecord).isNotNull();
        assertThat(createdCallRecord.getId()).isNull();
        assertThat(createdCallRecord.getClient()).isEqualTo(client);
        assertThat(createdCallRecord.getMinutes()).isEqualTo(duration);
        assertThat(createdCallRecord.getPrice()).isEqualTo(expectedPrice);
        assertThat(createdCallRecord.getCreatedAt()).isNotNull();
    }

    /**
     * Configuration for the default call charge
     * that should be used when there is no tariff map
     * of origin and destination of the cost of a call minute.
     *
     * @return
     */
    private SystemConfiguration buildDefaultCallChargeConfiguration() {
        return new SystemConfiguration()
                .setValue("10.00")
                .setName("Default call minute charge")
                .setDescription("Default call minute charge is used when " +
                        "there is no map for tariff call")
                .setCode("default_call_minute_charge");
    }

    /**
     * This is the percentage that must be applied to be used in a charge
     * after the monthly minutes of a plan is exceeded.
     *
     * @return
     */
    private SystemConfiguration buildExceedingPlanFeeConfiguration() {
        return new SystemConfiguration()
                .setValue("10")
                .setName("Fee for having call plan minutes exceeded")
                .setDescription("This percentage is used " +
                        "in a charge after the monthly minutes of a plan is exceeded.")
                .setCode("exceeding_call_plan_fee");
    }

    private User buildAValidClient() {
        return UserFactory.validUserWithoutId();
    }

    private CallTariffMap buildAValidCallTariffMap(DDD originDDD, DDD destinationDDD) {
        return CallTariffMapFactory.validCallTariffMapWithoutId(
                originDDD, destinationDDD);
    }

    private CallPlan buildAValidCallPlan() {
        return CallPlanFactory.validCallPlanWithoutId();
    }
}
