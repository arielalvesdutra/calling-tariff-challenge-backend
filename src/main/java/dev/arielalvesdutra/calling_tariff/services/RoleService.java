package dev.arielalvesdutra.calling_tariff.services;

import dev.arielalvesdutra.calling_tariff.entities.Role;
import dev.arielalvesdutra.calling_tariff.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Role with id " + roleId + " not found!"));
    }

    public Role findByCode(String code) {
        Role role =  roleRepository.findByCode(code);

        if (role == null) {
         throw new EntityNotFoundException("Role with code " + code + " not found!");
        }

        return role;
    }

    @Transactional
    public void deleteById(Long roleId) {
        Role role = findById(roleId);
        roleRepository.deleteById(role.getId());
    }

    @Transactional
    public Role update(Role parameterRole) {
        Role existingRole = findById(parameterRole.getId());

        existingRole.setName(parameterRole.getName());
        existingRole.setDescription(parameterRole.getDescription());
        existingRole.setUpdatedAt(OffsetDateTime.now());

        return existingRole;
    }
}
