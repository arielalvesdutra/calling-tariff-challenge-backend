package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {

    State findByAbbreviation(String abbreviation);
}
