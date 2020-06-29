package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;
import dev.arielalvesdutra.calling_tariff.repositories.UserRepository;
import dev.arielalvesdutra.calling_tariff.services.CallPlanService;
import dev.arielalvesdutra.calling_tariff.services.UserService;
import dev.arielalvesdutra.calling_tariff.services.dtos.UpdateUserCallPlanDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Unit Tests for UserService Class.
 */
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private CallPlanService mockCallPlanService;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(
                mockUserRepository,
                mockCallPlanService);
    }

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(UserService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void create_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = UserService.class
                .getDeclaredMethod("create", User.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void deleteById_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = UserService.class
                .getDeclaredMethod("deleteById", Long.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void update_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = UserService.class
                .getDeclaredMethod("update", User.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void updateUserCallPlan_mustHaveTransactionalAnnotation() throws NoSuchMethodException {
        boolean isPresent = UserService.class
                .getDeclaredMethod("updateUserCallPlan", UpdateUserCallPlanDTO.class)
                .isAnnotationPresent(Transactional.class);

        assertThat(isPresent).isTrue();
    }

    @Test
    public void updateUserCallPlan_withoutUser_shouldThrowAnException() {
        try {
            UpdateUserCallPlanDTO dto = new UpdateUserCallPlanDTO();

            userService.updateUserCallPlan(dto);

            fail("Expected exception wasn't throw");
        } catch (CallingTariffException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid update user call plan parameter!");
        }
    }
}
