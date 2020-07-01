package dev.arielalvesdutra.calling_tariff.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static dev.arielalvesdutra.calling_tariff.helpers.ObjectHelper.isEmpty;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = "email")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid", columnDefinition = "VARCHAR(255)")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID uuid = UUID.randomUUID();
    @Column(nullable = false)
    private String name;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @Size(min = 8)
    private String password;
    private boolean active = true;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt = OffsetDateTime.now();
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
    @ManyToOne
    private CallPlan callPlan;

    public boolean hasACallPlan() {
        return !isEmpty(this.getCallPlan());
    }
}
