package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {
    SystemConfiguration findByCode(String configurationCode);
}
