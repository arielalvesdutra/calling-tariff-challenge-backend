package dev.arielalvesdutra.calling_tariff.unit.entities;

import static org.assertj.core.api.Assertions.assertThat;

import dev.arielalvesdutra.calling_tariff.entities.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;

@RunWith(SpringRunner.class)
public class StateTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new State();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        String name = "Rio Grande do Sul";
        String abbreviation = "RS";
        Long id = 1L;

        State state = new State()
                .setName(name)
                .setAbbreviation(abbreviation)
                .setId(id);

        assertThat(state.getId()).isEqualTo(id);
        assertThat(state.getName()).isEqualTo(name);
        assertThat(state.getAbbreviation()).isEqualTo(abbreviation);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(State.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = State.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = State.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void name_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = State.class
                .getDeclaredField("name")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void abbreviation_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = State.class
                .getDeclaredField("abbreviation")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.unique()).isEqualTo(true);
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void equals_shouldBeByAbbreviation() {
        String abbreviation = "RS";

        State state1 = new State().setAbbreviation(abbreviation);
        State state2 = new State().setAbbreviation(abbreviation);

        assertThat(state1).isEqualTo(state2);
    }
}
