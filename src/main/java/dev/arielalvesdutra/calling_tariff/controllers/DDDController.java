package dev.arielalvesdutra.calling_tariff.controllers;

import dev.arielalvesdutra.calling_tariff.controllers.dtos.DDDDTO;
import dev.arielalvesdutra.calling_tariff.entities.DDD;
import dev.arielalvesdutra.calling_tariff.services.DDDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/ddd")
@RestController
public class DDDController {

    @Autowired
    private DDDService dddService;

    @GetMapping
    public ResponseEntity<List<DDDDTO>> retrieveAll() {
        List<DDD> dddList =  dddService.findAll();

        return ResponseEntity.ok().body(DDDDTO.fromDDDList(dddList));
    }
}
