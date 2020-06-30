package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.Role;
import dev.arielalvesdutra.calling_tariff.repositories.RoleRepository;
import dev.arielalvesdutra.calling_tariff.services.RoleService;
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

;

/**
 * Integration Test for RoleService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class RoleServiceIT {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @After
    public void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        Role roleToCreate = buildAValidRole();

        Role createdRole = roleService.create(roleToCreate);
        Role fetchedRole = roleRepository.findById(createdRole.getId()).get();

        assertThat(fetchedRole).isNotNull();
        assertThat(fetchedRole.getId()).isNotNull();
        assertThat(fetchedRole.getName()).isEqualTo(roleToCreate.getName());
        assertThat(fetchedRole.getDescription()).isEqualTo(roleToCreate.getDescription());
        assertThat(fetchedRole.getCode()).isEqualTo(roleToCreate.getCode());
        assertThat(fetchedRole.getCreatedAt()).isNotNull();
        assertThat(fetchedRole.getUpdatedAt()).isNotNull();
        assertThat(fetchedRole.getUuid()).isNotNull();
    }

    @Test
    public void findAll_shouldWork() {
        Role createdRole = this.saveARole();

        List<Role> roleList = roleService.findAll();

        assertThat(roleList).isNotNull();
        assertThat(roleList).contains(createdRole);
    }

    @Test
    public void findById_shouldWork() {
        Role createdRole = this.saveARole();

        Role fetchedRole = roleService.findById(createdRole.getId());

        assertThat(fetchedRole).isNotNull();
        assertThat(fetchedRole.getId()).isNotNull();
        assertThat(fetchedRole.getName()).isEqualTo(createdRole.getName());
        assertThat(fetchedRole.getDescription()).isEqualTo(createdRole.getDescription());
        assertThat(fetchedRole.getCode()).isEqualTo(createdRole.getCode());
        assertThat(fetchedRole.getCreatedAt()).isEqualTo(createdRole.getCreatedAt());
        assertThat(fetchedRole.getUpdatedAt()).isEqualTo(createdRole.getUpdatedAt());
        assertThat(fetchedRole.getUuid()).isEqualTo(createdRole.getUuid());
    }

    @Test
    public void findByCode_shouldWork() {
        Role createdRole = this.saveARole();

        Role fetchedRole = roleService.findByCode(createdRole.getCode());

        assertThat(fetchedRole).isNotNull();
        assertThat(fetchedRole.getId()).isNotNull();
        assertThat(fetchedRole.getName()).isEqualTo(createdRole.getName());
        assertThat(fetchedRole.getDescription()).isEqualTo(createdRole.getDescription());
        assertThat(fetchedRole.getCode()).isEqualTo(createdRole.getCode());
        assertThat(fetchedRole.getCreatedAt()).isEqualTo(createdRole.getCreatedAt());
        assertThat(fetchedRole.getUpdatedAt()).isEqualTo(createdRole.getUpdatedAt());
        assertThat(fetchedRole.getUuid()).isEqualTo(createdRole.getUuid());
    }

    @Test
    public void deleteBy_shouldWork() {
        Role createdRole = saveARole();

        roleService.deleteById(createdRole.getId());
        Optional<Role> roleOptional = roleRepository.findById(createdRole.getId());

        assertThat(roleOptional.isPresent()).isFalse();
    }

    @Test
    public void update_shouldWork() {
        Role roleToUpdate = saveARole();

        roleToUpdate.setName("ADMIN MODIFIED");
        roleToUpdate.setDescription("Modified description");

        Role updatedRole = roleService.update(roleToUpdate);
        Role fetchedRole = roleRepository.findById(updatedRole.getId()).get();

        assertThat(fetchedRole).isNotNull();
        assertThat(fetchedRole.getId()).isEqualTo(roleToUpdate.getId());
        assertThat(fetchedRole.getUuid()).isEqualTo(roleToUpdate.getUuid());
        assertThat(fetchedRole.getName()).isEqualTo(roleToUpdate.getName());
        assertThat(fetchedRole.getDescription()).isEqualTo(roleToUpdate.getDescription());
        assertThat(fetchedRole.getCode()).isEqualTo(roleToUpdate.getCode());
        assertThat(fetchedRole.getCreatedAt()).isEqualTo(roleToUpdate.getCreatedAt());
        assertThat(fetchedRole.getUpdatedAt()).isNotEqualTo(roleToUpdate.getUpdatedAt());
    }

    public Role buildAValidRole() {
        return new Role()
                .setName("ADMIN")
                .setDescription("Administrator of the systen.")
                .setCode("ROLE_ADMIN");
    }

    public Role saveARole() {
        return roleRepository.save(this.buildAValidRole());
    }
}
