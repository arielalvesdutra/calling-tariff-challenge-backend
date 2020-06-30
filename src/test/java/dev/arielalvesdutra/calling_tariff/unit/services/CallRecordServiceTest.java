package dev.arielalvesdutra.calling_tariff.unit.services;

import dev.arielalvesdutra.calling_tariff.exceptions.CallingTariffException;
import dev.arielalvesdutra.calling_tariff.repositories.CallRecordRepository;
import dev.arielalvesdutra.calling_tariff.services.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Unit Tests for CallRecordService Class.
 */
public class CallRecordServiceTest {

    @Mock
    private CallRecordRepository mockCallRecordRepository;
    @Mock
    private DDDService mockDddService;
    @Mock
    private SystemConfigurationService mockSystemConfigurationService;
    @Mock
    private CallTariffMapService mockCallTariffMapService;
    @Mock
    private UserService mockUserService;

    private CallRecordService callRecordService;

    @Before
    public void tearDown() {
        callRecordService = new CallRecordService(
                mockCallRecordRepository,
                mockDddService,
                mockSystemConfigurationService,
                mockCallTariffMapService,
                mockUserService);
    }

    @Test
    public void class_mustHaveServiceAnnotation() {
        assertThat(CallRecordService.class.isAnnotationPresent(Service.class)).isTrue();
    }

    @Test
    public void calculate_withNullParameter_shouldThrowAnException() {
        try {
            callRecordService.calculate(null);
            fail("Expected exception wasn't throw");
        } catch (CallingTariffException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid calculation parameter!");
        }
    }
}
