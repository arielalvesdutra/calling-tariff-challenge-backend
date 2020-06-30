package dev.arielalvesdutra.calling_tariff.integration.services;

import dev.arielalvesdutra.calling_tariff.entities.SystemConfiguration;
import dev.arielalvesdutra.calling_tariff.repositories.SystemConfigurationRepository;
import dev.arielalvesdutra.calling_tariff.services.SystemConfigurationService;
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
 * Integration Test for SystemConfigurationService Class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class SystemConfigurationServiceIT {

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private SystemConfigurationRepository systemConfigurationRepository;

    @After
    public void tearDown() {
        systemConfigurationRepository.deleteAll();
    }

    @Test
    public void create_shouldWork() {
        SystemConfiguration configurationToCreeate = this.buildAValidSystemConfiguration();

        SystemConfiguration createdConfiguration = systemConfigurationService.create(configurationToCreeate);
        SystemConfiguration fetchedConfiguration = systemConfigurationRepository.findById(createdConfiguration.getId()).get();

        assertThat(createdConfiguration).isNotNull();
        assertThat(createdConfiguration.getId()).isNotNull();
        assertThat(fetchedConfiguration.getName()).isEqualTo(configurationToCreeate.getName());
        assertThat(fetchedConfiguration.getDescription()).isEqualTo(configurationToCreeate.getDescription());
        assertThat(fetchedConfiguration.getCode()).isEqualTo(configurationToCreeate.getCode());
        assertThat(fetchedConfiguration.getValue()).isEqualTo(configurationToCreeate.getValue());
    }

    @Test
    public void findAll_shouldWork() {
        SystemConfiguration createdConfiguration = this.saveASystemConfiguration();

        List<SystemConfiguration> configurationList = systemConfigurationService.findAll();

        assertThat(configurationList).isNotNull();
        assertThat(configurationList).contains(createdConfiguration);
    }

    @Test
    public void findById_shouldWork() {
        SystemConfiguration createdConfiguration = this.saveASystemConfiguration();

        SystemConfiguration fetchedConfiguration =
                systemConfigurationService.findById(createdConfiguration.getId());

        assertThat(fetchedConfiguration).isNotNull();
        assertThat(fetchedConfiguration.getId()).isEqualTo(createdConfiguration.getId());
        assertThat(fetchedConfiguration.getName()).isEqualTo(createdConfiguration.getName());
        assertThat(fetchedConfiguration.getDescription()).isEqualTo(createdConfiguration.getDescription());
        assertThat(fetchedConfiguration.getCode()).isEqualTo(createdConfiguration.getCode());
        assertThat(fetchedConfiguration.getValue()).isEqualTo(createdConfiguration.getValue());
    }


    @Test
    public void findByCode_shouldWork() {
        SystemConfiguration createdConfiguration = this.saveASystemConfiguration();

        SystemConfiguration fetchedConfiguration =
                systemConfigurationService.findByCode(createdConfiguration.getCode());

        assertThat(fetchedConfiguration).isNotNull();
        assertThat(fetchedConfiguration.getId()).isEqualTo(createdConfiguration.getId());
        assertThat(fetchedConfiguration.getName()).isEqualTo(createdConfiguration.getName());
        assertThat(fetchedConfiguration.getDescription()).isEqualTo(createdConfiguration.getDescription());
        assertThat(fetchedConfiguration.getCode()).isEqualTo(createdConfiguration.getCode());
        assertThat(fetchedConfiguration.getValue()).isEqualTo(createdConfiguration.getValue());
    }

    @Test
    public void deleteBy_shouldWork() {
        SystemConfiguration createdConfiguration = saveASystemConfiguration();

        systemConfigurationService.deleteById(createdConfiguration.getId());
        Optional<SystemConfiguration> configurationOptional =
                systemConfigurationRepository.findById(createdConfiguration.getId());

        assertThat(configurationOptional.isPresent()).isFalse();
    }

    private SystemConfiguration buildAValidSystemConfiguration() {
        return new SystemConfiguration()
                .setName("Something")
                .setDescription("Something description;")
                .setCode("something")
                .setValue("33.1");
    }

    private SystemConfiguration saveASystemConfiguration(){
        return systemConfigurationRepository.save(this.buildAValidSystemConfiguration());
    }

}
