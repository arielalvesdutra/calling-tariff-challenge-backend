package dev.arielalvesdutra.calling_tariff.services.dtos;

import dev.arielalvesdutra.calling_tariff.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class CreateCallRecordDTO {
    private Integer originCode;
    private Integer destinationCode;
    private Integer minutes;
    private User client;
}
