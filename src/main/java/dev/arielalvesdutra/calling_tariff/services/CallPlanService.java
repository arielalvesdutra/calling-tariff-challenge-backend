package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.repositories.CallPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CallPlanService {

    @Autowired
    private CallPlanRepository callPlanRepository;

    @Transactional
    public CallPlan create(CallPlan callPlan) {
        return callPlanRepository.save(callPlan);
    }

    @Transactional
    public void deleteById(Long callPlanId) {
        CallPlan callPlan = findById(callPlanId);
        callPlanRepository.deleteById(callPlan.getId());
    }

    @Transactional
    public CallPlan update(CallPlan parameterCallPlan) {
        CallPlan existingCallPlan = findById(parameterCallPlan.getId());

        existingCallPlan.setName(parameterCallPlan.getName());
        existingCallPlan.setDescription(parameterCallPlan.getDescription());
        existingCallPlan.setPrice(parameterCallPlan.getPrice());
        existingCallPlan.setMinutes(parameterCallPlan.getMinutes());
        existingCallPlan.setVisible(parameterCallPlan.isVisible());
        existingCallPlan.setUpdatedAt(OffsetDateTime.now());

        return existingCallPlan;
    }

    public List<CallPlan> findAll() {
        return callPlanRepository.findAll();
    }

    public CallPlan findById(Long callPlanId) {
        return callPlanRepository.findById(callPlanId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Call plan with id " + callPlanId + " not found!"));
    }

    public CallPlan findByUuid(UUID uuid) {
        CallPlan callPlan = callPlanRepository.findByUuid(uuid);

        if (callPlan == null) {
            throw new EntityNotFoundException("Call plan with uuid " + uuid + " not found!");
        }

        return callPlan;
    }

    @Transactional
    public void deleteAll() {
        callPlanRepository.deleteAll();
    }
}
