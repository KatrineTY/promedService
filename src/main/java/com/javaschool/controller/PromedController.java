package com.javaschool.controller;

import com.javaschool.exception.ResourceNotFoundException;
import com.javaschool.model.Promed;
import com.javaschool.model.PromedDto;
import com.javaschool.services.PromedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Slf4j
public class PromedController {
    @Autowired
    private PromedService promedService;

    @GetMapping("promeds")
    public List<Promed> getAllPromeds() {
        log.info("Get all promeds");
        return promedService.getAllPromeds();
    }

    @GetMapping("promeds/{id}")
    public ResponseEntity<PromedDto> getPromedById(@PathVariable(value = "id") Integer promedId)
            throws ResourceNotFoundException {
        log.info("Get promed with id: {}", promedId);
        PromedDto promedDto = promedService.getPromedById(promedId);
        return ResponseEntity.ok().body(promedDto);
    }

    @PostMapping("promeds")
    public Promed createPromed(@Valid @RequestBody Promed promed) {
        log.info("Create promed");
        return promedService.createPromed(promed);
    }

    @PutMapping("promeds/{id}")
    public ResponseEntity<Promed> updatePromed(@PathVariable(value = "id") Integer promedId,
                                               @Valid @RequestBody PromedDto promedDetails) throws ResourceNotFoundException {
        log.info("Update promed with id: {}", promedId);
        Promed updatedPromed = promedService.updatePromed(promedId, promedDetails);
        return ResponseEntity.ok(updatedPromed);
    }

    @DeleteMapping("promeds/{id}")
    public Map<String, Boolean> deletePromed(@PathVariable(value = "id") Integer promedId)
            throws ResourceNotFoundException {
        log.info("Delete promed with id: {}", promedId);
        promedService.deletePromed(promedId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
