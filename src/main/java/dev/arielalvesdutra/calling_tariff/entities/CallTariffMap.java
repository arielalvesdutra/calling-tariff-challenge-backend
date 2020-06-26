package dev.arielalvesdutra.calling_tariff.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Entity that represents a map of a origin DDD and destination DDD with
 * the related price per minute.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = { "origin", "destination" })
public class CallTariffMap {

    @Id
    private DDD origin;
    @Id
    private DDD destination;
    @Column(nullable = false)
    private BigDecimal pricePerMinute;
}
