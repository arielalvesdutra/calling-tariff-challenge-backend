package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.repositories.CallTariffMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CallTariffMapService {

    @Autowired
    private CallTariffMapRepository callTariffMapRepository;
    
    @Transactional
    public CallTariffMap create(CallTariffMap callTariffMap) {
        return callTariffMapRepository.save(callTariffMap);
    }

    @Transactional
    public CallTariffMap update(CallTariffMap parameterCallTariffMap) {
        return null;
    }

    public List<CallTariffMap> findAll() {
        return callTariffMapRepository.findAll();
    }

    public CallTariffMap findByUuid(UUID uuid) {
        CallTariffMap callTariffMap = callTariffMapRepository.findByUuid(uuid);

        if (callTariffMap == null) {
            throw new EntityNotFoundException(
                    "Call tariff map with uuid " + uuid + " not found!");
        }

        return callTariffMap;
    }

    public CallTariffMap findByOriginCodeAndDestinationCode(Integer originCode, Integer destinationCode) {
        CallTariffMap callTariffMap =
                callTariffMapRepository.findByOrigin_CodeAndDestination_Code(originCode, destinationCode);

        if (callTariffMap == null) {
            throw new EntityNotFoundException(
                    "Call tariff map with origin code " + originCode
                            + " and destination code " + destinationCode
                            + " not found!");
        }

        return callTariffMap;
    }

    @Transactional
    public void deleteByUuid(UUID uuid) {
        CallTariffMap callTariffMap = findByUuid(uuid);
        callTariffMapRepository.deleteByUuid(callTariffMap.getUuid());
    }

    @Transactional
    public void deleteByOriginCodeAndDestinationCode(Integer originCode, Integer destinationCode) {
        findByOriginCodeAndDestinationCode(originCode, destinationCode);
        callTariffMapRepository.deleteByOrigin_codeAndDestination_Code(originCode, destinationCode);
    }

    @Transactional
    public void deleteAll() {
        callTariffMapRepository.deleteAll();
    }
}
