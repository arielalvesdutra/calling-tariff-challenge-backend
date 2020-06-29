package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.services.CallPlanService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for CallPlanService Class.
 */
public class CallPlanServiceTest {

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(CallPlanService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallPlanService.class
                .getDeclaredMethod("create", CallPlan.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteById_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallPlanService.class
                .getDeclaredMethod("deleteById", Long.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void update_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallPlanService.class
                .getDeclaredMethod("update", CallPlan.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }
}
