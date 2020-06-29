package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.services.StateService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for StateService class.
 */
public class StateServiceTest {

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(StateService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = StateService.class
                .getDeclaredMethod("create", State.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }
}
