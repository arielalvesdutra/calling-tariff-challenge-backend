package dev.arielalvesdutra.calling_tariff.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Role of a user.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = "code")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "uuid", columnDefinition = "VARCHAR(255)")
    private UUID uuid = UUID.randomUUID();
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(unique = true, nullable = false)
    private String code;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt = OffsetDateTime.now();
    @ManyToMany
    @JoinTable(name = "user_role",
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();
}
