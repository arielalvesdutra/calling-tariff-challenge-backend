package dev.arielalvesdutra.calling_tariff.repositories;

import dev.arielalvesdutra.calling_tariff.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByCode(String code);
}
