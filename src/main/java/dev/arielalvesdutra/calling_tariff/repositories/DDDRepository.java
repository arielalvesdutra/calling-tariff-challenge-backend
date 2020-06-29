package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDDRepository  extends JpaRepository<DDD, Long> {
    DDD findByCode(Integer code);
}
