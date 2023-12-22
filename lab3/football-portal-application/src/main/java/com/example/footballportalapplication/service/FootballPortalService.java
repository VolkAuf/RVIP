package com.example.footballportalapplication.service;

import com.example.footballportalapplication.model.FootballPortal;
import com.example.footballportalapplication.dto.FootballPortalDTO;
import com.example.footballportalapplication.repository.FootballPortalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FootballPortalService {
    private final FootballPortalRepository portalRepository;

    @Transactional
    public List<FootballPortal> getAllPortals() {
        return portalRepository.findAll();
    }

    @Transactional
    public Optional<FootballPortal> getPortalByUuid(String uuid) {
        return portalRepository.findByUuid(uuid);
    }

    @Transactional
    public FootballPortal createPortal(FootballPortalDTO portalDTO) {
        FootballPortal portal = new FootballPortal();
        portal.setUuid(createUUID()); // Генерация UUID
        portal.setName(portalDTO.getName());
        portal.setSupportedPlatforms(portalDTO.getSupportedPlatforms());
        portal.setMaximumUsers(portalDTO.getMaximumUsers());
        return portalRepository.save(portal);
    }

    @Transactional
    public FootballPortal updatePortal(String uuid, FootballPortalDTO portalDTO) {
        Optional<FootballPortal> existingPortal = portalRepository.findByUuid(uuid);
        if (existingPortal.isPresent()) {
            FootballPortal portal = existingPortal.get();
            portal.setName(portalDTO.getName());
            portal.setSupportedPlatforms(portalDTO.getSupportedPlatforms());
            portal.setMaximumUsers(portalDTO.getMaximumUsers());
            return portalRepository.save(portal);
        }
        return null;
    }

    @Transactional
    public boolean deletePortal(String uuid) {
        Optional<FootballPortal> existingPortal = portalRepository.findByUuid(uuid);
        if (existingPortal.isPresent()) {
            portalRepository.delete(existingPortal.get());
            return true;
        }
        return false;
    }

    private String createUUID() {
        Random random = new Random();
        long mostSigBits = random.nextLong();
        long leastSigBits = random.nextLong();
        return String.format("%08x-%04x-%04x-%04x-%012x",
                mostSigBits >>> 32, (mostSigBits >>> 16) & 0xFFFF, mostSigBits & 0xFFFF,
                (leastSigBits >>> 16) & 0xFFFF, leastSigBits & 0xFFFF);
    }
}
