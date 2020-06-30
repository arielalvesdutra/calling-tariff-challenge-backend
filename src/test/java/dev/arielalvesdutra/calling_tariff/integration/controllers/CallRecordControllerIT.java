package dev.arielalvesdutra.calling_tariff.integration.controllers;

import dev.arielalvesdutra.calling_tariff.controllers.dtos.CalculateCallRecordRequestDTO;
import dev.arielalvesdutra.calling_tariff.controllers.dtos.CallRecordDTO;
import dev.arielalvesdutra.calling_tariff.entities.*;
import dev.arielalvesdutra.calling_tariff.factories.entities.UserFactory;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.CallPlanFactory;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.CallTariffMapFactory;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.DDDFactory;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.StateFactory;
import dev.arielalvesdutra.calling_tariff.services.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static dev.arielalvesdutra.calling_tariff.factories.entities.SystemConfigurationFactory.defaultCallChargeConfiguration;
import static dev.arielalvesdutra.calling_tariff.factories.entities.SystemConfigurationFactory.exceedingPlanFeeConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Integration Tests for CallRecordController class.
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CallRecordControllerIT {

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
    private TestRestTemplate restTemplate;

    private User client;
    private SystemConfiguration defaultCallChargeConfiguration;
    private SystemConfiguration exceedingPlanFeeConfiguration;
    private DDD originDDD;
    private DDD destinationDDD;
    private State originState;
    private State destinationState;
    private CallTariffMap callTariffMap;
    private CallPlan callPlan;

    private String BASE_CALLS_CALCULATION_URL = "/call-records/calculate";

    @Before
    public void setUp() {
        defaultCallChargeConfiguration = systemConfigurationService.create(defaultCallChargeConfiguration());
        exceedingPlanFeeConfiguration = systemConfigurationService.create(exceedingPlanFeeConfiguration());

        client = userService.create(UserFactory.runnerUser());
        originState = stateService.create(StateFactory.RS());
        destinationState = stateService.create(StateFactory.SP());
        originDDD = dddService.create(DDDFactory.validAOriginDDDWithoutId(originState));
        destinationDDD = dddService.create(DDDFactory.validDestinationDDDWithoutId(destinationState));
        callTariffMap = callTariffMapService.create(CallTariffMapFactory.validCallTariffMapWithoutId(
                originDDD, destinationDDD));
        callPlan = callPlanService.create(CallPlanFactory.validCallPlanWithoutId());
    }

    @After
    public void tearDown() {
        callRecordService.deleteAll();
        callTariffMapService.deleteAll();
        dddService.deleteAll();
        stateService.deleteAll();
        userService.deleteAll();
        systemConfigurationService.deleteAll();
        callPlanService.deleteAll();
    }

    @Test
    public void calculate_clientWithoutPlan_withCallTariffMap_shouldWork() {
         CalculateCallRecordRequestDTO dto = new CalculateCallRecordRequestDTO()
                 .setOriginCode(originDDD.getCode())
                 .setDestinationCode(destinationDDD.getCode())
                 .setDuration(10);
        BigDecimal expectedPrice = new BigDecimal(dto.getDuration())
                .multiply(callTariffMap.getPricePerMinute());

        ResponseEntity<CallRecordDTO> response = restTemplate.postForEntity(
                BASE_CALLS_CALCULATION_URL,
                dto,
                CallRecordDTO.class);
        CallRecordDTO responseDto = response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getMinutes()).isEqualTo(dto.getDuration());
        assertThat(responseDto.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void calculate_clientWithPlan_withCallTariffMap_shouldWork() {
        CalculateCallRecordRequestDTO dto = new CalculateCallRecordRequestDTO()
                .setCallPlanUuid(callPlan.getUuid())
                .setOriginCode(originDDD.getCode())
                .setDestinationCode(destinationDDD.getCode())
                .setDuration(10);
        BigDecimal expectedPrice = BigDecimal.ZERO;

        ResponseEntity<CallRecordDTO> response = restTemplate.postForEntity(
                BASE_CALLS_CALCULATION_URL,
                dto,
                CallRecordDTO.class);
        CallRecordDTO responseDto = response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getMinutes()).isEqualTo(dto.getDuration());
        assertThat(responseDto.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void calculate_clientWithPlan_withCallTariffMap_exceedingCallPlanMinutes_shouldWork() {
        CalculateCallRecordRequestDTO dto = new CalculateCallRecordRequestDTO()
                .setCallPlanUuid(callPlan.getUuid())
                .setOriginCode(originDDD.getCode())
                .setDestinationCode(destinationDDD.getCode())
                .setDuration(100);
        BigDecimal expectedPrice = new BigDecimal("235.840");

        ResponseEntity<CallRecordDTO> response = restTemplate.postForEntity(
                BASE_CALLS_CALCULATION_URL,
                dto,
                CallRecordDTO.class);
        CallRecordDTO responseDto = response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getMinutes()).isEqualTo(dto.getDuration());
        assertThat(responseDto.getPrice()).isEqualTo(expectedPrice);
    }
}
