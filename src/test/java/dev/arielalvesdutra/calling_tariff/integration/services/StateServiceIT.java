package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.repositories.StateRepository;
import dev.arielalvesdutra.calling_tariff.services.StateService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Integration Test for StateService Class.
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class StateServiceIT {

    @Autowired
    private StateService stateService;

    @Autowired
    private StateRepository stateRepository;

    @After
    public void tearDown() {
        stateRepository.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        State stateToCreate = this.buildAStateForPersist();

        State createdState = stateService.create(stateToCreate);
        State fetchedState = stateRepository.findById(createdState.getId()).get();

        assertThat(fetchedState).isNotNull();
        assertThat(fetchedState.getId()).isEqualTo(createdState.getId());
        assertThat(fetchedState.getName()).isEqualTo(stateToCreate.getName());
        assertThat(fetchedState.getAbbreviation()).isEqualTo(stateToCreate.getAbbreviation());
    }

    @Test
    public void findAll_shouldWork() {
        State createdState = this.saveAState();

        List<State> stateList = stateService.findAll();

        assertThat(stateList).isNotNull();
        assertThat(stateList).contains(createdState);
    }

    @Test
    public void findById_shouldWork() {
        State createdState = this.saveAState();

        State state = stateService.findById(createdState.getId());

        assertThat(state).isNotNull();
        assertThat(state.getId()).isEqualTo(createdState.getId());
        assertThat(state.getName()).isEqualTo(createdState.getName());
        assertThat(state.getAbbreviation()).isEqualTo(createdState.getAbbreviation());
    }

    @Test
    public void findByAbbreviation_shouldWork() {
        State createdState = this.saveAState();

        State state = stateService.findByAbbreviation(createdState.getAbbreviation());

        assertThat(state).isNotNull();
        assertThat(state.getId()).isEqualTo(createdState.getId());
        assertThat(state.getName()).isEqualTo(createdState.getName());
        assertThat(state.getAbbreviation()).isEqualTo(createdState.getAbbreviation());
    }

    private State buildAStateForPersist() {
        return new State()
                .setName("Rio Grande do Sul")
                .setAbbreviation("RS");
    }

    private State saveAState() {
        return stateRepository.save(this.buildAStateForPersist());
    }
}
