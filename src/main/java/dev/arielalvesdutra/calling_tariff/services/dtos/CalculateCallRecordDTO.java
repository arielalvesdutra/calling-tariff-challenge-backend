package dev.arielalvesdutra.calling_tariff.services.dtos;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Getter
@Setter
@Accessors(chain = true)
public class CalculateCallRecordDTO {
    private User client;
    private DDD origin;
    private DDD destination;
    private Integer duration;
}
