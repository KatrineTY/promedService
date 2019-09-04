package com.javaschool.controller;

import com.javaschool.exception.ResourceNotFoundException;
import com.javaschool.model.Promed;
import com.javaschool.model.PromedDto;
import com.javaschool.services.PromedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class PromedController {
    @Autowired
    private PromedService promedService;

    @GetMapping("promeds")
    public List<Promed> getAllPromeds() {
        return promedService.getAllPromeds();
    }

    @GetMapping("promeds/{id}")
    public ResponseEntity<PromedDto> getPromedById(@PathVariable(value = "id") Integer promedId)
            throws ResourceNotFoundException {
        PromedDto promedDto = promedService.getPromedById(promedId);
        return ResponseEntity.ok().body(promedDto);
    }

    @PostMapping("promeds")
    public Promed createPromed(@Valid @RequestBody Promed promed) {
        return promedService.createPromed(promed);
    }

    @PutMapping("promeds/{id}")
    public ResponseEntity<Promed> updatePromed(@PathVariable(value = "id") Integer promedId,
                                               @Valid @RequestBody PromedDto promedDetails) throws ResourceNotFoundException {
        Promed updatedPromed = promedService.updatePromed(promedId, promedDetails);
        return ResponseEntity.ok(updatedPromed);
    }

    @DeleteMapping("promeds/{id}")
    public Map<String, Boolean> deletePromed(@PathVariable(value = "id") Integer promedId)
            throws ResourceNotFoundException {
        promedService.deletePromed(promedId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
