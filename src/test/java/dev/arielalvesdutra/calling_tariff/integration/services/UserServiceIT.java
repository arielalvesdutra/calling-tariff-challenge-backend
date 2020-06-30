package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.entities.User;
import dev.arielalvesdutra.calling_tariff.fakes.factories.entities.CallPlanFactory;
import dev.arielalvesdutra.calling_tariff.repositories.UserRepository;
import dev.arielalvesdutra.calling_tariff.services.CallPlanService;
import dev.arielalvesdutra.calling_tariff.services.UserService;
import dev.arielalvesdutra.calling_tariff.services.dtos.UpdateUserCallPlanDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for UserService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CallPlanService callPlanService;

    @After
    public void tearDown() {
        userRepository.deleteAll();
        callPlanService.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        User userToCreate = this.buildAValidUser();
        String passwordBeforeCreation = userToCreate.getPassword();

        User createdUser = userService.create(userToCreate);
        User fetchedUser = userRepository.findById(createdUser.getId()).get();

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(fetchedUser.getName()).isEqualTo(userToCreate.getName());
        assertThat(fetchedUser.getEmail()).isEqualTo(userToCreate.getEmail());
        assertThat(fetchedUser.getPassword()).isNotNull();
        assertThat(fetchedUser.getPassword().length()).isGreaterThan(7);
        assertThat(fetchedUser.getPassword()).isNotEqualTo(passwordBeforeCreation);
        assertThat(fetchedUser.getUuid()).isNotNull();
        assertThat(fetchedUser.getCreatedAt()).isNotNull();
        assertThat(fetchedUser.getUpdatedAt()).isNotNull();
        assertThat(fetchedUser.isActive()).isTrue();
    }

    @Test
    public void findAll_shouldWork() {
        User createdUser = saveAUser();

        List<User> userList = userService.findAll();

        assertThat(userList).isNotNull();
        assertThat(userList).contains(createdUser);
    }

    @Test
    public void findById_shouldWork() {
        User createdUser = saveAUser();

        User fetchedUser = userService.findById(createdUser.getId());

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(fetchedUser.getName()).isEqualTo(createdUser.getName());
        assertThat(fetchedUser.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(fetchedUser.getPassword()).isEqualTo(createdUser.getPassword());
        assertThat(fetchedUser.getUuid()).isEqualTo(createdUser.getUuid());
        assertThat(fetchedUser.getCreatedAt()).isEqualTo(createdUser.getCreatedAt());
        assertThat(fetchedUser.getUpdatedAt()).isEqualTo(createdUser.getUpdatedAt());
        assertThat(fetchedUser.isActive()).isEqualTo(createdUser.isActive());
    }

    @Test
    public void findByEmail_shouldWork() {
        User createdUser = saveAUser();

        User fetchedUser = userService.findByEmail(createdUser.getEmail());

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(fetchedUser.getName()).isEqualTo(createdUser.getName());
        assertThat(fetchedUser.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(fetchedUser.getPassword()).isEqualTo(createdUser.getPassword());
        assertThat(fetchedUser.getUuid()).isEqualTo(createdUser.getUuid());
        assertThat(fetchedUser.getCreatedAt()).isEqualTo(createdUser.getCreatedAt());
        assertThat(fetchedUser.getUpdatedAt()).isEqualTo(createdUser.getUpdatedAt());
        assertThat(fetchedUser.isActive()).isEqualTo(createdUser.isActive());
    }

    @Test
    public void findByUuid_shouldWork() {
        User createdUser = saveAUser();

        User fetchedUser = userService.findByUuid(createdUser.getUuid());

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(fetchedUser.getName()).isEqualTo(createdUser.getName());
        assertThat(fetchedUser.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(fetchedUser.getPassword()).isEqualTo(createdUser.getPassword());
        assertThat(fetchedUser.getUuid()).isEqualTo(createdUser.getUuid());
        assertThat(fetchedUser.getCreatedAt()).isEqualTo(createdUser.getCreatedAt());
        assertThat(fetchedUser.getUpdatedAt()).isEqualTo(createdUser.getUpdatedAt());
        assertThat(fetchedUser.isActive()).isEqualTo(createdUser.isActive());
    }

    @Test
    public void deleteById_shouldWork() {
        User createdUser = saveAUser();

        userService.deleteById(createdUser.getId());
        Optional<User> userOptional = userRepository.findById(createdUser.getId());

        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    public void update_shouldWork() {
        User userToUpdate = saveAUser();

        String oldPasswordHash = userToUpdate.getPassword();
        userToUpdate.setPassword("10A293ktz8m4756");
        userToUpdate.setName("Geralt");
        userToUpdate.setActive(false);

        User updatedUser = userService.update(userToUpdate);
        User fetchedUser = userRepository.findById(updatedUser.getId()).get();

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(updatedUser.getId());
        assertThat(fetchedUser.getName()).isEqualTo(userToUpdate.getName());
        assertThat(fetchedUser.getEmail()).isEqualTo(userToUpdate.getEmail());
        assertThat(fetchedUser.isActive()).isEqualTo(userToUpdate.isActive());
        assertThat(fetchedUser.getPassword()).isNotEqualTo(userToUpdate.getPassword());
        assertThat(fetchedUser.getPassword()).isNotEqualTo(oldPasswordHash);
        assertThat(fetchedUser.getCreatedAt()).isEqualTo(userToUpdate.getCreatedAt());
        assertThat(fetchedUser.getUpdatedAt()).isNotEqualTo(userToUpdate.getUpdatedAt());
        assertThat(fetchedUser.getUuid()).isEqualTo(userToUpdate.getUuid());
    }

    @Test
    public void updateUserCallPlan_shouldWork() {
        CallPlan callPlan = callPlanService.create(CallPlanFactory.validCallPlanWithoutId());
        User createdUser = saveAUser();
        UpdateUserCallPlanDTO updateUserCallPlanDTO = new UpdateUserCallPlanDTO()
                .setUserUuid(createdUser.getUuid())
                .setCallPlanUuid(callPlan.getUuid());

        userService.updateUserCallPlan(updateUserCallPlanDTO);
        User fetchedUser = userRepository.findById(createdUser.getId()).get();

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getCallPlan()).isEqualTo(callPlan);
    }

    private User buildAValidUser() {
        return new User()
                .setName("Geralt of Rivia")
                .setPassword("12345678")
                .setEmail("test@test.com");
    }

    private User saveAUser() {
        return userService.create(this.buildAValidUser());
    }
}
