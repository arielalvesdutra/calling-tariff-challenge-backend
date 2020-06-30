package dev.arielalvesdutra.calling_tariff.integration.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HomeControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void rootRoute_shouldWork() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Welcome!");
    }
}
