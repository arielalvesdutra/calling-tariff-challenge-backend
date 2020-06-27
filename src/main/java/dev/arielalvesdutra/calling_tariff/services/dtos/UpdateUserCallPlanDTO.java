package dev.arielalvesdutra.calling_tariff.services.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class UpdateUserCallPlanDTO {
    private UUID userUuid;
    private UUID callPlanUuid;
}
