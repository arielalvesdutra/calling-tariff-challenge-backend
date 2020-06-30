package dev.arielalvesdutra.calling_tariff.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class CalculateCallRecordRequestDTO {

    @NotNull
    private Integer duration;
    private Integer originCode;
    private Integer destinationCode;
    private UUID callPlanUuid;
}
