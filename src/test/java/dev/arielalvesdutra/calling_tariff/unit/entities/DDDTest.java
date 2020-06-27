package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.State;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.StateFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class DDDTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new DDD();
    }

    @Test
    public void settersAndGetters_shouldWork() {
        Long id = 1L;
        Integer code = 51;
        State state = StateFactory.fakeState();

        DDD ddd = new DDD()
                .setId(id)
                .setState(state)
                .setCode(code);

        assertThat(ddd.getId()).isEqualTo(id);
        assertThat(ddd.getState()).isEqualTo(state);
        assertThat(ddd.getCode()).isEqualTo(code);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(DDD.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = DDD.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = DDD.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void equals_shouldBeById() {
        Long id = 1L;

        DDD ddd1 = new DDD().setId(id);
        DDD ddd2 = new DDD().setId(id);

        assertThat(ddd1).isEqualTo(ddd2);
    }

    @Test
    public void state_MustHaveManyToOneAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = DDD.class
                .getDeclaredField("state")
                .isAnnotationPresent(ManyToOne.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void code_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = DDD.class
                .getDeclaredField("code")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }
}
