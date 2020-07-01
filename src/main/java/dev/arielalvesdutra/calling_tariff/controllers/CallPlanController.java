package dev.arielalvesdutra.calling_tariff.controllers;

import dev.arielalvesdutra.calling_tariff.controllers.dtos.CallPlanDTO;
import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.services.CallPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/call-plans")
@RestController
public class CallPlanController {

    @Autowired
    private CallPlanService callPlanService;

    @GetMapping
    public ResponseEntity<List<CallPlanDTO>> retrieveAll() {
        List<CallPlan> callPlanList = callPlanService.findAll();

        return ResponseEntity.ok().body(CallPlanDTO.fromCallPlanList(callPlanList));
    }

    @RequestMapping( path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<CallPlan> retrieve(@PathVariable UUID uuid) {
        CallPlan callPlan = callPlanService.findByUuid(uuid);

        return ResponseEntity.ok().body(callPlan);
    }
}
