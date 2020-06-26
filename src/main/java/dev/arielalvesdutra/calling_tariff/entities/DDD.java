package dev.arielalvesdutra.calling_tariff.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * DDD - Direct Distance Dialing.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = "id")
public class DDD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer code;
    @ManyToOne
    private State state;
}
