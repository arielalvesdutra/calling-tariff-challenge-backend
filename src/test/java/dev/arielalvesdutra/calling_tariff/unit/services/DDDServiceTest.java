package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.services.DDDService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for DDDService class.
 */
public class DDDServiceTest {

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(DDDService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = DDDService.class
                .getDeclaredMethod("create", DDD.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteById_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = DDDService.class
                .getDeclaredMethod("deleteById", Long.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }
}
