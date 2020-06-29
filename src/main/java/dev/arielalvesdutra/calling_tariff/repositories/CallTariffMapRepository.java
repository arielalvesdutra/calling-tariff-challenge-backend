package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.entities.pk.CallTariffMapId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CallTariffMapRepository extends
            JpaRepository<CallTariffMap, CallTariffMapId> {

    CallTariffMap findByUuid(UUID uuid);

    CallTariffMap findByOrigin_CodeAndDestination_Code(Integer originCode, Integer destinationCode);

    void deleteByUuid(UUID uuid);

    void deleteByOrigin_codeAndDestination_Code(Integer originCode, Integer destinationCode);
}
