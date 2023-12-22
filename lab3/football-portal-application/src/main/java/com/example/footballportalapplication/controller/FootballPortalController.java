package com.example.footballportalapplication.controller;

import com.example.footballportalapplication.model.FootballPortal;
import com.example.footballportalapplication.dto.FootballPortalDTO;
import com.example.footballportalapplication.service.FootballPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/football-portal-application")
public class FootballPortalController {
    private final FootballPortalService portalService;

    @Autowired
    public FootballPortalController(FootballPortalService portalService) {
        this.portalService = portalService;
    }

    @GetMapping("/")
    public List<FootballPortal> getAllPortals() {
        return portalService.getAllPortals();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FootballPortal> getPortalByUuid(@PathVariable String uuid) {
        return portalService.getPortalByUuid(uuid)
                .map(portal -> ResponseEntity.ok().body(portal))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<FootballPortal> createPortal(@RequestBody FootballPortalDTO portalDTO) {
        FootballPortal createdPortal = portalService.createPortal(portalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPortal);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<FootballPortal> updatePortal(@PathVariable String uuid, @RequestBody FootballPortalDTO portalDTO) {
        FootballPortal updatedPortal = portalService.updatePortal(uuid, portalDTO);
        if (updatedPortal != null) {
            return ResponseEntity.ok().body(updatedPortal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePortal(@PathVariable String uuid) {
        boolean deleted = portalService.deletePortal(uuid);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
