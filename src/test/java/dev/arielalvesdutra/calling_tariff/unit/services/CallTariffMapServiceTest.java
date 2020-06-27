package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.services.CallTariffMapService;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for CallTariffMapService Class.
 */
public class CallTariffMapServiceTest {

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(CallTariffMapService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallTariffMapService.class
                .getDeclaredMethod("create", CallTariffMap.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void update_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallTariffMapService.class
                .getDeclaredMethod("update", CallTariffMap.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteByUuid_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallTariffMapService.class
                .getDeclaredMethod("deleteByUuid", UUID.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteByOriginCodeAndDestinationCode_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = CallTariffMapService.class
                .getDeclaredMethod("deleteByOriginCodeAndDestinationCode", Integer.class, Integer.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }
}
