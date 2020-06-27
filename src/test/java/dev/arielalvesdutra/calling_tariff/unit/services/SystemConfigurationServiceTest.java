package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.SystemConfiguration;
import dev.arielalvesdutra.calling_tariff.services.SystemConfigurationService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for SystemConfigurationService class.
 */
public class SystemConfigurationServiceTest {

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(SystemConfigurationService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = SystemConfigurationService.class
                .getDeclaredMethod("create", SystemConfiguration.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteById_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = SystemConfigurationService.class
                .getDeclaredMethod("deleteById", Long.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }
}
