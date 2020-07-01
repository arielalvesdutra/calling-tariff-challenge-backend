package dev.arielalvesdutra.calling_tariff.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entity that represent a record of the actions executed by users on the
 * system.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = { "id", "client", "author", "description", "createdAt" })
public class Historic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "uuid", columnDefinition = "VARCHAR(255)")
    private UUID uuid = UUID.randomUUID();
    @ManyToOne
    private User author;
    @ManyToOne
    private User client;
    @Column(nullable = false)
    private String description;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
