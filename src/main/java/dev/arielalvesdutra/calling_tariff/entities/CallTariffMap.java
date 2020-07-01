package dev.arielalvesdutra.calling_tariff.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.arielalvesdutra.calling_tariff.entities.pk.CallTariffMapId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

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
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "uuid", columnDefinition = "VARCHAR(255)")
    private UUID uuid = UUID.randomUUID();

    @MapsId("originId")
    @ManyToOne
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    @JsonIgnore
    private DDD origin;

    @MapsId("destinationId")
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    @JsonIgnore
    private DDD destination;

    @Column(nullable = false)
    private BigDecimal pricePerMinute;
}
