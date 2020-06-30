package dev.arielalvesdutra.calling_tariff.controllers.dtos;

import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.entities.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO of DDD - Direct Distance Dialing.
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class DDDDTO {

    private Long id;
    private Integer code;
    private State state;

    public DDDDTO(DDD ddd) {
        this.id = ddd.getId();
        this.code= ddd.getCode();
        this.state = ddd.getState();
    }

    public static List<DDDDTO> fromDDDList(List<DDD> dddList) {
        List<DDDDTO> dddDtoList = new ArrayList<>();

        for (DDD ddd : dddList) {
            dddDtoList.add(new DDDDTO(ddd));
        }

        return dddDtoList;
    }
}
