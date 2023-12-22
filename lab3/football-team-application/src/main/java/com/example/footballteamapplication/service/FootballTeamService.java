package com.example.footballteamapplication.service;

import com.example.footballteamapplication.dto.FootballTeamCreateUpdateDTO;
import com.example.footballteamapplication.dto.FootballTeamDTO;
import com.example.footballteamapplication.dto.FootballTeamDetailDTO;
import com.example.footballteamapplication.dto.FootballTeamDetailInfoDTO;
import com.example.footballteamapplication.model.FootballTeam;
import com.example.footballteamapplication.repository.FootballTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FootballTeamService {
    private final FootballTeamRepository footballTeamRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public FootballTeamService(FootballTeamRepository footballTeamRepository, RestTemplate restTemplate) {
        this.footballTeamRepository = footballTeamRepository;
        this.restTemplate = restTemplate;
    }

    public List<FootballTeamDTO> getAllFootballTeams() {
        List<FootballTeam> footballTeams = footballTeamRepository.findAll();
        return footballTeams.stream()
                .map(this::convertToFootballTeamDTO)
                .collect(Collectors.toList());
    }

    public FootballTeamDetailDTO getFootballTeamByUuid(String uuid) {
        FootballTeam footballTeam = footballTeamRepository.findByUuid(uuid);
        if (footballTeam != null) {
            return convertToFootballTeamDetailDTO(footballTeam);
        }
        return null;
    }

    public FootballTeamDetailDTO createFootballTeam(FootballTeamCreateUpdateDTO createDTO) {
        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setUuid(createUUID());
        updateFootballTeamFromDTO(footballTeam, createDTO);
        footballTeam = footballTeamRepository.save(footballTeam);
        return convertToFootballTeamDetailDTO(footballTeam);
    }

    public FootballTeamDetailDTO updateFootballTeam(String uuid, FootballTeamCreateUpdateDTO updateDTO) {
        FootballTeam footballTeam = footballTeamRepository.findByUuid(uuid);
        if (footballTeam != null) {
            updateFootballTeamFromDTO(footballTeam, updateDTO);
            footballTeam = footballTeamRepository.save(footballTeam);
            return convertToFootballTeamDetailDTO(footballTeam);
        }
        return null;
    }

    public boolean deleteFootballTeam(String uuid) {
        FootballTeam footballTeam = footballTeamRepository.findByUuid(uuid);
        if (footballTeam != null) {
            footballTeamRepository.delete(footballTeam);
            return true;
        }
        return false;
    }

    private FootballTeamDTO convertToFootballTeamDTO(FootballTeam footballTeam) {
        FootballTeamDTO dto = new FootballTeamDTO();
        dto.setUuid(footballTeam.getUuid());
        dto.setName(footballTeam.getName());
        dto.setEstablishedYear(footballTeam.getEstablishedYear());
        dto.setDivision(footballTeam.getDivision());
        dto.setCoach(footballTeam.getCoach());
        dto.setPortalUuid(footballTeam.getPortalUuid());
        return dto;
    }

    private FootballTeamDetailDTO convertToFootballTeamDetailDTO(FootballTeam footballTeam) {
        FootballTeamDetailDTO detailDTO = new FootballTeamDetailDTO();
        detailDTO.setUuid(footballTeam.getUuid());
        detailDTO.setName(footballTeam.getName());
        detailDTO.setEstablishedYear(footballTeam.getEstablishedYear());
        detailDTO.setDivision(footballTeam.getDivision());
        detailDTO.setCoach(footballTeam.getCoach());
        detailDTO.setPortalUuid(footballTeam.getPortalUuid());

        FootballTeamDetailInfoDTO portalInfo = getPortalInfo(footballTeam.getPortalUuid());
        detailDTO.setPortalInfo(portalInfo);

        return detailDTO;
    }

    private void updateFootballTeamFromDTO(FootballTeam footballTeam, FootballTeamCreateUpdateDTO dto) {
        footballTeam.setName(dto.getName());
        footballTeam.setEstablishedYear(dto.getEstablishedYear());
        footballTeam.setDivision(dto.getDivision());
        footballTeam.setCoach(dto.getCoach());
        footballTeam.setPortalUuid(dto.getPortalUuid());
    }

    private String createUUID() {
        Random random = new Random();
        long mostSigBits = random.nextLong();
        long leastSigBits = random.nextLong();
        return String.format("%08x-%04x-%04x-%04x-%012x",
                mostSigBits >>> 32, (mostSigBits >>> 16) & 0xFFFF, mostSigBits & 0xFFFF,
                (leastSigBits >>> 16) & 0xFFFF, leastSigBits & 0xFFFF);
    }

    private FootballTeamDetailInfoDTO getPortalInfo(String portalUuid) {
        String portalInfoUrl = "http://nginx/football-portal-application/" + portalUuid;
        return restTemplate.getForObject(portalInfoUrl, FootballTeamDetailInfoDTO.class);
    }
}
