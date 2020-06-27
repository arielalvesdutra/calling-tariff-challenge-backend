package dev.arielalvesdutra.calling_tariff.fakes.factories.entities;

import dev.arielalvesdutra.calling_tariff.entities.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Factory to create fake objects for testing.
 */
public class RoleFactory {

    /**
     *
     * @return
     */
    public static Role fakeRole() {
        return new Role()
                .setId(1L)
                .setName("Support")
                .setCode("ROLE_SUPPORT")
                .setDescription("Support Analyst");
    }

    /**
     *
     * @param role
     * @return
     */
    public static Set<Role> fakeRoleSet(Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }
}
