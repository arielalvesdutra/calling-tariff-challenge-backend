package dev.arielalvesdutra.calling_tariff.controllers;

import dev.arielalvesdutra.calling_tariff.controllers.dtos.CallPlanDTO;
import dev.arielalvesdutra.calling_tariff.entities.CallPlan;
import dev.arielalvesdutra.calling_tariff.services.CallPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
