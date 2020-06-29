package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.Role;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.UserFactory;
import org.junit.Test;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleTest {

    private String SUPPORT_ROLE = "ROLE_SUPPORT";

    @Test
    public void emptyConstructor_shouldWork() {
        new Role();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        Long id = 1L;
        UUID uuid = UUID.randomUUID();
        String name = "Support";
        String description = "Support Anaylist";
        String code = SUPPORT_ROLE;
        OffsetDateTime dateTime = OffsetDateTime.now();
        User user = UserFactory.fakeUser();
        Set<User> userSet  = UserFactory.fakeUserSet(user);

        Role role = new Role()
                .setId(id)
                .setUuid(uuid)
                .setName(name)
                .setDescription(description)
                .setCode(code)
                .setCreatedAt(dateTime)
                .setUpdatedAt(dateTime)
                .setUsers(userSet);

        assertThat(role.getId()).isEqualTo(id);
        assertThat(role.getUuid()).isEqualTo(uuid);
        assertThat(role.getName()).isEqualTo(name);
        assertThat(role.getDescription()).isEqualTo(description);
        assertThat(role.getCode()).isEqualTo(code);
        assertThat(role.getCreatedAt()).isEqualTo(dateTime);
        assertThat(role.getUpdatedAt()).isEqualTo(dateTime);
        assertThat(role.getUsers()).contains(user);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(Role.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = Role.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = Role.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void name_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = Role.class
                .getDeclaredField("name")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void code_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column generatedValue = Role.class
                .getDeclaredField("code")
                .getAnnotation(Column.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.unique()).isEqualTo(true);
        assertThat(generatedValue.nullable()).isEqualTo(false);
    }

    @Test
    public void equals_shouldBeByCode() {
        String code = SUPPORT_ROLE;

        Role role1 = new Role().setCode(code);
        Role role2 = new Role().setCode(code);

        assertThat(role1).isEqualTo(role2);
    }

    @Test
    public void users_mustHaveManyToManyAnnotation() throws NoSuchFieldException, SecurityException {
        boolean isManyToOneAnnotationPresent = Role.class
                .getDeclaredField("users")
                .isAnnotationPresent(ManyToMany.class);

        assertThat(isManyToOneAnnotationPresent).isTrue();
    }

    @Test
    public void users_mustHaveJoinTableAnnotationConfigured() throws NoSuchFieldException {
        JoinTable joinTable = Role.class
                .getDeclaredField("users")
                .getAnnotation(JoinTable.class);

        assertThat(joinTable).isNotNull();

        JoinColumn inverseJoinColumn = joinTable.joinColumns()[0];
        JoinColumn joinColumn = joinTable.inverseJoinColumns()[0];

        assertThat(joinTable.name()).isEqualTo("user_role");
        assertThat(joinColumn.name()).isEqualTo("user_id");
        assertThat(joinColumn.referencedColumnName()).isEqualTo("id");
        assertThat(inverseJoinColumn.name()).isEqualTo("role_id");
        assertThat(inverseJoinColumn.referencedColumnName()).isEqualTo("id");
    }
}
