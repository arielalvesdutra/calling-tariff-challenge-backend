package dev.arielalvesdutra.calling_tariff.controllers.dtos;

import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.entities.DDD;
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
public class CallTariffMapDTO {

    private BigDecimal pricePerMinute;
    private UUID uuid;
    private Integer originDDD;
    private Integer destinationDDD;

    public CallTariffMapDTO(CallTariffMap callTariffMap) {
        DDD origin =  callTariffMap.getOrigin();
        DDD destination = callTariffMap.getDestination();

        this.pricePerMinute = callTariffMap.getPricePerMinute();
        this.uuid = callTariffMap.getUuid();
        this.originDDD = origin.getCode();
        this.destinationDDD = destination.getCode();
    }

    public static List<CallTariffMapDTO> fromCallTariffMapList(
            List<CallTariffMap> callTariffMapList) {

        List<CallTariffMapDTO> mapDTOList = new ArrayList<>();

        for (CallTariffMap map : callTariffMapList) {
            mapDTOList.add((new CallTariffMapDTO(map)));
        }

        return mapDTOList;
    }
}
