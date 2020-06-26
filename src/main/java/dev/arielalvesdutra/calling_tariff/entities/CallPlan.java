package dev.arielalvesdutra.calling_tariff.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = "id")
public class CallPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid = UUID.randomUUID();
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private BigDecimal price = new BigDecimal("0.00");
    @Column(nullable = false)
    private Integer minutes = 1;
    private boolean visible = false;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt = OffsetDateTime.now();
    @OneToMany(
            mappedBy = "callPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<User> users = new HashSet<>();
}
