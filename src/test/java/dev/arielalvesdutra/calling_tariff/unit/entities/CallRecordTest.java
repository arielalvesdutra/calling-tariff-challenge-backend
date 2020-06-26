package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallRecord;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.FakeUserFactory;
import org.junit.Test;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CallRecordTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new CallRecord();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        Long id = 1L;
        User client = FakeUserFactory.fakeUser();
        Integer minutes = 32;
        BigDecimal price = new BigDecimal("22.10");
        OffsetDateTime dateTime = OffsetDateTime.now();

        CallRecord callRecord = new CallRecord()
                .setId(id)
                .setClient(client)
                .setMinutes(minutes)
                .setPrice(price)
                .setCreatedAt(dateTime);

        assertThat(callRecord.getId()).isEqualTo(id);
        assertThat(callRecord.getClient()).isEqualTo(client);
        assertThat(callRecord.getMinutes()).isEqualTo(minutes);
        assertThat(callRecord.getPrice()).isEqualTo(price);
        assertThat(callRecord.getCreatedAt()).isEqualTo(dateTime);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(CallRecord.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = CallRecord.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = CallRecord.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void price_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = CallRecord.class
                .getDeclaredField("price")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void client_mustHaveManyToOneAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = CallRecord.class
                .getDeclaredField("client")
                .isAnnotationPresent(ManyToOne.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void equals_shouldBeById() {
        Long id = 1L;

        CallRecord callRecord1 = new CallRecord().setId(id);
        CallRecord callRecord2 = new CallRecord().setId(id);

        assertThat(callRecord1).isEqualTo(callRecord2);
    }
}
