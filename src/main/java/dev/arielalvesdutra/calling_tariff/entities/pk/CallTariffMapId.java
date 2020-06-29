package dev.arielalvesdutra.calling_tariff.entities.pk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * CallTariffMap entity Primary Key.
 */
@Getter
@Setter
@Embeddable
@ToString
public class CallTariffMapId implements Serializable {
    private static final long serialVersionUID = 113152343L;

    private Long originId;
    private Long destinationId;
}
