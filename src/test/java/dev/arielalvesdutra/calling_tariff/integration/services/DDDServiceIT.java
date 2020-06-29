package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.StateFactory;
import dev.arielalvesdutra.calling_tariff.repositories.DDDRepository;
import dev.arielalvesdutra.calling_tariff.services.DDDService;
import dev.arielalvesdutra.calling_tariff.services.StateService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Test for DDDService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
public class DDDServiceIT {

    @Autowired
    private DDDService dddService;

    @Autowired
    private DDDRepository dddRepository;

    @Autowired
    private StateService stateService;

    private State state;

    @Before
    public void setUp() {
        state = stateService.create(StateFactory.validStateWithoutId());
    }

    @After
    public void tearDown() {
        dddRepository.deleteAll();
        stateService.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        DDD dddToCreate = this.buildAValidDDD();

        DDD createdDDD = dddService.create(dddToCreate);
        DDD fetchedDDD = dddRepository.findById(createdDDD.getId()).get();

        assertThat(createdDDD).isNotNull();
        assertThat(createdDDD.getId()).isNotNull();
        assertThat(fetchedDDD.getCode()).isEqualTo(dddToCreate.getCode());
        assertThat(fetchedDDD.getState()).isEqualTo(dddToCreate.getState());
    }

    @Test
    public void findAll_shouldWork() {
        DDD createdDDD = saveADDD();

        List<DDD> dddList = dddService.findAll();

        assertThat(dddList).isNotNull();
        assertThat(dddList).contains(createdDDD);
    }

    @Test
    public void findById_shouldWork() {
        DDD createdDDD = saveADDD();

        DDD fetchedDDD = dddService.findById(createdDDD.getId());

        assertThat(fetchedDDD).isNotNull();
        assertThat(fetchedDDD.getId()).isEqualTo(createdDDD.getId());
        assertThat(fetchedDDD.getCode()).isEqualTo(createdDDD.getCode());
        assertThat(fetchedDDD.getState()).isEqualTo(createdDDD.getState());
    }

    @Test
    public void findFirstByCode_shouldWork() {
        DDD createdDDD = saveADDD();

        DDD fetchedDDD = dddService.findFirstByCode(createdDDD.getCode());

        assertThat(fetchedDDD).isNotNull();
        assertThat(fetchedDDD.getId()).isEqualTo(createdDDD.getId());
        assertThat(fetchedDDD.getCode()).isEqualTo(createdDDD.getCode());
        assertThat(fetchedDDD.getState()).isEqualTo(createdDDD.getState());
    }

    @Test
    public void deleteBy_shouldWork() {
        DDD createdDDD = saveADDD();

        dddService.deleteById(createdDDD.getId());
        Optional<DDD> dddOption = dddRepository.findById(createdDDD.getId());

        assertThat(dddOption.isPresent()).isFalse();
    }

    public DDD buildAValidDDD() {
        return new DDD()
                .setState(state)
                .setCode(51);
    }

    public DDD saveADDD() {
        return dddRepository.save(this.buildAValidDDD());
    }
}
