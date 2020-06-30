package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.DDDFactory;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.StateFactory;
import dev.arielalvesdutra.calling_tariff.repositories.CallTariffMapRepository;
import dev.arielalvesdutra.calling_tariff.services.CallTariffMapService;
import dev.arielalvesdutra.calling_tariff.services.DDDService;
import dev.arielalvesdutra.calling_tariff.services.StateService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for CallTariffMapService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CallTariffMapServiceIT {

    @Autowired
    private CallTariffMapRepository callTariffMapRepository;
    @Autowired
    private CallTariffMapService callTariffMapService;
    @Autowired
    private DDDService dddService;
    @Autowired
    private StateService stateService;
    private DDD originDDD;
    private DDD destinationDDD;
    private State originState;
    private State destinationState;

    @Before
    public void setUp() {
        originState = stateService.create(StateFactory.RS());
        destinationState = stateService.create(StateFactory.SP());
        originDDD = dddService.create(DDDFactory.validAOriginDDDWithoutId(originState));
        destinationDDD = dddService.create(DDDFactory.validDestinationDDDWithoutId(destinationState));
    }

    @After
    public void tearDown() {
        callTariffMapRepository.deleteAll();
        dddService.deleteAll();
        stateService.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        CallTariffMap callTariffMapToCreate = buildAValidCallTariffMap();

        CallTariffMap createdCallTariffMap = callTariffMapService.create(callTariffMapToCreate);

        assertThat(createdCallTariffMap).isNotNull();
        assertThat(createdCallTariffMap.getDestination()).isNotNull();
    }

    @Test
    public void findAll_shouldWork() {
        CallTariffMap createdCallTariffMap = saveACallTariffMap();

        List<CallTariffMap> callTariffMapList = callTariffMapService.findAll();
        System.out.println(callTariffMapList);

        assertThat(callTariffMapList).isNotNull();
        assertThat(callTariffMapList).contains(createdCallTariffMap);
    }

    @Test
    public void findByUuid_shouldWork() {
        CallTariffMap createdCallTariffMap = saveACallTariffMap();

        CallTariffMap fetchedMap = callTariffMapService.findByUuid(createdCallTariffMap.getUuid());

        assertThat(fetchedMap).isNotNull();
        assertThat(fetchedMap.getUuid()).isEqualTo(createdCallTariffMap.getUuid());
        assertThat(fetchedMap.getPricePerMinute()).isEqualTo(createdCallTariffMap.getPricePerMinute());
        assertThat(fetchedMap.getDestination()).isNotNull();
        assertThat(fetchedMap.getOrigin()).isNotNull();
    }

    @Test
    public void findByOriginCodeAndDestinationCode_shouldWork() {
        CallTariffMap createdCallTariffMap = saveACallTariffMap();

        CallTariffMap fetchedMap = callTariffMapService.findByOriginCodeAndDestinationCode(
                originDDD.getCode(), destinationDDD.getCode());

        assertThat(fetchedMap).isNotNull();
        assertThat(fetchedMap.getUuid()).isEqualTo(createdCallTariffMap.getUuid());
        assertThat(fetchedMap.getPricePerMinute()).isEqualTo(createdCallTariffMap.getPricePerMinute());
        assertThat(fetchedMap.getOrigin()).isEqualTo(createdCallTariffMap.getOrigin());
        assertThat(fetchedMap.getDestination()).isEqualTo(createdCallTariffMap.getDestination());
    }

    @Test
    public void deleteByUuid_shouldWork() {
        CallTariffMap createdCallTariffMap = saveACallTariffMap();

        callTariffMapService.deleteByUuid(createdCallTariffMap.getUuid());
        CallTariffMap fetchedMap = callTariffMapRepository.findByUuid(createdCallTariffMap.getUuid());

        assertThat(fetchedMap).isNull();
    }

    @Test
    public void deleteByOriginCodeAndDestinationCode_shouldWork() {
        saveACallTariffMap();
        Integer originCode = originDDD.getCode();
        Integer destinationCode = destinationDDD.getCode();

        callTariffMapService.deleteByOriginCodeAndDestinationCode(originCode, destinationCode);
        CallTariffMap fetchedMap = callTariffMapRepository.findByOrigin_CodeAndDestination_Code(originCode, destinationCode);

        assertThat(fetchedMap).isNull();
    }

    private CallTariffMap saveACallTariffMap() {
        return callTariffMapRepository.save(buildAValidCallTariffMap());
    }

    private CallTariffMap buildAValidCallTariffMap() {
        return new CallTariffMap()
                .setOrigin(originDDD)
                .setDestination(destinationDDD)
                .setPricePerMinute(new BigDecimal("2.20"));
    }

    private DDD buildAOriginDDD(State state) {
        return new DDD()
                .setCode(51)
                .setState(state);
    }

    private DDD buildADestinationDDD(State state) {
        return new DDD()
                .setCode(11)
                .setState(state);
    }
}
