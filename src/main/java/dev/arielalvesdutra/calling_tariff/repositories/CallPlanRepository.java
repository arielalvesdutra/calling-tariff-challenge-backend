package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CallPlanRepository extends JpaRepository<CallPlan, Long> {
    CallPlan findByUuid(UUID uuid);
}
