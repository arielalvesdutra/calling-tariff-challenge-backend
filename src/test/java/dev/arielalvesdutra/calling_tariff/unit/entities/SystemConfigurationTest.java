package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.SystemConfiguration;
import org.junit.Test;

import javax.persistence.*;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemConfigurationTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new SystemConfiguration();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        Long id = 1L;
        String code = "default_tariff";
        String name = "Tarifa padrão";
        String description = "Tarifa padrão utilizada no sistema";
        String value = "1.44";

        SystemConfiguration systemConfiguration = new SystemConfiguration()
                .setId(id)
                .setCode(code)
                .setName(name)
                .setDescription(description)
                .setValue(value);

        assertThat(systemConfiguration.getId()).isEqualTo(id);
        assertThat(systemConfiguration.getCode()).isEqualTo(code);
        assertThat(systemConfiguration.getName()).isEqualTo(name);
        assertThat(systemConfiguration.getDescription()).isEqualTo(description);
        assertThat(systemConfiguration.getValue()).isEqualTo(value);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(SystemConfiguration.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = SystemConfiguration.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = SystemConfiguration.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void name_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = SystemConfiguration.class
                .getDeclaredField("name")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void code_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = SystemConfiguration.class
                .getDeclaredField("code")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.unique()).isEqualTo(true);
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void value_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = SystemConfiguration.class
                .getDeclaredField("value")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void equals_shouldBeByCode() {
        String code = "some_code";

        SystemConfiguration systemConfiguration1 = new SystemConfiguration()
                .setCode(code);
        SystemConfiguration systemConfiguration2 = new SystemConfiguration()
                .setCode(code);

        assertThat(systemConfiguration1).isEqualTo(systemConfiguration2);
    }
}
