package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.Role;
import dev.arielalvesdutra.calling_tariff.services.RoleService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for RoleService class.
 */
public class RoleServiceTest {

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(RoleService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = RoleService.class
                .getDeclaredMethod("create", Role.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteById_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = RoleService.class
                .getDeclaredMethod("deleteById", Long.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void update_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = RoleService.class
                .getDeclaredMethod("update", Role.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }
}
