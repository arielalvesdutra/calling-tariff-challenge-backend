package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.repositories.CallPlanRepository;
import dev.arielalvesdutra.calling_tariff.services.CallPlanService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for CallPlanService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CallPlanServiceIT {

    @Autowired
    private CallPlanRepository callPlanRepository;

    @Autowired
    private CallPlanService callPlanService;

    @After
    public void tearDown() {
        callPlanRepository.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        CallPlan callPlanToCreate = buildAValidCallPlan();

        CallPlan createdCallPlan = callPlanService.create(callPlanToCreate);
        CallPlan fetchedCallPlan = callPlanRepository.findById(createdCallPlan.getId()).get();

        assertThat(fetchedCallPlan).isNotNull();
        assertThat(fetchedCallPlan.getId()).isEqualTo(createdCallPlan.getId());
        assertThat(fetchedCallPlan.getName()).isEqualTo(callPlanToCreate.getName());
        assertThat(fetchedCallPlan.getDescription()).isEqualTo(callPlanToCreate.getDescription());
        assertThat(fetchedCallPlan.getPrice()).isEqualTo(callPlanToCreate.getPrice());
        assertThat(fetchedCallPlan.getMinutes()).isEqualTo(callPlanToCreate.getMinutes());
        assertThat(fetchedCallPlan.isVisible()).isTrue();
        assertThat(fetchedCallPlan.getCreatedAt()).isNotNull();
        assertThat(fetchedCallPlan.getUpdatedAt()).isNotNull();
        assertThat(fetchedCallPlan.getUuid()).isNotNull();
    }

    @Test
    public void findAll_shouldWork() {
        CallPlan createdCallPlan = saveACallPlan();

        List<CallPlan> callPlanList = callPlanService.findAll();

        assertThat(callPlanList).isNotNull();
        assertThat(callPlanList).contains(createdCallPlan);
    }

    @Test
    public void findById_shouldWork() {
        CallPlan createdCallPlan = saveACallPlan();

        CallPlan fetchedCallPlan = callPlanService.findById(createdCallPlan.getId());

        assertThat(fetchedCallPlan).isNotNull();
        assertThat(fetchedCallPlan.getId()).isEqualTo(createdCallPlan.getId());
        assertThat(fetchedCallPlan.getName()).isEqualTo(createdCallPlan.getName());
        assertThat(fetchedCallPlan.getDescription()).isEqualTo(createdCallPlan.getDescription());
        assertThat(fetchedCallPlan.getPrice()).isEqualTo(createdCallPlan.getPrice());
        assertThat(fetchedCallPlan.getMinutes()).isEqualTo(createdCallPlan.getMinutes());
        assertThat(fetchedCallPlan.isVisible()).isEqualTo(createdCallPlan.isVisible());
        assertThat(fetchedCallPlan.getCreatedAt()).isEqualTo(createdCallPlan.getCreatedAt());
        assertThat(fetchedCallPlan.getUpdatedAt()).isEqualTo(createdCallPlan.getUpdatedAt());
        assertThat(fetchedCallPlan.getUuid()).isEqualTo(createdCallPlan.getUuid());
    }

    @Test
    public void findByUuid_shouldWork() {
        CallPlan createdCallPlan = saveACallPlan();

        CallPlan fetchedCallPlan = callPlanService.findByUuid(createdCallPlan.getUuid());

        assertThat(fetchedCallPlan).isNotNull();
        assertThat(fetchedCallPlan.getId()).isEqualTo(createdCallPlan.getId());
        assertThat(fetchedCallPlan.getName()).isEqualTo(createdCallPlan.getName());
        assertThat(fetchedCallPlan.getDescription()).isEqualTo(createdCallPlan.getDescription());
        assertThat(fetchedCallPlan.getPrice()).isEqualTo(createdCallPlan.getPrice());
        assertThat(fetchedCallPlan.getMinutes()).isEqualTo(createdCallPlan.getMinutes());
        assertThat(fetchedCallPlan.isVisible()).isEqualTo(createdCallPlan.isVisible());
        assertThat(fetchedCallPlan.getCreatedAt()).isEqualTo(createdCallPlan.getCreatedAt());
        assertThat(fetchedCallPlan.getUpdatedAt()).isEqualTo(createdCallPlan.getUpdatedAt());
        assertThat(fetchedCallPlan.getUuid()).isEqualTo(createdCallPlan.getUuid());
    }

    @Test
    public void deleteById_shouldWork() {
        CallPlan createdCallPlan = saveACallPlan();

        callPlanService.deleteById(createdCallPlan.getId());
        Optional<CallPlan> callPlanOptional = callPlanRepository.findById(createdCallPlan.getId());

        assertThat(callPlanOptional.isPresent()).isFalse();
    }

    @Test
    public void update_shouldWork() {
        CallPlan createdCallPlan = saveACallPlan();
        CallPlan callPlanToUpdate = new CallPlan()
                .setId(createdCallPlan.getId())
                .setUuid(createdCallPlan.getUuid())
                .setVisible(false)
                .setName("FaleMais 35")
                .setDescription("Fale mais com 35 minutos")
                .setPrice(new BigDecimal("35.00"))
                .setMinutes(35);

        CallPlan updatedCallPlan = callPlanService.update(callPlanToUpdate);
        CallPlan fetchedCallPlan = callPlanRepository.findById(updatedCallPlan.getId()).get();

        assertThat(fetchedCallPlan).isNotNull();
        assertThat(fetchedCallPlan.getId()).isEqualTo(updatedCallPlan.getId());
        assertThat(fetchedCallPlan.getUuid()).isEqualTo(callPlanToUpdate.getUuid());
        assertThat(fetchedCallPlan.getName()).isEqualTo(callPlanToUpdate.getName());
        assertThat(fetchedCallPlan.getDescription()).isEqualTo(callPlanToUpdate.getDescription());
        assertThat(fetchedCallPlan.getPrice()).isEqualTo(callPlanToUpdate.getPrice());
        assertThat(fetchedCallPlan.getMinutes()).isEqualTo(callPlanToUpdate.getMinutes());
        assertThat(fetchedCallPlan.isVisible()).isNotEqualTo(createdCallPlan.isVisible());
        assertThat(fetchedCallPlan.getCreatedAt()).isEqualTo(createdCallPlan.getCreatedAt());
        assertThat(fetchedCallPlan.getUpdatedAt()).isNotEqualTo(createdCallPlan.getUpdatedAt());
        assertThat(updatedCallPlan.getUpdatedAt()).isNotEqualTo(callPlanToUpdate.getUpdatedAt());
    }

    private CallPlan saveACallPlan() {
        return callPlanRepository.save(buildAValidCallPlan());
    }

    private CallPlan buildAValidCallPlan() {
        return new CallPlan()
                .setName("FaleMais 33")
                .setDescription("Fale mais com 33 minutos")
                .setMinutes(33)
                .setPrice(new BigDecimal("33.00"))
                .setVisible(true);
    }
}
