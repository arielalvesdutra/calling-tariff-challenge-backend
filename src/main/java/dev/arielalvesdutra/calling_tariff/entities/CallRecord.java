package dev.arielalvesdutra.calling_tariff.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Entity that represents the registration of information
 * of a call that will be used to charge customers.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = "id")
public class CallRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal price = new BigDecimal("0.00");
    @Column(nullable = false)
    private Integer minutes;
    @ManyToOne
    private User client;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
