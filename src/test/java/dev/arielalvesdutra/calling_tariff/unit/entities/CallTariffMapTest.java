package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.entities.DDD;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CallTariffMapTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new CallTariffMap();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        DDD origin = new DDD();
        DDD destination = new DDD();
        BigDecimal pricePerMinute = new BigDecimal("2.00");

        CallTariffMap callTariffMap = new CallTariffMap()
                .setOrigin(origin)
                .setDestination(destination)
                .setPricePerMinute(pricePerMinute);

        assertThat(callTariffMap.getOrigin()).isEqualTo(origin);
        assertThat(callTariffMap.getDestination()).isEqualTo(destination);
        assertThat(callTariffMap.getPricePerMinute()).isEqualTo(pricePerMinute);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(CallTariffMap.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void originAndDestination_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean originHasAnnotation = CallTariffMap.class
                .getDeclaredField("origin")
                .isAnnotationPresent(Id.class);
        boolean destinationHasAnnotation = CallTariffMap.class
                .getDeclaredField("destination")
                .isAnnotationPresent(Id.class);

        assertThat(originHasAnnotation).isTrue();
        assertThat(destinationHasAnnotation).isTrue();
    }

    @Test
    public void equals_shouldBeByOriginAndDestination() {
        DDD origin = new DDD().setId(1L);
        DDD destination = new DDD().setId(2L);

        CallTariffMap callTariffMap1 = new CallTariffMap()
                .setOrigin(origin)
                .setDestination(destination);
        CallTariffMap callTariffMap2 = new CallTariffMap()
                .setOrigin(origin)
                .setDestination(destination);

        assertThat(callTariffMap1).isEqualTo(callTariffMap2);
    }

    @Test
    public void pricePerMinute_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = CallTariffMap.class
                .getDeclaredField("pricePerMinute")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }
}
