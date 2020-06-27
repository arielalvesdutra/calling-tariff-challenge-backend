package dev.arielalvesdutra.calling_tariff.unit.entities;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.entities.Role;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.CallPlanFactory;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.RoleFactory;
import org.junit.Test;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void emptyConstructor_shouldWork() {
        new User();
    }

    @Test
    public void gettersAndSetters_shouldWork() {
        Long id = 1L;
        UUID uuid = UUID.randomUUID();
        String name = "Geralt of Rivia";
        String email = "test@test.com";
        String password = "132456";
        boolean active = true;
        OffsetDateTime dateTime = OffsetDateTime.now();
        Role role = RoleFactory.fakeRole();
        Set<Role> roles = RoleFactory.fakeRoleSet(role);
        CallPlan callPlan = CallPlanFactory.fakeCallPlan();

        User user = new User()
            .setId(id)
            .setUuid(uuid)
            .setName(name)
            .setEmail(email)
            .setPassword(password)
            .setActive(active)
            .setCreatedAt(dateTime)
            .setUpdatedAt(dateTime)
            .setRoles(roles)
            .setCallPlan(callPlan);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUuid()).isEqualTo(uuid);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.isActive()).isEqualTo(active);
        assertThat(user.getCreatedAt()).isEqualTo(dateTime);
        assertThat(user.getUpdatedAt()).isEqualTo(dateTime);
        assertThat(user.getRoles()).contains(role);
        assertThat(user.getCallPlan()).isEqualTo(callPlan);
    }

    @Test
    public void class_mustHaveEntityAnnotation() {
        assertThat(User.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    public void id_mustHaveIdAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = User.class
                .getDeclaredField("id")
                .isAnnotationPresent(Id.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void id_mustHaveGeneratedValueAnnotationWithStrategyIdentity()
            throws NoSuchFieldException {
        GeneratedValue generatedValue = User.class
                .getDeclaredField("id")
                .getAnnotation(GeneratedValue.class);

        assertThat(generatedValue).isNotNull();
        assertThat(generatedValue.strategy()).isEqualTo(GenerationType.IDENTITY);
    }

    @Test
    public void email_mustMustHaveEmailAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = User.class
                .getDeclaredField("email")
                .isAnnotationPresent(Email.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void email_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = User.class
                .getDeclaredField("email")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.unique()).isEqualTo(true);
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void password_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = User.class
                .getDeclaredField("password")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void name_mustHaveColumnAnnotationConfigured() throws NoSuchFieldException {
        Column column = User.class
                .getDeclaredField("name")
                .getAnnotation(Column.class);

        assertThat(column).isNotNull();
        assertThat(column.nullable()).isEqualTo(false);
    }

    @Test
    public void equals_shouldBeByEmail() {
        String email = "test@test.com";

        User user1 = new User().setEmail(email);
        User user2 = new User().setEmail(email);

        assertThat(user1).isEqualTo(user2);
    }

    @Test
    public void roles_mustHaveManyToManyAnnotation() throws NoSuchFieldException, SecurityException {
        boolean isManyToOneAnnotationPresent = User.class
                .getDeclaredField("roles")
                .isAnnotationPresent(ManyToMany.class);

        assertThat(isManyToOneAnnotationPresent).isTrue();
    }

    @Test
    public void roles_mustHaveJoinTableAnnotationConfigured() throws NoSuchFieldException {
        JoinTable joinTable = User.class
                .getDeclaredField("roles")
                .getAnnotation(JoinTable.class);

        assertThat(joinTable).isNotNull();

        JoinColumn inverseJoinColumn = joinTable.inverseJoinColumns()[0];
        JoinColumn joinColumn = joinTable.joinColumns()[0];

        assertThat(joinTable.name()).isEqualTo("user_role");
        assertThat(joinColumn.name()).isEqualTo("user_id");
        assertThat(joinColumn.referencedColumnName()).isEqualTo("id");
        assertThat(inverseJoinColumn.name()).isEqualTo("role_id");
        assertThat(inverseJoinColumn.referencedColumnName()).isEqualTo("id");
    }

    @Test
    public void callPlan_mustHaveManyToOneAnnotation() throws NoSuchFieldException {
        boolean hasAnnotation = User.class
                .getDeclaredField("callPlan")
                .isAnnotationPresent(ManyToOne.class);

        assertThat(hasAnnotation).isTrue();
    }

    @Test
    public void password_mustHaveMinAnnotationConfigured() throws NoSuchFieldException {
        Size size = User.class
                .getDeclaredField("password")
                .getAnnotation(Size.class);

        assertThat(size).isNotNull();
        assertThat(size.min()).isEqualTo(8);
    }
}
