package com.example.footballteamapplication.controller;

import com.example.footballteamapplication.dto.FootballTeamCreateUpdateDTO;
import com.example.footballteamapplication.dto.FootballTeamDTO;
import com.example.footballteamapplication.dto.FootballTeamDetailDTO;
import com.example.footballteamapplication.service.FootballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/football-team-application")
public class FootballTeamController {
    private final FootballTeamService footballTeamService;

    @Autowired
    public FootballTeamController(FootballTeamService footballTeamService) {
        this.footballTeamService = footballTeamService;
    }

    @GetMapping("/")
    public ResponseEntity<List<FootballTeamDTO>> getFootballTeams() {
        List<FootballTeamDTO> footballTeams = footballTeamService.getAllFootballTeams();
        return ResponseEntity.ok(footballTeams);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FootballTeamDetailDTO> getFootballTeam(@PathVariable String uuid) {
        FootballTeamDetailDTO footballTeam = footballTeamService.getFootballTeamByUuid(uuid);
        if (footballTeam != null) {
            return ResponseEntity.ok(footballTeam);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<FootballTeamDetailDTO> createFootballTeam(@RequestBody FootballTeamCreateUpdateDTO createDTO) {
        FootballTeamDetailDTO footballTeam = footballTeamService.createFootballTeam(createDTO);
        return ResponseEntity.ok(footballTeam);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<FootballTeamDetailDTO> updateFootballTeam(@PathVariable String uuid, @RequestBody FootballTeamCreateUpdateDTO updateDTO) {
        FootballTeamDetailDTO footballTeam = footballTeamService.updateFootballTeam(uuid, updateDTO);
        if (footballTeam != null) {
            return ResponseEntity.ok(footballTeam);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteFootballTeam(@PathVariable String uuid) {
        boolean deleted = footballTeamService.deleteFootballTeam(uuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
