package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.FakeUserFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class CallPlanTest {

    @Test
    public void emptyConstructor_shouldWork(){
        new CallPlan();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        Long id = 1L;
        UUID uuid = UUID.randomUUID();
        String name = "FaleMais 23";
        String description = "Fale por 23 minutos";
        Integer minutes = 23;
        BigDecimal price = new BigDecimal(31.99);
        boolean isVisible = true;
        OffsetDateTime datetime = OffsetDateTime.now();
        User user = FakeUserFactory.fakeUser();
        Set<User> userSet = FakeUserFactory.fakeUserSet(user);

        CallPlan callPlan = new CallPlan()
                .setId(id)
                .setUuid(uuid)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setMinutes(minutes)
                .setVisible(isVisible)
                .setCreatedAt(datetime)
                .setUpdatedAt(datetime)
                .setUsers(userSet);

        assertThat(callPlan.getId()).isEqualTo(id);
        assertThat(callPlan.getUuid()).isEqualTo(uuid);
        assertThat(callPlan.getName()).isEqualTo(name);
        assertThat(callPlan.getDescription()).isEqualTo(description);
        assertThat(callPlan.getMinutes()).isEqualTo(minutes);
        assertThat(callPlan.getPrice()).isEqualTo(price);
        assertThat(callPlan.isVisible()).isEqualTo(isVisible);
        assertThat(callPlan.getCreatedAt()).isEqualTo(datetime);
        assertThat(callPlan.getUpdatedAt()).isEqualTo(datetime);
        assertThat(callPlan.getUsers()).contains(user);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(CallPlan.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = CallPlan.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = CallPlan.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void equals_shouldBeById() {
        Long id = 1L;

        CallPlan callPlan1 = new CallPlan().setId(id);
        CallPlan callPlan2 = new CallPlan().setId(id);

        assertThat(callPlan1).isEqualTo(callPlan2);
    }

    @Test
    public void visible_defaultValue_shouldBeFalse() {
        CallPlan callPlan = new CallPlan();

        assertThat(callPlan.isVisible()).isFalse();
    }

    @Test
    public void users_mustHaveOneToManyAnnotationConfigured() throws NoSuchFieldException {
        OneToMany oneToMany = CallPlan.class
                .getDeclaredField("users")
                .getAnnotation(OneToMany.class);

        assertThat(oneToMany).isNotNull();
        assertThat(oneToMany.mappedBy()).isEqualTo("callPlan");
        assertThat(oneToMany.orphanRemoval()).isEqualTo(true);

        CascadeType[] cascade = oneToMany.cascade();
        assertThat(cascade[0]).isEqualTo(CascadeType.ALL);
    }

    @Test
    public void name_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = CallPlan.class
                .getDeclaredField("name")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void price_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = CallPlan.class
                .getDeclaredField("price")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void minutes_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = CallPlan.class
                .getDeclaredField("minutes")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }
}
