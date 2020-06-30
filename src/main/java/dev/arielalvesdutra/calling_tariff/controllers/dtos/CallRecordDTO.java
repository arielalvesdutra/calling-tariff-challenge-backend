package dev.arielalvesdutra.calling_tariff.controllers.dtos;

import dev.arielalvesdutra.calling_tariff.entities.CallRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class CallRecordDTO {
    private BigDecimal price;
    private Integer minutes;

    public CallRecordDTO(CallRecord callRecord) {
        this.price = callRecord.getPrice();
        this.minutes = callRecord.getMinutes();
    }
}
