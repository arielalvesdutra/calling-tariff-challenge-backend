package dev.arielalvesdutra.calling_tariff.controllers;

import dev.arielalvesdutra.calling_tariff.controllers.dtos.CallTariffMapDTO;
import dev.arielalvesdutra.calling_tariff.entities.CallTariffMap;
import dev.arielalvesdutra.calling_tariff.services.CallTariffMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/call-tariff-maps")
@RestController
public class CallTariffMapController {

    @Autowired
    private CallTariffMapService callTariffMapService;

    @GetMapping
    public ResponseEntity<List<CallTariffMapDTO>> retrieveAll() {
        List<CallTariffMap> callTariffMapList = callTariffMapService.findAll();

        return ResponseEntity.ok().body(CallTariffMapDTO.fromCallTariffMapList(callTariffMapList));
    }
}
