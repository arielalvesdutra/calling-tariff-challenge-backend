package dev.arielalvesdutra.calling_tariff.configs;

import dev.arielalvesdutra.calling_tariff.services.ApplicationDataSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Runner for insert essential data in the application.
 */
@Component
public class ApplicationDataSeed implements CommandLineRunner {

    @Autowired
    private ApplicationDataSeedService dataSeedService;

    @Override
    public void run(String... args) throws Exception {
        dataSeedService.seed();
    }
}
