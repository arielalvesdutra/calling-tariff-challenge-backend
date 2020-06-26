package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.Historic;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.FakeUserFactory;
import org.junit.Test;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoricTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new Historic();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        Long id = 1L;
        UUID uuid = UUID.randomUUID();
        User author = FakeUserFactory.fakeUser();
        User client = FakeUserFactory.fakeUser();
        String description = "User updated his information.";
        OffsetDateTime dateTime = OffsetDateTime.now();

        Historic historic = new Historic()
                .setId(id)
                .setUuid(uuid)
                .setAuthor(author)
                .setClient(client)
                .setDescription(description)
                .setCreatedAt(dateTime);

        assertThat(historic.getId()).isEqualTo(id);
        assertThat(historic.getUuid()).isEqualTo(uuid);
        assertThat(historic.getAuthor()).isEqualTo(author);
        assertThat(historic.getClient()).isEqualTo(client);
        assertThat(historic.getDescription()).isEqualTo(description);
        assertThat(historic.getCreatedAt()).isEqualTo(dateTime);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(Historic.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = Historic.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = Historic.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void client_mustHaveManyToOneAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = Historic.class
                .getDeclaredField("client")
                .isAnnotationPresent(ManyToOne.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void description_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = Historic.class
                .getDeclaredField("description")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void author_mustHaveManyToOneAnnotation() throws NoSuchFieldException {
        boolean isAnnotationPresent = Historic.class
                .getDeclaredField("author")
                .isAnnotationPresent(ManyToOne.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    public void equals_shouldBeByIdAndClientAndAuthorAndDescriptionAndCreatedAt() {
        Long id = 1L;
        User author = FakeUserFactory.fakeUser();
        User client = FakeUserFactory.fakeUser();
        String description = "User updated his information.";
        OffsetDateTime dateTime = OffsetDateTime.now();

        Historic historic1 = new Historic()
                .setId(id)
                .setAuthor(author)
                .setClient(client)
                .setDescription(description)
                .setCreatedAt(dateTime);
        Historic historic2 = new Historic()
                .setId(id)
                .setAuthor(author)
                .setClient(client)
                .setDescription(description)
                .setCreatedAt(dateTime);

        assertThat(historic1).isEqualTo(historic2);
    }
}
