package dev.arielalvesdutra.calling_tariff.controllers.dtos;

import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class CallPlanDTO {

    private UUID uuid = UUID.randomUUID();
    private String name;
    private String description;
    private BigDecimal price = new BigDecimal("0.00");
    private Integer minutes = 1;

    public CallPlanDTO(CallPlan callPlan) {
        this.uuid = callPlan.getUuid();
        this.name = callPlan.getName();
        this.description = callPlan.getDescription();
        this.price = callPlan.getPrice();
        this.minutes = callPlan.getMinutes();
    }

    public static List<CallPlanDTO> fromCallPlanList(List<CallPlan> callPlanList) {
        List<CallPlanDTO> callPlanDTOList = new ArrayList<>();

        for (CallPlan callPlan : callPlanList) {
            callPlanDTOList.add((new CallPlanDTO(callPlan)));
        }

        return callPlanDTOList;
    }
}
