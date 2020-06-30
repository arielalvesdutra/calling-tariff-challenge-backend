package dev.arielalvesdutra.calling_tariff.controllers;


import dev.arielalvesdutra.calling_tariff.controllers.dtos.CalculateCallRecordRequestDTO;
import dev.arielalvesdutra.calling_tariff.controllers.dtos.CallRecordDTO;
import dev.arielalvesdutra.calling_tariff.entities.CallRecord;
import dev.arielalvesdutra.calling_tariff.services.CallRecordService;
import dev.arielalvesdutra.calling_tariff.services.dtos.CalculateCallRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/call-records")
@RestController
public class CallRecordController {

    @Autowired
    private CallRecordService callRecordService;

    /**
     * Method for testing call calculations.
     *
     * Need to be refactored.
     *
     * @param dto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/calculate")
    public ResponseEntity<CallRecordDTO> calculate(
            @Valid @RequestBody CalculateCallRecordRequestDTO dto) {

        CalculateCallRecordDTO calculateDto = callRecordService.fillCalculateCallRecordDTO(dto);
        CallRecord callRecord = callRecordService.calculate(calculateDto);

        return ResponseEntity.ok().body(new CallRecordDTO(callRecord));
    }
}
