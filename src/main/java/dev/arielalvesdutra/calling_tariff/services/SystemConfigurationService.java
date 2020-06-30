package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.SystemConfiguration;
import dev.arielalvesdutra.calling_tariff.repositories.SystemConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static dev.arielalvesdutra.calling_tariff.helpers.ObjectHelper.isEmpty;

@Service
public class SystemConfigurationService {

    @Autowired
    private SystemConfigurationRepository systemConfigurationRepository;

    @Transactional
    public SystemConfiguration create(SystemConfiguration systemConfiguration) {
        return systemConfigurationRepository.save(systemConfiguration);
    }

    public List<SystemConfiguration> findAll() {
        return systemConfigurationRepository.findAll();
    }

    public SystemConfiguration findByCode(String configurationCode) {
        SystemConfiguration configuration = systemConfigurationRepository.findByCode(configurationCode);

        if (isEmpty(configuration)) {
            throw new EntityNotFoundException(
                    "System configuration with code " + configurationCode + " not found!");
        }

        return configuration;
    }

    public SystemConfiguration findById(Long configurationId) {
        return systemConfigurationRepository.findById(configurationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "System configuration with id " + configurationId + " not found!"));
    }

    @Transactional
    public void deleteById(Long systemConfigurationId) {
        SystemConfiguration configuration = findById(systemConfigurationId);
        systemConfigurationRepository.deleteById(configuration.getId());
    }

    @Transactional
    public void deleteAll() {
        systemConfigurationRepository.deleteAll();
    }

    public SystemConfiguration findExceedingPlanFee() {
        return this.findByCode("exceeding_call_plan_fee");
    }

    public SystemConfiguration findDefaultCallMinuteCharge() {
        return this.findByCode("default_call_minute_charge");
    }

    /**
     * Find configuration that is persisted after the installation of
     * the system.
     *
     * @return
     */
    public SystemConfiguration findInstallation() {
        return this.findByCode("installation");
    }
}
