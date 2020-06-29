package dev.arielalvesdutra.calling_tariff.entities;

import dev.arielalvesdutra.calling_tariff.entities.pk.CallTariffMapId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity that represents a map of a origin DDD and destination DDD with
 * the related price per minute.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@ToString
@EqualsAndHashCode(of = { "origin", "destination" })
public class CallTariffMap implements Serializable {
    private static final long serialVersionUID = 12L;

    @EmbeddedId
    private CallTariffMapId id = new CallTariffMapId();

    private UUID uuid = UUID.randomUUID();

    @MapsId("originId")
    @ManyToOne
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private DDD origin;

    @MapsId("destinationId")
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private DDD destination;

    @Column(nullable = false)
    private BigDecimal pricePerMinute;
}
